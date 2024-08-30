package com.paulomlr.ecommerceSystem.services;

import com.paulomlr.ecommerceSystem.domain.User;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserResponseDTO;
import com.paulomlr.ecommerceSystem.domain.enums.UserRole;
import com.paulomlr.ecommerceSystem.repositories.UserRepository;
import com.paulomlr.ecommerceSystem.services.exceptions.ResourceNotFoundException;
import com.paulomlr.ecommerceSystem.services.exceptions.UniqueConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new ResourceNotFoundException("Login already exists."));
    }

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

//    public User findCompleteUserById(UUID id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found. Id: " + id));
//    }

    public User registerUser(UserRequestDTO body){
        return createUser(body, UserRole.USER);
    }

    public User registerAdmin(UserRequestDTO body) {
        return createUser(body, UserRole.ADMIN);
    }

    private User createUser(UserRequestDTO body, UserRole role) {
        if(userRepository.findByLogin(body.login()).isPresent()) {
            throw new UniqueConstraintViolationException("Login already exists.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        User user = new User(body.login(), encryptedPassword, role);

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
