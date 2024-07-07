package com.teste.daviugtsic.controller;

import com.teste.daviugtsic.controller.AuthDto;
import com.teste.daviugtsic.controller.LoginResponseDTO;
import com.teste.daviugtsic.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${api.security.admin.username}")
    private String adminUsername;

    @Value("${api.security.admin.password}")
    private String adminPassword;

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthDto data) {
        try {
            // Verifica se as credenciais correspondem ao admin local
            if (!data.getUsername().equals(adminUsername) || !data.getPassword().equals(adminPassword)) {
                return ResponseEntity.status(401).body(new LoginResponseDTO("Invalid credentials", null));
            }

            // Gera um token JWT
            String token = tokenService.generateToken(data.getUsername());

            // Retorna a resposta com token e outras informações, se necessário
            return ResponseEntity.ok(new LoginResponseDTO(token, null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new LoginResponseDTO("An unexpected error occurred", null));
        }
    }
}
