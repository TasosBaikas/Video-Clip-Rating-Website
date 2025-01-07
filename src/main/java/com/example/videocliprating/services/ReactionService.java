package com.example.videocliprating.services;

import com.example.videocliprating.models.Comment;
import com.example.videocliprating.models.Dislike;
import com.example.videocliprating.models.Like;
import com.example.videocliprating.models.dto.VideoClipDTO;
import com.example.videocliprating.models.dto.VideoClipWithCommentsDTO;
import com.example.videocliprating.repositories.CommentRepository;
import com.example.videocliprating.repositories.DislikeRepository;
import com.example.videocliprating.repositories.LikeRepository;
import com.example.videocliprating.repositories.VideoClipRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReactionService {

    private final VideoClipRepository videoClipRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final DislikeRepository dislikeRepository;

    @Autowired
    public ReactionService(VideoClipRepository videoClipRepository, CommentRepository commentRepository, LikeRepository likeRepository, DislikeRepository dislikeRepository) {
        this.videoClipRepository = videoClipRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.dislikeRepository = dislikeRepository;
    }

    public boolean handleLikeReaction(String username, String videoClipId){

        Like.LikeId likeId = new Like.LikeId(username, videoClipId);

        Optional<Like> likeOptional = likeRepository.findById(likeId);
        if (likeOptional.isPresent()){

            likeRepository.deleteById(likeId);
            return false;
        }

        dislikeRepository.deleteById(new Dislike.DislikeId(username, videoClipId));

        Like like = new Like(likeId);

        likeRepository.save(like);
        return true;
    }

    public boolean handleDislikeReaction(String username, String videoClipId) {

        Dislike.DislikeId dislikeId = new Dislike.DislikeId(username, videoClipId);

        Optional<Dislike> dislikeOptional = dislikeRepository.findById(dislikeId);
        if (dislikeOptional.isPresent()) {
            // If the dislike already exists, remove it
            dislikeRepository.deleteById(dislikeId);
            return false;
        }

        // Remove any existing like for this user and video
        likeRepository.deleteById(new Like.LikeId(username, videoClipId));

        // Save the new dislike
        Dislike dislike = new Dislike(dislikeId);
        dislikeRepository.save(dislike);
        return true;
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


    @Nullable
    public VideoClipWithCommentsDTO getVideoClipWithCommentsById(String uuid){

        Optional<VideoClipDTO> videoClipOptional = videoClipRepository.getVideoClipById(uuid);
        if (videoClipOptional.isEmpty())
            return null;

        VideoClipDTO videoClip = videoClipOptional.get();

        Optional<List<Comment>> commentsOptional = commentRepository.getCommentsByMovieTitle(videoClip.getMovieTitle());

        return new VideoClipWithCommentsDTO(videoClip, commentsOptional.orElse(new ArrayList<>()));
    }


}
