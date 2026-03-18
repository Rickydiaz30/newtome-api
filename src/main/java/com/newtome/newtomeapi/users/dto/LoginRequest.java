package com.newtome.newtomeapi.users.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message="Username is Required")
    private String username;

    @NotBlank(message="Password is Required")
    private String password;

    public String getUsername() {return username;}
    public void setUsername(String userName) {this.username = userName;}

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
