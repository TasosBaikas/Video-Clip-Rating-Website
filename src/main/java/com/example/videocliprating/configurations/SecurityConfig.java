package com.example.videocliprating.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/", "/home", "/signup", "/static/**", "/login", "/css/**", "/js/**", "/images/**", "/webjars/**") // Updated from antMatchers to requestMatchers
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // Custom login page
                .permitAll()
                .and()
                .logout()
                .permitAll(); // Allow logout

//                .and()
//                .csrf()
//                .ignoringRequestMatchers("/css/**", "/images/**", "/webjars/**"); // All other paths require authentication




        return http.build();
    }

    //        .logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout")
//                .permitAll();

    // Password encoder bean for hashing passwords (you can choose another encoder)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    // Optionally configure a custom UserDetailsService (this is just an example)
//    @Bean
//    public UserDetailsService userDetailsService() {
//        JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
//        // Configure user details service (e.g., JDBC-based, in-memory, etc.)
//        return userDetailsService;
//    }
}
