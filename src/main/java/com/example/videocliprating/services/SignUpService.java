package com.example.videocliprating.services;

import com.example.videocliprating.models.User;
import com.example.videocliprating.models.constants.Role;
import com.example.videocliprating.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public SignUpService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void signUp(String username, String password, Role role) {
        // Check if the username is already taken
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Ο χρήστης υπάρχει ήδη");
        }

        // Save the user to the database
        User user = new User(username, passwordEncoder.encode(password), role);

        userRepository.save(user);

        // Authenticate the user
        authenticateAndSetSession(username, password);
    }

    private void authenticateAndSetSession(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
