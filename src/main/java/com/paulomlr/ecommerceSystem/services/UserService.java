package com.paulomlr.ecommerceSystem.services;

import com.paulomlr.ecommerceSystem.domain.User;
import com.paulomlr.ecommerceSystem.repositories.UserRepository;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User user){
        return userRepository.save(user);
    }

    public void delete(UUID id){
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public User update(UUID id, User obj) {
        try {
            User user = userRepository.getReferenceById(id);
            updateData(user, obj);
            return userRepository.save(user);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User user, User obj) {
        user.setLogin(obj.getLogin());
        user.setPassword(obj.getPassword());
    }
}
