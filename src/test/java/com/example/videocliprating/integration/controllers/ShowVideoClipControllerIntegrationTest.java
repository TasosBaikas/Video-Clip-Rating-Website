package com.example.videocliprating.integration.controllers;

import com.example.videocliprating.models.Comment;
import com.example.videocliprating.models.constants.Role;
import com.example.videocliprating.models.dto.UserDTO;
import com.example.videocliprating.models.dto.VideoClipWithCommentsDTO;
import com.example.videocliprating.services.ShowVideoClipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShowVideoClipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShowVideoClipService service;

    @Test
    public void testShowVideoClipForAnonymousUser() throws Exception {
        // Arrange
        String movieTitle = "Test Movie";
        VideoClipWithCommentsDTO videoClipWithComments = new VideoClipWithCommentsDTO(
                "uuid1",
                "https://youtube.com/video1",
                "https://image1.com",
                "https://clipimage1.com",
                "Description 1",
                "Test Movie",
                120,
                2,
                LocalDateTime.of(2025, 1, 10, 12, 0),
                100,
                10,
                Arrays.asList(new Comment(
                        "comment1",
                        null,
                        null,
                        "Great video!",
                        LocalDateTime.now()
                ))
        );

        when(service.getVideoClipWithCommentsByMovieTitle(movieTitle)).thenReturn(videoClipWithComments);

        // Act & Assert
        mockMvc.perform(get("/showVideoClip/{movieTitle}", movieTitle))
                .andExpect(status().isOk())
                .andExpect(view().name("showVideoClip"))
                .andExpect(model().attribute("videoClipWithComments", videoClipWithComments));
    }

    @Test
    public void testShowVideoClipForAuthenticatedUser() throws Exception {
        // Arrange
        String movieTitle = "Test Movie";
        String username = "testUser";

        VideoClipWithCommentsDTO videoClipWithComments = new VideoClipWithCommentsDTO(
                "uuid1",
                "https://youtube.com/video1",
                "https://image1.com",
                "https://clipimage1.com",
                "Description 1",
                "Test Movie",
                120,
                2,
                LocalDateTime.of(2025, 1, 10, 12, 0),
                100,
                10,
                Arrays.asList(new Comment(
                        "comment1",
                        null,
                        null,
                        "Great video!",
                        LocalDateTime.now()
                ))
        );

        UserDTO user = new UserDTO(username, Role.ROLE_VISITOR);

        when(service.getVideoClipWithCommentsByMovieTitle(movieTitle)).thenReturn(videoClipWithComments);
        when(service.getUserByUsername(username)).thenReturn(user);
        when(service.isVideoClipLiked(username, videoClipWithComments.getUuid())).thenReturn(true);
        when(service.isVideoClipDisliked(username, videoClipWithComments.getUuid())).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/showVideoClip/{movieTitle}", movieTitle)
                .with(SecurityMockMvcRequestPostProcessors.user(username)))
                .andExpect(status().isOk())
                .andExpect(view().name("showVideoClip"))
                .andExpect(model().attribute("videoClipWithComments", videoClipWithComments))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("hasLiked", true))
                .andExpect(model().attribute("hasDisliked", false));
    }
}
