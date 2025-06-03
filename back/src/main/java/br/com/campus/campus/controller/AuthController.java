import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.exemplo.auth.dto.AuthRequest;
import com.exemplo.auth.dto.AuthResponse;
import com.exemplo.auth.entity.User;
import com.exemplo.auth.repository.UserRepository;
import com.exemplo.auth.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        User user = repo.findByUsername(request.username()).orElseThrow();
        String jwt = jwtService.generateToken((UserDetails) auth.getPrincipal());
        return new AuthResponse(jwt);
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        String hash = new BCryptPasswordEncoder().encode(request.password());
        repo.save(new User(null, request.username(), hash, "USER"));
        return "User created!";
    }
}
