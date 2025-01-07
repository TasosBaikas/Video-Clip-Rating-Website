package com.example.videocliprating.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "video_clips")
public class VideoClip {

    @Id
    private String uuid;

    @Column(nullable = false)
    private String youtubeUrl;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false, unique = true)
    private String movieTitle;

    @Column(nullable = false)
    private String videoClipImageUrl;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int durationInSeconds;

    @Column(nullable = false)
    private int movieDurationInMinutes;

    @Column(nullable = false)
    private LocalDateTime releaseTimeInUTC;

    @OneToMany(mappedBy = "videoClip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public VideoClip(String uuid, String youtubeUrl, String imageUrl, String movieTitle, String videoClipImageUrl, String description, int durationInSeconds, int movieDurationInMinutes, LocalDateTime releaseTimeInUTC) {
        this.uuid = uuid;
        this.youtubeUrl = youtubeUrl;
        this.imageUrl = imageUrl;
        this.movieTitle = movieTitle;
        this.videoClipImageUrl = videoClipImageUrl;
        this.description = description;
        this.durationInSeconds = durationInSeconds;
        this.movieDurationInMinutes = movieDurationInMinutes;
        this.releaseTimeInUTC = releaseTimeInUTC;
    }

}
