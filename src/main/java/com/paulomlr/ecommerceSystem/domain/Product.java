package com.paulomlr.ecommerceSystem.domain;

import com.paulomlr.ecommerceSystem.domain.dto.product.ProductRequestDTO;
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
    @Column(nullable = false, unique = true)
    private String name;

    @Setter
    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Setter
    private ProductStatus productStatus;

    public Product(ProductRequestDTO data) {
        this.name = data.name();
        this.price = data.price();
        this.stockQuantity = data.stockQuantity();
        productStatus = ProductStatus.ACTIVE;
    }

    public Product(UUID productId, String name, Double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    @OneToMany(mappedBy = "id.product")
    private Set<ProductSale> items = new HashSet<>();
}
