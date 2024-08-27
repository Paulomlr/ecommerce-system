package com.paulomlr.ecommerceSystem.services;

import com.paulomlr.ecommerceSystem.domain.Product;
import com.paulomlr.ecommerceSystem.domain.ProductSale;
import com.paulomlr.ecommerceSystem.domain.Sale;
import com.paulomlr.ecommerceSystem.domain.User;
import com.paulomlr.ecommerceSystem.domain.dto.sale.SaleRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.sale.SaleResponseDTO;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserResponseDTO;
import com.paulomlr.ecommerceSystem.domain.pk.ProductSalePK;
import com.paulomlr.ecommerceSystem.repositories.SaleRepository;
import com.paulomlr.ecommerceSystem.services.exceptions.DatabaseException;
import com.paulomlr.ecommerceSystem.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SaleService {

    private SaleRepository saleRepository;
    private UserService userService;
    private ProductService productService;

    public List<SaleResponseDTO> findAll() {
        return saleRepository.findAll()
                .stream().map(SaleResponseDTO::new)
                .toList();
    }

    public SaleResponseDTO findById(UUID id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found. Id: " + id));
        return new SaleResponseDTO(sale);
    }

    public Sale insert(SaleRequestDTO saleRequest) {

        User user = userService.findCompleteUserById(saleRequest.userId());
        if(user == null) {
            throw new ResourceNotFoundException("User not found. Id: " + saleRequest.userId());
        }

        Set<ProductSale> productSales = saleRequest.items().stream()
                .map(item -> {
                    Product product = productService.findCompleteProductById(item.productId());
                    if (product == null) {
                        throw new ResourceNotFoundException("Product not found. Id: " + item.productId());
                    }
                    return new ProductSale(product, item.quantity());
                })
                .collect(Collectors.toSet());

        Sale sale = new Sale(Instant.now(), user, productSales);

        productSales.forEach(productSale -> productSale.getId().setSale(sale));

        return saleRepository.save(sale);
    }

    public void delete(UUID id){
        Optional<Sale> sale = saleRepository.findById(id);
        if (sale.isPresent()) {
            saleRepository.delete(sale.get());
        } else {
            throw new ResourceNotFoundException("Sale not found. Id: " + id);
        }
    }

    public SaleResponseDTO update(UUID id, SaleRequestDTO obj) {
        return saleRepository.findById(id)
                .map(sale -> {
                    updateData(sale, obj);
                    Sale updateSale = saleRepository.save(sale);
                    return new SaleResponseDTO(updateSale);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found. Id: " + id));
    }

    private void updateData(Sale sale, SaleRequestDTO obj) {
        sale.getItems().clear();

        obj.items().forEach(itemDto -> {
            Product product = productService.findCompleteProductById(itemDto.productId());

            ProductSale productSale = new ProductSale(product, sale, itemDto.quantity());
            sale.getItems().add(productSale);
        });

        sale.setSaleDate(Instant.now());
    }
}
