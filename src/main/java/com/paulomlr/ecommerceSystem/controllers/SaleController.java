package com.paulomlr.ecommerceSystem.controllers;

import com.paulomlr.ecommerceSystem.domain.Product;
import com.paulomlr.ecommerceSystem.domain.ProductSale;
import com.paulomlr.ecommerceSystem.domain.Sale;
import com.paulomlr.ecommerceSystem.domain.User;
import com.paulomlr.ecommerceSystem.domain.dto.product.ProductRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.productSale.ProductSaleRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.sale.SaleRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.sale.SaleResponseDTO;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserResponseDTO;
import com.paulomlr.ecommerceSystem.domain.enums.SaleStatus;
import com.paulomlr.ecommerceSystem.domain.pk.ProductSalePK;
import com.paulomlr.ecommerceSystem.services.ProductService;
import com.paulomlr.ecommerceSystem.services.SaleService;
import com.paulomlr.ecommerceSystem.services.UserService;
import com.paulomlr.ecommerceSystem.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService service;

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> findAll(){
        List<SaleResponseDTO> productList = service.findAll();
        return ResponseEntity.ok(productList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleResponseDTO> findById(@PathVariable @NotNull UUID id) {
        SaleResponseDTO sale = service.findById(id);
        return ResponseEntity.ok().body(sale);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody @Valid SaleRequestDTO body){
        Sale sale = service.insert(body);
        URI location = URI.create("/sales/" + sale.getSaleId());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SaleResponseDTO> update(@PathVariable @NotNull UUID id, @RequestBody @Valid SaleRequestDTO body) {
        SaleResponseDTO saleResponse = service.update(id, body);
        return ResponseEntity.ok().body(saleResponse);
    }
}
