package br.com.baba.api_product.api.controller;

import br.com.baba.api_product.api.dto.UserDetailDTO;
import br.com.baba.api_product.api.dto.UserFormDTO;
import br.com.baba.api_product.api.dto.UserUpdateDTO;
import br.com.baba.api_product.api.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity createUser(@Valid @RequestBody UserFormDTO userFormDTO, UriComponentsBuilder uriBuilder) {
        var user = userService.createUser(userFormDTO);
        var uri = uriBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDetailDTO(user));
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity getUserById(@PathVariable Long id) {
        var user = userService.getUserById(id);
        return ResponseEntity.ok(new UserDetailDTO(user));
    }

    @PutMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        var user = userService.updateUser(userUpdateDTO);
        return ResponseEntity.ok(new UserDetailDTO(user));
    }

}
