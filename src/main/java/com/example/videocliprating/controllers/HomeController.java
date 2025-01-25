package com.example.videocliprating.controllers;

import com.example.videocliprating.models.dto.VideoClipDTO;
import com.example.videocliprating.services.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }


    @GetMapping({"/", "/home", "/login"})
    public String home(Model model) {
        // Fetch video clips as DTOs
        List<VideoClipDTO> videoClips = homeService.getAllVideoClips();

        // Add video clips to the model
        model.addAttribute("videoClips", videoClips);

        return "index"; // Refers to src/main/resources/templates/index.html
    }

}
