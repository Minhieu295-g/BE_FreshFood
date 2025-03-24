package com.freshfood.service.impl;
import com.freshfood.dto.request.CartItemRequestDTO;
import com.freshfood.dto.request.OrderRequestDTO;
import com.freshfood.dto.response.DeliveryFeeResponseDTO;
import com.freshfood.model.*;
import com.freshfood.repository.OrderRepository;
import com.freshfood.service.*;
import com.freshfood.service.api.GiaoHangNhanhService;
import com.freshfood.util.DiscountTypeEnum;
import com.freshfood.util.OrderStatusEnum;
import com.freshfood.util.PaymentMethodsEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.freshfood.util.OrderStatusEnum.PENDING;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final VoucherService voucherService;
    private final DeliveryAddressService deliveryAddressService;
    private final ProductVariantService productVariantService;
    private final GiaoHangNhanhService giaoHangNhanhService;
    private final CartItemService cartItemService;
    private final UserService userService;
    @Override
    public int addOrder(OrderRequestDTO orderRequestDTO) {
        int deliveryAddressId = orderRequestDTO.getDeliveryAddressId();
        int voucherId = orderRequestDTO.getVoucherId();
        int userId = orderRequestDTO.getUserId();
        DeliveryAddress address = deliveryAddressService.getDeliveryAddressById(deliveryAddressId);
        DeliveryFeeResponseDTO deliveryFee = getDeliveryFee(address);
        Instant instant = Instant.parse(deliveryFee.getDeliveryDate());
        Date date = Date.from(instant);
        Order order = Order.builder()
                .orderStatus(PENDING)
                .paymentMethods(PaymentMethodsEnum.CASH)
                .note(orderRequestDTO.getNote())
                .deliveryAddress(deliveryAddressService.getDeliveryAddressById(deliveryAddressId))
                .voucher(voucherService.getVoucherById(voucherId))
                .deliveryFee(Double.parseDouble(deliveryFee.getDeliveryFee()))
                .expectedDeliveryTime(date)
                .user(userService.findByUserId(userId))
                .build();
        Set<OrderItem> orderItems = convertToOrderItems(orderRequestDTO.getCartItems(), order);
        order.setOrderItems(orderItems);
        double totalPrice = calTotalPrice(order.getVoucher(), orderItems);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        deleteCartItem(orderRequestDTO.getCartItems());
        return order.getId();
    }

    private Set<OrderItem> convertToOrderItems(Set<CartItemRequestDTO> cartItems, Order order) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItemRequestDTO cartItem : cartItems) {
            orderItems.add(OrderItem.builder()
                            .order(order)
                            .productVariant(productVariantService.getProductVariantById(cartItem.getProductVariantId()))
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
    private void deleteCartItem(Set<CartItemRequestDTO> cartItems){
        for (CartItemRequestDTO cart: cartItems){
            cartItemService.deleteCartItem(cart.getCartId());
        }
    }
}
