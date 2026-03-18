package com.newtome.newtomeapi.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Size(max = 120)
    private String firstName;

    @NotBlank
    @Size(max = 120)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 30)
    private String username;

    @NotBlank
    @Email
    @Size(max = 190)
    private String email;

    @NotBlank
    @Size(min = 8, max = 72)
    private String password;

    @Size(max = 30)
    private String phone;

    @Size(max = 255)
    private String streetAddress;

    @Size(max = 120)
    private String city;

    @Size(max = 50)
    private String state;

    @Size(max = 10)
    private String zipCode;

    // getters + setters...

    public @NotBlank @Size(max = 120) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank @Size(max = 120) String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank @Size(max = 120) String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank @Size(max = 120) String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank @Size(min = 3, max = 30) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 30) String username) {
        this.username = username;
    }

    public @NotBlank @Email @Size(max = 190) String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email @Size(max = 190) String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 8, max = 72) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 8, max = 72) String password) {
        this.password = password;
    }

    public @Size(max = 30) String getPhone() {
        return phone;
    }

    public void setPhone(@Size(max = 30) String phone) {
        this.phone = phone;
    }

    public @Size(max = 255) String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(@Size(max = 255) String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public @Size(max = 120) String getCity() {
        return city;
    }

    public void setCity(@Size(max = 120) String city) {
        this.city = city;
    }

    public @Size(max = 50) String getState() {
        return state;
    }

    public void setState(@Size(max = 50) String state) {
        this.state = state;
    }

    public @Size(max = 10) String getZipCode() {
        return zipCode;
    }

    public void setZipCode(@Size(max = 10) String zipCode) {
        this.zipCode = zipCode;
    }
}