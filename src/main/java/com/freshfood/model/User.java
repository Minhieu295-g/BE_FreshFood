package com.freshfood.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User extends AbsEntity<Integer> {

    @Column(unique = true, nullable = false, name = "username")
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "number_phone")
    private String numberPhone;

    @Column(name = "email")
    private String email;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

}
