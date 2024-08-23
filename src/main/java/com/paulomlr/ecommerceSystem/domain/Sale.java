package com.paulomlr.ecommerceSystem.domain;

import com.paulomlr.ecommerceSystem.domain.enums.SaleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "tb_sale")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "saleId")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sale_id")
    private UUID saleId;
    private Instant saleDate;
    private SaleStatus saleStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User name;

    @OneToMany(mappedBy = "id.sale")
    private Set<ProductSale> items = new HashSet<>();
}
