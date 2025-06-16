package br.com.campus.campus.entity.auth.dto;

public class AuthResponse {
    private String token;
    private UserResponse user;

    public AuthResponse() {
    }

    public AuthResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}