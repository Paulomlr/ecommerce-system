package com.paulomlr.ecommerceSystem.controllers;

import com.paulomlr.ecommerceSystem.domain.Sale;
import com.paulomlr.ecommerceSystem.domain.dto.sale.SaleRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.sale.SaleResponseDTO;
import com.paulomlr.ecommerceSystem.services.SaleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService service;

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> findAll(){
        List<SaleResponseDTO> productList = service.findAll();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> findById(@PathVariable UUID id) {
        SaleResponseDTO sale = service.findById(id);
        return ResponseEntity.ok().body(sale);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<SaleResponseDTO>> findSalesByUser (@PathVariable UUID id) {
        List<SaleResponseDTO> sales = service.getSalesByUser(id);
        return ResponseEntity.ok(sales);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid SaleRequestDTO body){
        Sale sale = service.insert(body);
        URI location = URI.create("/sales/" + sale.getSaleId());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid SaleRequestDTO body) {
        SaleResponseDTO saleResponse = service.update(id, body);
        return ResponseEntity.ok().body(saleResponse);
    }

    @PutMapping("/{saleId}/cancel")
    public ResponseEntity<Void> cancelPurchase (@PathVariable UUID saleId) {
        service.cancelSale(saleId);
        return ResponseEntity.noContent().build();
    }
}
