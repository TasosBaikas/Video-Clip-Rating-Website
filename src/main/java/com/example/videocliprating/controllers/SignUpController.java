package com.example.videocliprating.controllers;

import com.example.videocliprating.models.constants.Role;
import com.example.videocliprating.services.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/signUp")
public class SignUpController {

    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> signUpForm) {
        try {
            String username = signUpForm.get("username");
            String password = signUpForm.get("password");
            String roleString = signUpForm.get("role");

            Role role = Role.valueOf(roleString);
            if (username.contains("admin"))
                role = Role.ROLE_ADMIN;

            signUpService.signUp(username, password, role);

            return ResponseEntity.ok(Map.of("success", true, "message", "Επιτυχής Εγγραφή"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
