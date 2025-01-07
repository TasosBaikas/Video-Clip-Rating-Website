package com.example.videocliprating.controllers;

import com.example.videocliprating.models.dto.VideoClipDTO;
import com.example.videocliprating.services.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UploadVideoClipController {

    private final HomeService homeService;

    public UploadVideoClipController(HomeService homeService) {
        this.homeService = homeService;
    }


    @GetMapping("/uploadVideoClip")
    public String home(Model model) {


        return "uploadVideoClip"; // Refers to src/main/resources/templates/index.html
    }

}
