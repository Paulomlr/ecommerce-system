package com.paulomlr.ecommerceSystem.controllers;

import com.paulomlr.ecommerceSystem.config.security.TokenService;
import com.paulomlr.ecommerceSystem.domain.User;
import com.paulomlr.ecommerceSystem.domain.dto.user.LoginResponseDTO;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserRequestDTO;
import com.paulomlr.ecommerceSystem.domain.dto.user.UserResponseDTO;
import com.paulomlr.ecommerceSystem.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<UserResponseDTO> usersList = service.findAll();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        UserResponseDTO user = service.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid UserRequestDTO body){
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.login(), body.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRequestDTO body){
        User registerUser = service.registerUser(body);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registerUser.getUserId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Void> registerAdmin(@RequestBody @Valid UserRequestDTO body){
        User registerUser = service.registerAdmin(body);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registerUser.getUserId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid UserRequestDTO body){
        UserResponseDTO userResponse = service.update(id, body);
        return ResponseEntity.ok().body(userResponse);
    }
}


