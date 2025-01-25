package com.example.videocliprating.unittest.services;


import com.example.videocliprating.exceptions.customexceptions.ResourceAlreadyExistsException;
import com.example.videocliprating.helpers.TimeUtil;
import com.example.videocliprating.models.VideoClip;
import com.example.videocliprating.repositories.VideoClipRepository;
import com.example.videocliprating.services.FileUploadService;
import com.example.videocliprating.services.UploadVideoClipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UploadVideoClipServiceTest {

    @InjectMocks
    private UploadVideoClipService uploadVideoClipService;

    @Mock
    private FileUploadService fileUploadService;

    @Mock
    private Supplier<UUID> uuidSupplier;

    @Mock
    private TimeUtil timeUtil;

    @Mock
    private VideoClipRepository videoClipRepository;

    @Mock
    private MultipartFile videoClipImage;

    @Mock
    private MultipartFile movieImage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleSaveVideoClip_Success() throws IOException, ResourceAlreadyExistsException {
        // Arrange
        String movieTitle = "Test Movie";
        String youtubeUrl = "https://youtube.com/watch?v=test123";
        String description = "Description";
        int durationInSeconds = 120;
        int movieDurationInMinutes = 90;
        LocalDateTime releaseTimeInAthensTime = LocalDateTime.now();
        String randomString = "mockUUID123";
        String imagePath = "movieImagePath.jpg";
        String videoClipImagePath = "videoClipImagePath.jpg";
        UUID mockUUID = UUID.randomUUID();

        when(uuidSupplier.get()).thenReturn(mockUUID);
        when(videoClipRepository.getVideoClipByMovieTitle(movieTitle)).thenReturn(Optional.empty());
        when(fileUploadService.uploadFile(movieImage, randomString)).thenReturn(imagePath);
        when(fileUploadService.uploadFile(videoClipImage, randomString)).thenReturn(videoClipImagePath);
        when(timeUtil.convertAthensToUTC(releaseTimeInAthensTime)).thenReturn(LocalDateTime.now());

        // Act
        uploadVideoClipService.handleSaveVideoClip(
                videoClipImage, movieImage, youtubeUrl, movieTitle, description,
                durationInSeconds, movieDurationInMinutes, releaseTimeInAthensTime
        );

        // Assert
        verify(videoClipRepository, times(1)).save(any(VideoClip.class));
    }


    @Test
    void handleSaveVideoClip_NullVideoClipImage() {
        // Act & Assert
        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> uploadVideoClipService.handleSaveVideoClip(
                        null, movieImage, "https://youtube.com/watch?v=test123", "Test Movie",
                        "Description", 120, 90, LocalDateTime.now()
                )
        );

        assertEquals("Δεν υπάρχει εικόνα για το video clip", exception.getMessage());
    }

    @Test
    void handleSaveVideoClip_NullMovieImage() {
        // Act & Assert
        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> uploadVideoClipService.handleSaveVideoClip(
                        videoClipImage, null, "https://youtube.com/watch?v=test123", "Test Movie",
                        "Description", 120, 90, LocalDateTime.now()
                )
        );

        assertEquals("Δεν υπάρχει η εικόνα της ταινίας", exception.getMessage());
    }

    @Test
    void parseYoutubeUrl_ValidWatchUrl() {
        // Arrange
        String youtubeUrl = "https://youtube.com/watch?v=test123";

        // Act
        String result = uploadVideoClipService.parseYoutubeUrl(youtubeUrl);

        // Assert
        assertEquals("test123", result);
    }

    @Test
    void parseYoutubeUrl_ValidEmbedUrl() {
        // Arrange
        String youtubeUrl = "https://youtube.com/embed/test123";

        // Act
        String result = uploadVideoClipService.parseYoutubeUrl(youtubeUrl);

        // Assert
        assertEquals("test123", result);
    }

    @Test
    void parseYoutubeUrl_InvalidUrl() {
        // Arrange
        String youtubeUrl = "https://invalid.com";

        // Act & Assert
        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> uploadVideoClipService.parseYoutubeUrl(youtubeUrl)
        );

        assertEquals("Το youtube url δεν είναι σε σωστή μορφή", exception.getMessage());
    }
}
