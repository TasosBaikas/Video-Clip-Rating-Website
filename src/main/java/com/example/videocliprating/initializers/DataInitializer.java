package com.example.videocliprating.initializers;

import com.example.videocliprating.models.User;
import com.example.videocliprating.models.VideoClip;
import com.example.videocliprating.models.constants.Role;
import com.example.videocliprating.repositories.UserRepository;
import com.example.videocliprating.repositories.VideoClipRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, VideoClipRepository videoClipRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Initialize Users
            User user = new User();
            user.setUsername("john");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(Role.ROLE_VISITOR);
            userRepository.save(user);

            User user2 = new User("john2", passwordEncoder.encode("password"), Role.ROLE_RATER);
            userRepository.save(user2);

            User user3 = new User("john3", passwordEncoder.encode("password"), Role.ROLE_UPLOADER);
            userRepository.save(user3);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpass"));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);

            // Initialize Video Clips
            VideoClip video1 = new VideoClip(
                    "1asdasmdkasjdkasm",
                    "https://www.youtube.com/embed/YZf8zpchbXE",
                    "/images/gladiator_image.jpg",
                    "Epic Adventure",
                    "/images/gladiator_image.jpg",
                    "An epic journey of adventure and discovery.",
                    360,
                    120,
                    LocalDateTime.now().minusHours(1)
            );

            VideoClip video2 = new VideoClip(
                    "2asdasaidjasodjasoimdkasjdkasm",
                    "https://www.youtube.com/embed/YZf8zpchbXE",
                    "/images/superman_image.jpg",
                    "Sci-Fi Thriller",
                    "/images/superman_image.jpg",
                    "A gripping sci-fi thriller that keeps you on the edge.",
                    540,
                    140,
                    LocalDateTime.now().minusHours(2)
            );

            VideoClip video3 = new VideoClip(
                    "3asdasmdkasjdkasmadkajodkaskd",
                    "https://www.youtube.com/embed/YZf8zpchbXE",
                    "/images/video_clip_logo.png",
                    "Romantic Comedy",
                    "/images/video_clip_logo.png",
                    "A romantic comedy that will leave you laughing.",
                    480,
                    135,
                    LocalDateTime.now().minusHours(3)
            );


            videoClipRepository.saveAll(List.of(video1, video2, video3));
        };
    }
}
