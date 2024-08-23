package com.paulomlr.ecommerceSystem.domain;

import com.paulomlr.ecommerceSystem.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "tb_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;
    private String login;
    private String password;
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Sale> sales = new ArrayList<>();
}
