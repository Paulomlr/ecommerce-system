package com.paulomlr.ecommerceSystem.services;

import com.paulomlr.ecommerceSystem.domain.Product;
import com.paulomlr.ecommerceSystem.repositories.ProductRepository;
import com.paulomlr.ecommerceSystem.services.exceptions.DatabaseException;
import com.paulomlr.ecommerceSystem.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(UUID id) {
        Optional<Product> Product = productRepository.findById(id);
        return Product.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Product insert(Product Product){
        return productRepository.save(Product);
    }

    public void delete(UUID id){
        try {
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Product update(UUID id, Product obj) {
        try {
            Product Product = productRepository.getReferenceById(id);
            updateData(Product, obj);
            return productRepository.save(Product);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Product product, Product obj) {
        product.setName(obj.getName());
        product.setPrice(obj.getPrice());
        product.setProductStatus(obj.getProductStatus());
    }
}
