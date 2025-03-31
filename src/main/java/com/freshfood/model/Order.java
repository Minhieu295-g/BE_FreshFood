package com.freshfood.model;

import com.freshfood.util.OrderStatusEnum;
import com.freshfood.util.PaymentMethodsEnum;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AbsEntity<Integer>{

    @Column(name = "order_code", unique = true)
    private String orderCode;

    @Column(nullable = false, name = "total_price")
    private double totalPrice;

    @Column(name = "note")
    private String note;

    @Column(nullable = false, name = "delivery_fee")
    private double deliveryFee;

    @Column(name = "expected_delivery_time")
    private Date expectedDeliveryTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_methods")
    private PaymentMethodsEnum paymentMethods;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "order_status")
    private OrderStatusEnum orderStatus;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private DeliveryAddress deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

}
