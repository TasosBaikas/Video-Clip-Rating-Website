package com.example.videocliprating.services;

import com.example.videocliprating.exceptions.customexceptions.ResourceAlreadyExistsException;
import com.example.videocliprating.helpers.TimeUtil;
import com.example.videocliprating.models.VideoClip;
import com.example.videocliprating.repositories.VideoClipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class UploadVideoClipService {

    private final Supplier<UUID> uuidSupplier;
    private final FileUploadService fileUploadService;
    private final TimeUtil timeUtil;
    private final VideoClipRepository videoClipRepository;

    @Autowired
    public UploadVideoClipService(Supplier<UUID> uuidSupplier, FileUploadService fileUploadService, TimeUtil timeUtil, VideoClipRepository videoClipRepository) {
        this.uuidSupplier = uuidSupplier;
        this.fileUploadService = fileUploadService;
        this.timeUtil = timeUtil;
        this.videoClipRepository = videoClipRepository;
    }

    public void handleSaveVideoClip(
            MultipartFile videoClipImage,
            MultipartFile movieImage,
            String youtubeUrlNeedsParsing,
            String movieTitle,
            String description,
            int durationInSeconds,
            int movieDurationInMinutes,
            LocalDateTime releaseTimeInAthensTime
    ) throws IOException, ResourceAlreadyExistsException {

        if (videoClipImage == null)
            throw new InvalidParameterException("Δεν υπάρχει εικόνα για το video clip");

        if (movieImage == null)
            throw new InvalidParameterException("Δεν υπάρχει η εικόνα της ταινίας");

        String uuid = uuidSupplier.get().toString();

        String youtubeUsefulDataFromUrl = parseYoutubeUrl(youtubeUrlNeedsParsing);

        String randomString = uuid.substring(0, 12);
        String imagePath = fileUploadService.uploadFile(movieImage, randomString);

        String videoClipImagePath = fileUploadService.uploadFile(videoClipImage, randomString);


        LocalDateTime movieReleaseTimeInUTC = timeUtil.convertAthensToUTC(releaseTimeInAthensTime);

        VideoClip videoClip = new VideoClip(uuid, youtubeUsefulDataFromUrl, imagePath, movieTitle,
                videoClipImagePath, description, durationInSeconds,
                movieDurationInMinutes, movieReleaseTimeInUTC);

        videoClipRepository.save(videoClip);
    }

    public String parseYoutubeUrl(String youtubeUrl) {

        if (youtubeUrl.contains("youtube.com/watch?v=")) {

            String vAndEqual = "v=";
            int indexOfvAndEqual = youtubeUrl.indexOf(vAndEqual);

            return youtubeUrl.substring(indexOfvAndEqual + vAndEqual.length());
        }

        if (youtubeUrl.contains("youtube.com/embed/")){
            String embedAndDash = "embed/";

            int indexOfEmbedAndDash = youtubeUrl.indexOf(embedAndDash);

            return youtubeUrl.substring(indexOfEmbedAndDash + embedAndDash.length());
        }

        throw new InvalidParameterException("Το youtube url δεν είναι σε σωστή μορφή");
    }

}

