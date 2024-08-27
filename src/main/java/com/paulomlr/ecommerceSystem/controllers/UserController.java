package com.paulomlr.ecommerceSystem.controllers;

import com.paulomlr.ecommerceSystem.domain.User;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserResponseDTO;
import com.paulomlr.ecommerceSystem.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<UserResponseDTO> usersList = service.findAll();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable @NotNull UUID id) {
        UserResponseDTO user = service.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody @Valid UserRequestDTO body){
        var user = new User(body);
        User savedUser = service.insert(user);
        URI location = URI.create("/users/" + savedUser.getUserId());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable @NotNull UUID id, @RequestBody @Valid UserRequestDTO body){
        UserResponseDTO userResponse = service.update(id, body);
        return ResponseEntity.ok().body(userResponse);
    }
}


