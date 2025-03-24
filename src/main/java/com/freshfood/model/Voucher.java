package com.freshfood.model;

import com.freshfood.util.DiscountTypeEnum;
import com.freshfood.util.VoucherStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vouchers")
public class Voucher extends AbsEntity<Integer>{
    @Column(nullable = false, unique = true, name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "discount_type")
    private DiscountTypeEnum discountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "voucher_status")
    private VoucherStatusEnum voucherStatus;

    @Column(nullable = false, name = "discount_value")
    private double discountValue;

    @Column(nullable = false, name = "min_order_value")
    private double minOrderValue;

    @Column(nullable = false, name = "max_discount")
    private double maxDiscount;

    @Column(nullable = false, name = "start_date")
    private Date startDate;

    @Column(nullable = false, name = "end_date")
    private Date endDate;

    @Column(name = "description")
    private String description;

    public boolean isExpired() {
        Date now = new Date();
        return now.after(endDate);
    }
}
