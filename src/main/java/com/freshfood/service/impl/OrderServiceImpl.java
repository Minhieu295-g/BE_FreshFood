package com.freshfood.service.impl;
import com.freshfood.dto.request.CartItemRequestDTO;
import com.freshfood.dto.request.OrderRequestDTO;
import com.freshfood.dto.request.OrderStatusRequestDTO;
import com.freshfood.dto.response.*;
import com.freshfood.model.*;
import com.freshfood.repository.OrderRepository;
import com.freshfood.service.*;
import com.freshfood.service.api.GiaoHangNhanhService;
import com.freshfood.util.DiscountTypeEnum;
import com.freshfood.util.OrderStatusEnum;
import com.freshfood.util.PaymentMethodsEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.freshfood.util.OrderStatusEnum.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final VoucherService voucherService;
    private final DeliveryAddressService deliveryAddressService;
    private final ProductVariantService productVariantService;
    private final GiaoHangNhanhService giaoHangNhanhService;
    private final CartItemService cartItemService;
    private final UserService userService;
    @Override
    public String addOrder(OrderRequestDTO orderRequestDTO) {
        int deliveryAddressId = orderRequestDTO.getDeliveryAddressId();
        int voucherId = orderRequestDTO.getVoucherId();
        int userId = orderRequestDTO.getUserId();
        DeliveryAddress address = deliveryAddressService.getDeliveryAddressById(deliveryAddressId);
        DeliveryFeeResponseDTO deliveryFee = getDeliveryFee(address);
        Instant instant = Instant.parse(deliveryFee.getDeliveryDate());
        Date date = Date.from(instant);
        log.info("Da vao den day ne: ");
        Order order = Order.builder()
                .orderStatus(PENDING)
                .note(orderRequestDTO.getNote())
                .deliveryAddress(deliveryAddressService.getDeliveryAddressById(deliveryAddressId))
                .voucher(voucherService.getVoucherById(voucherId))
                .deliveryFee(Double.parseDouble(deliveryFee.getDeliveryFee()))
                .expectedDeliveryTime(date)
                .user(userService.findByUserId(userId))
                .build();
        if(orderRequestDTO.getPaymentMethod().equals(PaymentMethodsEnum.COD.toString())){
            order.setPaymentMethods(PaymentMethodsEnum.COD);
        }else if(orderRequestDTO.getPaymentMethod().equals(PaymentMethodsEnum.BANK_TRANSFER.toString())){
            order.setPaymentMethods(PaymentMethodsEnum.BANK_TRANSFER);
        }
        Set<OrderItem> orderItems = convertToOrderItems(orderRequestDTO.getCartItems(), order);
        order.setOrderItems(orderItems);
        double totalPrice = calTotalPrice(order.getVoucher(), orderItems);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        order.setOrderCode(generateOrderCode(order.getId()));
        orderRepository.save(order);
        deleteCartItem(orderRequestDTO.getCartItems());
        return order.getOrderCode();
    }

    @Override
    public void updateStatus(int orderId, OrderStatusRequestDTO status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        String statusRequest = status.getStatus().toUpperCase();
        if (order==null) return;
        switch (statusRequest) {
            case "PROCESSING":
                if (order.getOrderStatus()==PENDING ||order.getOrderStatus()==(RETURNED)) {
                    order.setOrderStatus(PROCESSING);
                }
                break;
            case "SHIPPED":
                if (order.getOrderStatus()==(PROCESSING)) {
                    order.setOrderStatus(SHIPPED);
                }
                break;
            case "DELIVERED":
                if (order.getOrderStatus().equals(SHIPPED)) {
                    order.setOrderStatus(DELIVERED);
                }
                break;
            case "RETURNED":
                if (order.getOrderStatus()==(SHIPPED)||order.getOrderStatus()==(DELIVERED)) {
                    order.setOrderStatus(RETURNED);
                }
                break;
            case "CANCELLED":
                if (order.getOrderStatus()!= DELIVERED && order.getOrderStatus() != RETURNED && order.getOrderStatus() != SHIPPED) {
                    order.setOrderStatus(CANCELLED);
                }
                break;
        }
        orderRepository.save(order);
    }

    @Override
    public PageResponse getOrdersByUserId(int userId, int pageNo, int pageSize) {
        User user = userService.findByUserId(userId);
        pageNo = (pageNo == 0) ? 1 : pageNo;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Order> orders = orderRepository.findByUser(user, pageable);
        List<OrderResponseDTO> orderResponse = orders.stream().map(order -> {
            List<OrderItemResponseDTO> orderItemResponseDTOs = order.getOrderItems().stream().map(orderItem ->
                            OrderItemResponseDTO.builder()
                                    .id(orderItem.getId())
                                    .product(ProductVariantResponseDTO.builder()
                                            .id(orderItem.getProductVariant().getId())
                                            .name(orderItem.getProductVariant().getProduct().getName())
                                            .price(orderItem.getProductVariant().getPrice())
                                            .thumbnailUrl(orderItem.getProductVariant().getThumbnailUrl())
                                            .unit(orderItem.getProductVariant().getUnit().toString())
                                            .expiryDate(orderItem.getProductVariant().getExpiryDate())
                                            .status(orderItem.getProductVariant().getStatus().toString())
                                            .build())
                                    .quantity(orderItem.getQuantity())
                                    .build())
                    .collect(Collectors.toList());

            return OrderResponseDTO.builder()
                    .id(order.getId())
                    .orderNumber(order.getOrderCode())
                    .date(order.getCreatedAt())
                    .totalPrice(order.getTotalPrice())
                    .shippingFee(order.getDeliveryFee())
                    .expectedDate(order.getExpectedDeliveryTime())
                    .paymentMethod(order.getPaymentMethods().name())
                    .status(order.getOrderStatus().name().toLowerCase())
                    .note(order.getNote())
                    .items(orderItemResponseDTOs)
                    .shippingAddress(DeliveryAddressResponseDTO.builder()
                            .id(order.getDeliveryAddress().getId())
                            .name(order.getDeliveryAddress().getName())
                            .numberPhone(order.getDeliveryAddress().getNumberPhone())
                            .provinceId(order.getDeliveryAddress().getProvinceId())
                            .wardId(order.getDeliveryAddress().getWardId())
                            .districtId(order.getDeliveryAddress().getDistrictId())
                            .provinceName(order.getDeliveryAddress().getProvinceName())
                            .districtName(order.getDeliveryAddress().getDistrictName())
                            .wardName(order.getDeliveryAddress().getWardName())
                            .isDefault(order.getDeliveryAddress().isDefault())
                            .detailAddress(order.getDeliveryAddress().getDetailAddress())
                            .build())
                    .build();
        }).collect(Collectors.toList());
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(orders.getTotalPages())
                .items(orderResponse)
                .build();
    }

    @Override
    public PageResponse getOrders(int pageNo, int pageSize) {
        pageNo = (pageNo == 0) ? 1 : pageNo;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Order> orders = orderRepository.findAll(pageable);
        List<OrderResponseDTO> orderResponse = orders.stream().map(order -> {
            List<OrderItemResponseDTO> orderItemResponseDTOs = order.getOrderItems().stream().map(orderItem ->
                            OrderItemResponseDTO.builder()
                                    .id(orderItem.getId())
                                    .product(ProductVariantResponseDTO.builder()
                                            .id(orderItem.getProductVariant().getId())
                                            .name(orderItem.getProductVariant().getProduct().getName())
                                            .price(orderItem.getProductVariant().getPrice())
                                            .thumbnailUrl(orderItem.getProductVariant().getThumbnailUrl())
                                            .unit(orderItem.getProductVariant().getUnit().toString())
                                            .expiryDate(orderItem.getProductVariant().getExpiryDate())
                                            .status(orderItem.getProductVariant().getStatus().toString())
                                            .build())
                                    .quantity(orderItem.getQuantity())
                                    .build())
                    .collect(Collectors.toList());

            return OrderResponseDTO.builder()
                    .id(order.getId())
                    .orderNumber(order.getOrderCode())
                    .date(order.getCreatedAt())
                    .totalPrice(order.getTotalPrice())
                    .shippingFee(order.getDeliveryFee())
                    .expectedDate(order.getExpectedDeliveryTime())
                    .paymentMethod(order.getPaymentMethods().name())
                    .status(order.getOrderStatus().name().toLowerCase())
                    .note(order.getNote())
                    .items(orderItemResponseDTOs)
                    .shippingAddress(DeliveryAddressResponseDTO.builder()
                            .id(order.getDeliveryAddress().getId())
                            .name(order.getDeliveryAddress().getName())
                            .numberPhone(order.getDeliveryAddress().getNumberPhone())
                            .provinceId(order.getDeliveryAddress().getProvinceId())
                            .wardId(order.getDeliveryAddress().getWardId())
                            .districtId(order.getDeliveryAddress().getDistrictId())
                            .provinceName(order.getDeliveryAddress().getProvinceName())
                            .districtName(order.getDeliveryAddress().getDistrictName())
                            .wardName(order.getDeliveryAddress().getWardName())
                            .isDefault(order.getDeliveryAddress().isDefault())
                            .detailAddress(order.getDeliveryAddress().getDetailAddress())
                            .build())
                    .build();
        }).collect(Collectors.toList());
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(orders.getTotalPages())
                .items(orderResponse)
                .build();
    }


    private void deleteCartItem(Set<CartItemReponseDTO> cartItems){
        for (CartItemReponseDTO cart: cartItems){
            cartItemService.deleteCartItem(cart.getId());
        }
    }
    private String generateOrderCode(int orderId) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Random random = new Random();
        int rd = random.nextInt(1000);
        return "ORD-" + today.format(formatter) + "-" + orderId + rd;
    }
    private Set<OrderItem> convertToOrderItems(Set<CartItemReponseDTO> cartItems, Order order) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItemReponseDTO cartItem : cartItems) {
            orderItems.add(OrderItem.builder()
                            .order(order)
                            .productVariant(productVariantService.getProductVariantById(cartItem.getProductVariant().getId()))
                            .quantity(cartItem.getQuantity())
                            .build());
        }
        return orderItems;
    }
    private double calTotalPrice(Voucher voucher, Set<OrderItem> orderItems){
        double totalPrice = 0;
        double discount = 0;
        for (OrderItem orderItem : orderItems ){
            ProductVariant productVariant = orderItem.getProductVariant();
            totalPrice += productVariant.getPrice() - (productVariant.getPrice()*productVariant.getDiscountPercentage())/100;
        }
        if(voucher != null && !voucher.isExpired() && totalPrice >= voucher.getMinOrderValue()){
            discount = getDiscountVoucherAmount(voucher, totalPrice);
        }
        return totalPrice - discount;
    }
    private double getDiscountVoucherAmount(Voucher voucher, double totalPrice){
        double discount = 0;
        if(voucher.getDiscountType() == DiscountTypeEnum.PERCENTAGE){
            discount = totalPrice - (totalPrice*voucher.getDiscountValue())/100;
        }else{
            discount = voucher.getDiscountValue();
        }
        discount = (discount >= voucher.getMaxDiscount()) ? voucher.getMaxDiscount() : discount;
        return discount;
    }
    private DeliveryFeeResponseDTO getDeliveryFee(DeliveryAddress deliveryAddress){
        String wardId = String.valueOf(deliveryAddress.getWardId());
        String districtId = String.valueOf(deliveryAddress.getDistrictId());
        return giaoHangNhanhService.getData2(deliveryAddress.getDetailAddress(), wardId, districtId);
    }

}
