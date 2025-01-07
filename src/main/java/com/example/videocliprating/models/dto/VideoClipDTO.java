package com.example.videocliprating.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoClipDTO {

    private String uuid;

    private String youtubeUrl;

    private String imageUrl;

    private String videoClipImageUrl;

    private String description;

    private String movieTitle;

    private int durationInSeconds;

    private int movieDurationInMinutes;

    private LocalDateTime releaseTimeInUTC;

    private long likeCount;

    private long dislikeCount;

}
