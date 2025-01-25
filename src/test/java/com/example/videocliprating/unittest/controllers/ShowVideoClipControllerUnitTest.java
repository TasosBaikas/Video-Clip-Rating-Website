package com.example.videocliprating.unittest.controllers;

import com.example.videocliprating.controllers.ShowVideoClipController;
import com.example.videocliprating.models.Comment;
import com.example.videocliprating.models.dto.UserDTO;
import com.example.videocliprating.models.constants.Role;
import com.example.videocliprating.models.dto.VideoClipWithCommentsDTO;
import com.example.videocliprating.services.ShowVideoClipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShowVideoClipControllerUnitTest {

    @Mock
    private ShowVideoClipService service;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private ShowVideoClipController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowVideoClipForAnonymousUser() {
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

        // Act
        String viewName = controller.showVideoClip(movieTitle, model, null);

        // Assert
        assertEquals("showVideoClip", viewName);
        verify(model, times(1)).addAttribute("videoClipWithComments", videoClipWithComments);
        verifyNoMoreInteractions(model);
    }

    @Test
    public void testShowVideoClipForAuthenticatedUser() {
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

        UserDTO user = new UserDTO("testUser", Role.ROLE_VISITOR);

        when(service.getVideoClipWithCommentsByMovieTitle(movieTitle)).thenReturn(videoClipWithComments);
        when(principal.getName()).thenReturn(username);
        when(service.getUserByUsername(username)).thenReturn(user);
        when(service.isVideoClipLiked(username, videoClipWithComments.getUuid())).thenReturn(true);
        when(service.isVideoClipDisliked(username, videoClipWithComments.getUuid())).thenReturn(false);

        // Act
        String viewName = controller.showVideoClip(movieTitle, model, principal);

        // Assert
        assertEquals("showVideoClip", viewName);
        verify(model, times(1)).addAttribute("videoClipWithComments", videoClipWithComments);
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("hasLiked", true);
        verify(model, times(1)).addAttribute("hasDisliked", false);
    }

    @Test
    public void testShowVideoClipHandlesNullVideoClip() {
        // Arrange
        String movieTitle = "Nonexistent Movie";

        when(service.getVideoClipWithCommentsByMovieTitle(movieTitle)).thenReturn(null);

        // Act
        String viewName = controller.showVideoClip(movieTitle, model, null);

        // Assert
        assertEquals("showVideoClip", viewName);
        verify(model, times(1)).addAttribute("videoClipWithComments", null);
        verifyNoMoreInteractions(model);
    }

}
