package com.paulomlr.ecommerceSystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserRequestDTO;
import com.paulomlr.ecommerceSystem.domain.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @NotBlank
    @Size(min = 5, max = 15)
    @Column(unique = true)
    private String login;

    @NotNull @Size(min = 8)
    private String password;

    private UserRole role;

    public User(UserRequestDTO user) {
        login = user.login();
        password = user.password();
        role = UserRole.USER;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Sale> sales = new ArrayList<>();
}
