package br.com.baba.api_product.api.controller;

import br.com.baba.api_product.api.configuration.security.TokenService;
import br.com.baba.api_product.api.dto.DataTokenJWTDTO;
import br.com.baba.api_product.api.dto.LoginDTO;
import br.com.baba.api_product.api.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO) {
        var token = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
        var authenticationToken = manager.authenticate(token);
        var tokenJWT = tokenService.generateToken((User) authenticationToken.getPrincipal());
        return ResponseEntity.ok(new DataTokenJWTDTO(tokenJWT));
    }
}
