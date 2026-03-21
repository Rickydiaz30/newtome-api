package com.newtome.newtomeapi.users.controller;

import com.newtome.newtomeapi.common.ApiResponse;
import com.newtome.newtomeapi.users.dto.LoginRequest;
import com.newtome.newtomeapi.users.dto.RegisterRequest;
import com.newtome.newtomeapi.users.model.User;
import com.newtome.newtomeapi.users.service.AuthService;
import com.newtome.newtomeapi.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {

        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/whoami")
    public ResponseEntity<?> whoami() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        java.util.HashMap<String, Object> body = new java.util.HashMap<>();
        body.put("authenticated", false);
        body.put("principal", null);

        if (auth == null) {
            return ResponseEntity.ok(body);
        }

        Object principalObj = auth.getPrincipal();
        if (principalObj == null || "anonymousUser".equals(principalObj)) {
            return ResponseEntity.ok(body);
        }

        String principal;
        if (principalObj instanceof String s) {
            principal = s;
        } else if (principalObj instanceof UserDetails ud) {
            principal = ud.getUsername();
        } else {
            principal = principalObj.toString();
        }

        body.put("authenticated", true);
        body.put("principal", principal);
        body.put("authType", auth.getClass().getSimpleName());

        return ResponseEntity.ok(body);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest req) {

        User saved = userService.register(req);

        return ResponseEntity
                .created(URI.create("/api/users/" + saved.getId()))
                .body(new ApiResponse<>(true, "User registered", null));
    }



}
