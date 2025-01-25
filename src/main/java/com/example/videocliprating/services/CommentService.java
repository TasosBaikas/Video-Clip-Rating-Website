package com.example.videocliprating.services;

import com.example.videocliprating.helpers.TimeUtil;
import com.example.videocliprating.models.Comment;
import com.example.videocliprating.models.User;
import com.example.videocliprating.models.VideoClip;
import com.example.videocliprating.repositories.CommentRepository;
import com.example.videocliprating.repositories.UserRepository;
import com.example.videocliprating.repositories.VideoClipRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final VideoClipRepository videoClipRepository;
    private final UserRepository userRepository;
    private final Supplier<UUID> uuidSupplier;


    public void saveComment(String videoClipId, String username, String content){
        if (videoClipId == null || videoClipId.length() == 0)
            throw new IllegalArgumentException("Δεν δώθηκε video clip id");


        if (content == null || content.trim().length() == 0)
            throw new IllegalArgumentException("Το σχόλιο δεν έχει περιεχόμενο");

        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Δεν δώθηκε username");

        VideoClip videoClip = videoClipRepository.findById(videoClipId)
                .orElseThrow(() -> new EntityNotFoundException("Δεν βρέθηκε το video clip με ID: " + videoClipId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Δεν βρέθηκε ο χρήστης με username: " + username));

        String uuid = uuidSupplier.get().toString();

        LocalDateTime commentCreatedTime = LocalDateTime.now();

        Comment comment = new Comment(uuid, videoClip, user, content, commentCreatedTime);

        commentRepository.save(comment);
    }

}
