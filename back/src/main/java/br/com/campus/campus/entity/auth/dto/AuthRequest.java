package br.com.campus.campus.entity.auth.dto;

import br.com.campus.campus.service.AuthService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequest {
    private String email;
    private String password;
    private AuthService.UserRole role;

    public AuthRequest() {
    }

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
