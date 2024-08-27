package com.paulomlr.ecommerceSystem.services;

import com.paulomlr.ecommerceSystem.domain.Product;
import com.paulomlr.ecommerceSystem.domain.dto.product.ProductRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.product.ProductResponseDTO;
import com.paulomlr.ecommerceSystem.domain.enums.ProductStatus;
import com.paulomlr.ecommerceSystem.domain.enums.SaleStatus;
import com.paulomlr.ecommerceSystem.repositories.ProductRepository;
import com.paulomlr.ecommerceSystem.services.exceptions.DatabaseException;
import com.paulomlr.ecommerceSystem.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    public ProductResponseDTO findById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found. Id: " + id));
        return new ProductResponseDTO(product);
    }

    public Product findCompleteProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found. Id: " + id));
    }

    public Product insert(Product product){
        return productRepository.save(product);
    }

    public void delete(UUID id){
        try {
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Product not found. Id: " + id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ProductResponseDTO update(UUID id, ProductRequestDTO obj) {
        return productRepository.findById(id)
                .map(product -> {
                    updateData(product, obj);
                    Product updateProduct = productRepository.save(product);
                    return new ProductResponseDTO(updateProduct);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found. Id: " + id));
    }

    private void updateData(Product product, ProductRequestDTO obj) {
        product.setName(obj.name());
        product.setPrice(obj.price());
        product.setProductStatus(ProductStatus.ACTIVE);
    }
}
