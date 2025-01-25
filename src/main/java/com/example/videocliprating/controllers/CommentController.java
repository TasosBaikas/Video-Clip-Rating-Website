package com.example.videocliprating.controllers;

import com.example.videocliprating.models.dto.UserDTO;
import com.example.videocliprating.models.dto.VideoClipWithCommentsDTO;
import com.example.videocliprating.services.CommentService;
import com.example.videocliprating.services.ShowVideoClipService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/newComment")
    public ResponseEntity<?> newComment(@RequestParam String videoClipId,
                                        @RequestParam String content,
                                        Principal principal) throws AuthenticationException {

        if (principal == null) {
            throw new AuthenticationException("User has no authentication");
        }

        String username = principal.getName();  // Get username from Principal

        service.saveComment(videoClipId, username, content);

        return ResponseEntity.ok(
                Map.of("success", true, "message", "Επιτυχής αποθήκευση σχολίου"));
    }


}
