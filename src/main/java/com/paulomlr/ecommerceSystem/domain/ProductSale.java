package com.paulomlr.ecommerceSystem.domain;

import com.paulomlr.ecommerceSystem.domain.pk.ProductSalePK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "tb_product_sale")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductSale {

    @EmbeddedId
    private ProductSalePK id = new ProductSalePK();

    private Integer quantity;
    private Double price;

    public ProductSale(Product product, Sale sale, Integer quantity, Double price) {
        id.setProduct(product);
        id.setSale(sale);
        this.quantity = quantity;
        this.price = price;
    }
}
