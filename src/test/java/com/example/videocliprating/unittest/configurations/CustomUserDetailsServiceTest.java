package com.example.videocliprating.unittest.configurations;


import com.example.videocliprating.configurations.CustomUserDetailsService;
import com.example.videocliprating.models.User;
import com.example.videocliprating.models.constants.Role;
import com.example.videocliprating.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Test
    void testLoadUserByUsername_UserFound() {
        // Mock repository
        UserRepository userRepository = mock(UserRepository.class);
        User mockUser = new User("john", "password", Role.ROLE_VISITOR);

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(mockUser));

        // Inject mock into the service
        CustomUserDetailsService service = new CustomUserDetailsService(userRepository);

        // Test
        UserDetails userDetails = service.loadUserByUsername("john");

        assertEquals("john", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_VISITOR")));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Mock repository
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // Inject mock into the service
        CustomUserDetailsService service = new CustomUserDetailsService(userRepository);

        // Test
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("unknown"));
    }
}
