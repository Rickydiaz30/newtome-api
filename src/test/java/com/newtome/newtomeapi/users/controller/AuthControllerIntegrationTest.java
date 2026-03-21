package com.newtome.newtomeapi.users.controller;

import com.newtome.newtomeapi.catalog.repository.ListingRepository;
import com.newtome.newtomeapi.catalog.repository.OfferRepository;
import com.newtome.newtomeapi.users.model.User;
import com.newtome.newtomeapi.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AuthControllerIntegrationTest {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private OfferRepository offerRepository;

    @BeforeEach
    void cleanDb() {
        offerRepository.deleteAll();
        listingRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void login_shouldReturnToken_andUpdateLastLogin() throws Exception {


        // given - create user in DB
        User user = new User();
        user.setUsername("user" + System.currentTimeMillis());
        user.setPasswordHash(passwordEncoder.encode("password"));
        user.setEmail("test" + System.currentTimeMillis() + "@test.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole("ROLE_USER");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        String json = """
    {
        "username": "%s",
        "password": "password"
    }
    """.formatted(user.getUsername());

        // when
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());


        // then - verify DB updated
        User updated = userRepository
                .findByUsernameIgnoreCase(user.getUsername())
                .orElseThrow();

        assertNotNull(updated.getLastLoginAt());
    }
}
