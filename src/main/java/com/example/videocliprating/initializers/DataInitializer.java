package com.example.videocliprating.initializers;

import com.example.videocliprating.models.Comment;
import com.example.videocliprating.models.User;
import com.example.videocliprating.models.VideoClip;
import com.example.videocliprating.models.constants.Role;
import com.example.videocliprating.repositories.CommentRepository;
import com.example.videocliprating.repositories.UserRepository;
import com.example.videocliprating.repositories.VideoClipRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, VideoClipRepository videoClipRepository, CommentRepository commentRepository, PasswordEncoder passwordEncoder) {
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
                    "123e4567-e89b-12d3-a456-426614174000", // A randomly generated UUID
                    "https://www.youtube.com/embed/4rgYUipGJNo", // Example YouTube trailer URL
                    "/images/gladiator_image.jpg", // Placeholder image URL for Gladiator 2 poster
                    "Gladiator 2", // Movie title
                    "/images/hq720.avif", // Video clip file path
                    "Set years after the events of Gladiator, this film follows Lucius, the son of Lucilla, as he seeks to honor the legacy of Maximus.", // Realistic description
                    190, // Clip duration in seconds (2 minutes 30 seconds as a placeholder)
                    155, // Movie duration in minutes (estimated runtime for Gladiator 2)
                    LocalDateTime.of(2025, 5, 22, 12, 0) // Hypothetical release date in UTC
            );

            VideoClip video2 = new VideoClip(
                    "2asdasaidjasodjasoimdkasjdkasm",
                    "https://www.youtube.com/embed/6UXGo5PFUA0",
                    "/images/superman_image.jpg",
                    "Superman (2025)",
                    "/images/supermanVideoClip.avif",
                    "Clark Kent faces his greatest challenge yet as he balances his Kryptonian heritage with the fate of humanity, directed by James Gunn.",
                    140, // Example clip duration in seconds (e.g., 5 minutes)
                    155, // Estimated movie duration in minutes (e.g., 2 hours and 35 minutes)
                    LocalDateTime.of(2025, 7, 11, 0, 0) // Hypothetical release date in UTC
            );
//
//            VideoClip video3 = new VideoClip(
//                    "3asdasmdkasjdkasmadkajodkaskd",
//                    "https://www.youtube.com/embed/YZf8zpchbXE",
//                    "/images/video_clip_logo.png",
//                    "Romantic Comedy",
//                    "/images/video_clip_logo.png",
//                    "A romantic comedy that will leave you laughing.",
//                    480,
//                    135,
//                    LocalDateTime.now().minusHours(3)
//            );


            videoClipRepository.saveAll(List.of(video1, video2));
//            videoClipRepository.save(video1);

            Comment comment = new Comment(UUID.randomUUID().toString(), video1, user2, "hello", LocalDateTime.now().minusDays(2).minusMinutes(20).minusHours(1));

            commentRepository.save(comment);
        };
    }
}
