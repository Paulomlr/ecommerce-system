package com.paulomlr.ecommerceSystem.services;

import com.paulomlr.ecommerceSystem.domain.Product;
import com.paulomlr.ecommerceSystem.domain.User;
import com.paulomlr.ecommerceSystem.domain.dto.product.ProductRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.product.ProductResponseDTO;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserResponseDTO;
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

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public UserResponseDTO findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found. Id: " + id));
        return new UserResponseDTO(user);
    }

    public User findCompleteUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found. Id: " + id));
    }

    public User insert(User user){
        return userRepository.save(user);
    }

    public void delete(UUID id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new ResourceNotFoundException("User not found. Id: " + id);
        }
    }

    public UserResponseDTO update(UUID id, UserRequestDTO obj) {
        return userRepository.findById(id)
                .map(product -> {
                    updateData(product, obj);
                    User updateUser = userRepository.save(product);
                    return new UserResponseDTO(updateUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found. Id: " + id));
    }

    private void updateData(User user, UserRequestDTO obj) {
        user.setLogin(obj.login());
        user.setPassword(obj.password());
    }
}
