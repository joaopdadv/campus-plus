// AuthController.java
package br.com.campus.campus.controller;

import br.com.campus.campus.entity.auth.User;
import br.com.campus.campus.entity.auth.dto.AuthRequest;
import br.com.campus.campus.entity.auth.dto.AuthResponse;
import br.com.campus.campus.entity.auth.dto.UserResponse;
import br.com.campus.campus.service.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${app.jwt.cookie-name}")
    private String jwtCookieName;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @RequestBody AuthRequest req
    ) throws AuthenticationException {
        AuthResponse res = authService.login(req);

        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, res.getToken())
                .httpOnly(true)
                .path("/")
                .secure(false) // Defina como 'true' em produção (HTTPS)
                .maxAge(24 * 60 * 60) // Expiração em segundos (24 horas)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(res);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, null)
                .httpOnly(true)
                .path("/")
                .secure(false) // Deve corresponder às configurações do cookie de login
                .maxAge(0) // Expira o cookie imediatamente
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
        @RequestBody User user
    ) throws IllegalArgumentException {
        UserResponse res = authService.register(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(res);
    }
}
