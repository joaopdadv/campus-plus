package br.com.campus.campus.entity.auth.dto;

import br.com.campus.campus.service.AuthService;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private AuthService.UserRole role;

    public UserResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }

}