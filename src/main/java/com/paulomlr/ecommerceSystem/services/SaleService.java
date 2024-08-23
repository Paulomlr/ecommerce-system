package com.paulomlr.ecommerceSystem.services;

import com.paulomlr.ecommerceSystem.domain.Sale;
import com.paulomlr.ecommerceSystem.repositories.SaleRepository;
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
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Sale findById(UUID id) {
        Optional<Sale> Sale = saleRepository.findById(id);
        return Sale.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Sale insert(Sale Sale){
        return saleRepository.save(Sale);
    }

    public void delete(UUID id){
        try {
            saleRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Sale update(UUID id, Sale obj) {
        try {
            Sale Sale = saleRepository.getReferenceById(id);
            updateData(Sale, obj);
            return saleRepository.save(Sale);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Sale sale, Sale obj) {
        sale.setSaleStatus(obj.getSaleStatus());
    }
}
