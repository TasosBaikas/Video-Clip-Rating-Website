package com.example.videocliprating.controllers;

import com.example.videocliprating.services.UploadVideoClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/uploadVideoClip")
public class UploadVideoClipController {


    private UploadVideoClipService service;



    @Autowired
    public UploadVideoClipController(UploadVideoClipService service) {
        this.service = service;
    }


    @GetMapping
    public String home(Model model) {

        return "uploadVideoClip";
    }


    @PostMapping
    public String uploadVideoClip(
            @RequestParam("videoClipImage") MultipartFile videoClipImage,
            @RequestParam("image") MultipartFile image,
            @RequestParam("youtubeUrl") String youtubeUrl,
            @RequestParam("movieTitle") String movieTitle,
            @RequestParam("description") String description,
            @RequestParam("durationInSeconds") int durationInSeconds,
            @RequestParam("movieDurationInMinutes") int movieDurationInMinutes,
            @RequestParam("releaseTimeInAthensTime") LocalDateTime releaseTimeInAthensTime
    ) throws IOException {

        service.handleSaveVideoClip(videoClipImage, image, youtubeUrl, movieTitle, description,
                durationInSeconds, movieDurationInMinutes, releaseTimeInAthensTime);


        return "redirect:/";
    }


}
