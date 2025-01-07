package com.example.videocliprating.controllers;


import com.example.videocliprating.models.dto.UserDTO;
import com.example.videocliprating.models.dto.VideoClipWithCommentsDTO;
import com.example.videocliprating.services.ShowVideoClipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class ShowVideoClipController {

    private final ShowVideoClipService service;

    public ShowVideoClipController(ShowVideoClipService service) {
        this.service = service;
    }


    @GetMapping("/showVideoClip/{movieTitle}")
    public String showVideoClip(@PathVariable("movieTitle") String movieTitle, Model model, Principal principal) {
        // Fetch video clips using the movie title
        VideoClipWithCommentsDTO videoClipWithComments = service.getVideoClipWithCommentsByMovieTitle(movieTitle);

        model.addAttribute("videoClipWithComments", videoClipWithComments);

        if (principal == null){
            return "showVideoClip"; // Refers to src/main/resources/templates/showVideoClip.html
        }

        String username = principal.getName();

        UserDTO user = service.getUserByUsername(username);

        model.addAttribute("user", user);

        boolean hasLikedTheVideoClip = service.isVideoClipLiked(username, videoClipWithComments.getUuid());
        model.addAttribute("hasLiked", hasLikedTheVideoClip);

        boolean hasDislikedTheVideoClip = service.isVideoClipDisliked(username, videoClipWithComments.getUuid());
        model.addAttribute("hasDisliked", hasDislikedTheVideoClip);


        return "showVideoClip"; // Refers to src/main/resources/templates/showVideoClip.html
    }

}
