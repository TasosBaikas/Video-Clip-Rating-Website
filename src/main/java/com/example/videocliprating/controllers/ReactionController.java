package com.example.videocliprating.controllers;

import com.example.videocliprating.models.dto.VideoClipWithCommentsDTO;
import com.example.videocliprating.models.requests.ReactionRequest;
import com.example.videocliprating.services.ReactionService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    private final ReactionService service;

    public ReactionController(ReactionService service) {
        this.service = service;
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/like")
    public ResponseEntity<?> likeVideo(@RequestBody ReactionRequest request, Principal principal) throws AuthenticationException {
        if (principal == null) {
            throw new AuthenticationException("Ο χρήστης δεν έχει πρόσβαση");
        }

        String username = principal.getName(); // Get the currently logged-in user's username
        String videoClipId = request.getVideoClipId();

        boolean liked = service.handleLikeReaction(username, videoClipId);

        boolean disliked = service.isVideoClipDisliked(username, videoClipId);

        VideoClipWithCommentsDTO videoClipWithComments = service.getVideoClipWithCommentsById(videoClipId);

        String message;
        if (liked)
            message = "Κάνατε Like";
        else
            message = "Αφαιρέσατε το Like";

        return ResponseEntity.ok(
                Map.of("success", true,
                        "message", message,
                        "liked", liked,
                        "disliked", disliked,
                        "videoClipWithComments", videoClipWithComments
                ));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/dislike")
    public ResponseEntity<?> dislikeVideo(@RequestBody ReactionRequest request, Principal principal) throws AuthenticationException {
        if (principal == null) {
            throw new AuthenticationException("Ο χρήστης δεν έχει πρόσβαση");
        }

        String username = principal.getName(); // Get the currently logged-in user's username
        String videoClipId = request.getVideoClipId();

        boolean disliked = service.handleDislikeReaction(username, videoClipId);

        boolean liked = service.isVideoClipLiked(username, videoClipId);

        // Fetch the updated video clip with comments
        VideoClipWithCommentsDTO videoClipWithComments = service.getVideoClipWithCommentsById(videoClipId);


        String message;
        if (disliked)
            message = "Κάνατε Dislike";
        else
            message = "Αφαιρέσατε το Dislike";


        return ResponseEntity.ok(
                Map.of("success", true,
                        "message", message,
                        "liked", liked,
                        "disliked", disliked,
                        "videoClipWithComments", videoClipWithComments
                ));
    }

}
