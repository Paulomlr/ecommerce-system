package com.paulomlr.ecommerceSystem.services;

import com.paulomlr.ecommerceSystem.domain.Category;
import com.paulomlr.ecommerceSystem.repositories.CategoryRepository;
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
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Category insert(Category category){
        return categoryRepository.save(category);
    }

    public void delete(UUID id){
        try {
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Category update(UUID id, Category obj) {
        try {
            Category category = categoryRepository.getReferenceById(id);
            updateData(category, obj);
            return categoryRepository.save(category);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Category category, Category obj) {
        category.setName(obj.getName());
    }
}
