package com.paulomlr.ecommerceSystem.controllers;

import com.paulomlr.ecommerceSystem.domain.Product;
import com.paulomlr.ecommerceSystem.domain.dto.product.ProductRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.product.ProductResponseDTO;
import com.paulomlr.ecommerceSystem.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll(){
        List<ProductResponseDTO> productList = service.findAll();
        return ResponseEntity.ok(productList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable @NotNull UUID id) {
       ProductResponseDTO product = service.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid ProductRequestDTO body){
        var product = new Product(body);
        Product savedProduct = service.insert(product);
        URI location = URI.create("/products/" + savedProduct.getProductId());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable @NotNull UUID id, @RequestBody @Valid ProductRequestDTO body){
        ProductResponseDTO productResponse = service.update(id, body);
        return ResponseEntity.ok().body(productResponse);
    }
}
