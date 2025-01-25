package com.example.videocliprating.integration.controllers;

import com.example.videocliprating.repositories.VideoClipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UploadVideoClipControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private VideoClipRepository realVideoClipRepository;

    @MockBean
    private VideoClipRepository mockVideoClipRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    void testUploadVideoClip_RealDBCall_Success() throws Exception {
        // This test uses the real repository
        MockMultipartFile videoClipImage = new MockMultipartFile(
                "videoClipImage", "video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "test data".getBytes()
        );
        MockMultipartFile image = new MockMultipartFile(
                "image", "image.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "test data".getBytes()
        );

        String randomizedMovieTitle = "blabla" + UUID.randomUUID();

        mockMvc.perform(multipart("/uploadVideoClip")
                .file(videoClipImage)
                .file(image)
                .param("youtubeUrl", "https://youtube.com/watch?v=test123")
                .param("movieTitle", randomizedMovieTitle)
                .param("description", "This is a test movie")
                .param("durationInSeconds", "120")
                .param("movieDurationInMinutes", "90")
                .param("releaseTimeInAthensTime", "2025-01-16T21:34")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isFound()) // 302 for redirect
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testUploadVideoClip_Success_NoDBSave() throws Exception {
        // Mock repository behavior to avoid database interaction
        when(mockVideoClipRepository.save(any())).thenAnswer(invocation -> null);

        MockMultipartFile videoClipImage = new MockMultipartFile(
                "videoClipImage", "video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "test data".getBytes()
        );
        MockMultipartFile image = new MockMultipartFile(
                "image", "image.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "test data".getBytes()
        );


        String randomizedMovieTitle = "blabla" + UUID.randomUUID();

        mockMvc.perform(multipart("/uploadVideoClip")
                .file(videoClipImage)
                .file(image)
                .param("youtubeUrl", "https://youtube.com/watch?v=test123")
                .param("movieTitle", randomizedMovieTitle)
                .param("description", "This is a test movie")
                .param("durationInSeconds", "120")
                .param("movieDurationInMinutes", "90")
                .param("releaseTimeInAthensTime", "2025-01-16T21:34")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isFound()) // 302 for redirect
                .andExpect(redirectedUrl("/"));

        // Ensure save is never actually called
        verify(mockVideoClipRepository, times(1)).save(any());
    }
}
