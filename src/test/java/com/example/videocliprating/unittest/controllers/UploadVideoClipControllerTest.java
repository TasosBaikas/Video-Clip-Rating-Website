package com.example.videocliprating.unittest.controllers;

import com.example.videocliprating.controllers.UploadVideoClipController;
import com.example.videocliprating.services.UploadVideoClipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = UploadVideoClipController.class)
public class UploadVideoClipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UploadVideoClipService service;


    @Test
    public void testUploadVideoClipSuccess() throws Exception {
        // Mock service behavior
        doNothing().when(service).handleSaveVideoClip(
                any(MultipartFile.class),
                any(MultipartFile.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(Integer.class),
                any(Integer.class),
                any(LocalDateTime.class)
        );

        // Create mock files
        MockMultipartFile videoClipImage = new MockMultipartFile(
                "videoClipImage", "video.mp4", "multipart/form-data", "test data".getBytes()
        );
        MockMultipartFile image = new MockMultipartFile(
                "image", "image.jpg", "multipart/form-data", "test data".getBytes()
        );

        // Perform the POST request with CSRF token
        mockMvc.perform(multipart("/uploadVideoClip")
                .file(videoClipImage)
                .file(image)
                .param("youtubeUrl", "https://youtube.com/watch2?v=test")
                .param("movieTitle", "Test Movie")
                .param("description", "This is a test movie")
                .param("durationInSeconds", "120")
                .param("movieDurationInMinutes", "2")
                .param("releaseTimeInAthensTime", "2025-01-16T21:34")
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // Add CSRF token
                .with(SecurityMockMvcRequestPostProcessors.user("testUser").roles("USER"))
        )
                .andExpect(status().isFound()) // 302 for redirect
                .andExpect(redirectedUrl("/"));
    }


    @Test
    public void testUploadVideoClipMissingFile() throws Exception {

        // Create mock image file only
        MockMultipartFile image = new MockMultipartFile(
                "image", "image.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "test data".getBytes()
        );

        // Perform the POST request without `videoClipImage`
        mockMvc.perform(multipart("/uploadVideoClip")
                .file(image)
                .param("youtubeUrl", "https://youtube.com/watch?v=test")
                .param("movieTitle", "Test Movie")
                .param("description", "This is a test movie")
                .param("durationInSeconds", "120")
                .param("movieDurationInMinutes", "2")
                .param("releaseTimeInAthensTime", "2025-01-16T21:34")
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // Add CSRF token
                .with(SecurityMockMvcRequestPostProcessors.user("testUser").roles("USER"))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Required part 'videoClipImage' is not present."));
    }
}
