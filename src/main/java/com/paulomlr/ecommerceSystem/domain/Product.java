package com.paulomlr.ecommerceSystem.domain;

import com.paulomlr.ecommerceSystem.domain.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "tb_product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "productId")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;

    @Setter
    private String name;

    @Setter
    private Double price;

    @Setter
    private ProductStatus productStatus;

    @OneToMany(mappedBy = "id.product")
    private Set<ProductSale> items = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @OneToOne(mappedBy = "product")
    private Stock stock;
}
