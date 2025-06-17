package br.com.campus.campus.entity.auth;

import br.com.campus.campus.service.AuthService;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING) // ou ORDINAL, se preferir salvar como número
    private AuthService.UserRole role = AuthService.UserRole.ALUNO;

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

}