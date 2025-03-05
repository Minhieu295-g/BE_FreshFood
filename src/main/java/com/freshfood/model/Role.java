package com.freshfood.model;

import com.freshfood.util.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends AbsEntity<Integer>{
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private UserRole name;
}
