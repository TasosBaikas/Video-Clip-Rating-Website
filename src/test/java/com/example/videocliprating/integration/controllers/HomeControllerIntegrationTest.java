package com.example.videocliprating.integration.controllers;

import com.example.videocliprating.models.dto.VideoClipDTO;
import com.example.videocliprating.services.HomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HomeService homeService;

    @BeforeEach
    public void setUp() {
        // Mock data setup for tests
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
    }

    @Test
    public void testHomePageLoadsSuccessfully() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("videoClips"));
    }

    @Test
    public void testHomePageDisplaysCorrectVideoClips() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("videoClips", homeService.getAllVideoClips()));
    }

    @Test
    public void testHomePageAccessibleThroughAlternatePaths() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("videoClips"));

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("videoClips"));
    }
}
