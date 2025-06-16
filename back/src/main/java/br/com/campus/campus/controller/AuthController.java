// AuthController.java
package br.com.campus.campus.controller;

import br.com.campus.campus.entity.auth.User;
import br.com.campus.campus.entity.auth.dto.AuthRequest;
import br.com.campus.campus.entity.auth.dto.AuthResponse;
import br.com.campus.campus.entity.auth.dto.UserResponse;
import br.com.campus.campus.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @RequestBody AuthRequest req
    ) throws AuthenticationException {
        AuthResponse resp = authService.login(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
        @RequestBody User user
    ) throws IllegalArgumentException {
        UserResponse resp = authService.register(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resp);
    }
}
