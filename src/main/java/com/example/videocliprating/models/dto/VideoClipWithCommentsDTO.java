package com.example.videocliprating.models.dto;

import com.example.videocliprating.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoClipWithCommentsDTO extends VideoClipDTO {

    private List<Comment> comments;

    public VideoClipWithCommentsDTO(String uuid, String youtubeUrl, String imageUrl, String videoClipImageUrl, String description, String movieTitle, int durationInSeconds, int movieDurationInMinutes, LocalDateTime releaseTimeInUTC, long likeCount, long dislikeCount, List<Comment> comments) {

        super(uuid, youtubeUrl, imageUrl, videoClipImageUrl, description, movieTitle, durationInSeconds, movieDurationInMinutes, releaseTimeInUTC, likeCount, dislikeCount);

        this.comments = comments;
    }

    public VideoClipWithCommentsDTO(VideoClipDTO videoClip, List<Comment> comments) {
        super(
                videoClip.getUuid(),
                videoClip.getYoutubeUrl(),
                videoClip.getImageUrl(),
                videoClip.getVideoClipImageUrl(),
                videoClip.getDescription(),
                videoClip.getMovieTitle(),
                videoClip.getDurationInSeconds(),
                videoClip.getMovieDurationInMinutes(),
                videoClip.getReleaseTimeInUTC(),
                videoClip.getLikeCount(),
                videoClip.getDislikeCount()
        );

        this.comments = new ArrayList<>(comments);
    }

}
