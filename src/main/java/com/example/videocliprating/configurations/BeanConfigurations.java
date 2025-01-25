package com.example.videocliprating.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class BeanConfigurations {

    @Bean
    public Supplier<UUID> uuidSupplier() {
        return UUID::randomUUID; // Returns a new UUID each time it's called
    }

}
