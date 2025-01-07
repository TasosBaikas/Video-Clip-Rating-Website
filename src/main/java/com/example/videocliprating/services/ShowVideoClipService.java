package com.example.videocliprating.services;

import com.example.videocliprating.models.Comment;
import com.example.videocliprating.models.Dislike;
import com.example.videocliprating.models.Like;
import com.example.videocliprating.models.dto.UserDTO;
import com.example.videocliprating.models.dto.VideoClipDTO;
import com.example.videocliprating.models.dto.VideoClipWithCommentsDTO;
import com.example.videocliprating.repositories.*;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowVideoClipService {

    private final VideoClipRepository videoClipRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final DislikeRepository dislikeRepository;

    @Autowired
    public ShowVideoClipService(VideoClipRepository videoClipRepository, CommentRepository commentRepository, UserRepository userRepository, LikeRepository likeRepository, DislikeRepository dislikeRepository) {
        this.videoClipRepository = videoClipRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.dislikeRepository = dislikeRepository;
    }

    @Nullable
    public VideoClipWithCommentsDTO getVideoClipWithCommentsByMovieTitle(String movieTitle){

        Optional<VideoClipDTO> videoClipOptional = videoClipRepository.getVideoClipByMovieTitle(movieTitle);
        if (videoClipOptional.isEmpty())
            return null;

        Optional<List<Comment>> commentsOptional = commentRepository.getCommentsByMovieTitle(movieTitle);

        VideoClipDTO videoClip = videoClipOptional.get();

        return new VideoClipWithCommentsDTO(videoClip, commentsOptional.orElse(new ArrayList<>()));
    }

    public List<VideoClipDTO> getAllVideoClips(){

        return videoClipRepository.getVideoClips()
                .orElse(new ArrayList<>());
    }


    public UserDTO getUserByUsername(String username) {

        return userRepository.findUserDTOByUsername(username)
                .orElse(null);
    }

    public boolean isVideoClipLiked(String username, String videoClipId) {
        return likeRepository
                .findById(new Like.LikeId(username, videoClipId))
                .isPresent();
    }

    public boolean isVideoClipDisliked(String username, String videoClipId) {
        return dislikeRepository
                .findById(new Dislike.DislikeId(username, videoClipId))
                .isPresent();
    }

}
