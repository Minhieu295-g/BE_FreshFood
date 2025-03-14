package com.freshfood.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery_addresses")
public class DeliveryAddress extends AbsEntity<Integer>{

    @Column(name = "name")
    private String name;

    @Column(name = "number_phone")
    private String numberPhone;

    @Column(name = "province_id")
    private int provinceId;

    @Column(name = "district_id")
    private int districtId;

    @Column(name = "ward_id")
    private int wardId;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "ward_name")
    private String wardName;

    @Column(name = "detail_address")
    private String detailAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;
}
