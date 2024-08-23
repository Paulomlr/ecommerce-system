package com.paulomlr.ecommerceSystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "tb_category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "categoryId")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID categoryId;

    @Setter
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();
}
