package com.example.videocliprating.unittest.controllers;


import com.example.videocliprating.controllers.HomeController;
import com.example.videocliprating.models.dto.VideoClipDTO;
import com.example.videocliprating.services.HomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HomeControllerUnitTest {

    @Mock
    private HomeService homeService;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHomeMethodReturnsCorrectView() {
        // Arrange
        List<VideoClipDTO> mockClips = Arrays.asList(
                new VideoClipDTO(
                        "uuid1", "https://youtube.com/video1", "https://image1.com", "https://clipimage1.com",
                        "Description 1", "Movie 1", 120, 2, LocalDateTime.of(2025, 1, 10, 12, 0),
                        100, 10
                ),
                new VideoClipDTO(
                        "uuid2", "https://youtube.com/video2", "https://image2.com", "https://clipimage2.com",
                        "Description 2", "Movie 2", 150, 2, LocalDateTime.of(2025, 2, 15, 14, 0),
                        200, 20
                )
        );
        when(homeService.getAllVideoClips()).thenReturn(mockClips);

        // Act
        String viewName = homeController.home(model);

        // Assert
        assertEquals("index", viewName);
        verify(model, times(1)).addAttribute("videoClips", mockClips);
    }

    @Test
    public void testHomeMethodWithEmptyClips() {
        // Arrange
        when(homeService.getAllVideoClips()).thenReturn(List.of());

        // Act
        String viewName = homeController.home(model);

        // Assert
        assertEquals("index", viewName);
        verify(model, times(1)).addAttribute("videoClips", List.of());
    }

    @Test
    public void testHomeMethodIsCalledOnce() {
        // Arrange
        when(homeService.getAllVideoClips()).thenReturn(List.of());

        // Act
        homeController.home(model);

        // Assert
        verify(homeService, times(1)).getAllVideoClips();
    }
}
