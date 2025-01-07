package com.example.videocliprating.repositories;

import com.example.videocliprating.models.VideoClip;
import com.example.videocliprating.models.dto.VideoClipDTO;
import com.example.videocliprating.models.dto.VideoClipWithCommentsDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoClipRepository extends JpaRepository<VideoClip, String> {

    @Query("SELECT COUNT(l) FROM Like l WHERE l.id.videoClipId = :videoClipId")
    long countLikes(@Param("videoClipId") String videoClipId);

    @Query("SELECT COUNT(d) FROM Dislike d WHERE d.id.videoClipId = :videoClipId")
    long countDislikes(@Param("videoClipId") String videoClipId);

    @Query("SELECT new com.example.videocliprating.models.dto.VideoClipDTO(v.uuid, v.youtubeUrl, v.imageUrl, v.videoClipImageUrl, v.description, " +
            "v.movieTitle, v.durationInSeconds, v.movieDurationInMinutes, v.releaseTimeInUTC, " +
            "(SELECT COUNT(l) FROM Like l WHERE l.id.videoClipId = v.uuid), " +
            "(SELECT COUNT(d) FROM Dislike d WHERE d.id.videoClipId = v.uuid)) " +
            "FROM VideoClip v")
    Optional<List<VideoClipDTO>> getVideoClips();


    @Query("SELECT new com.example.videocliprating.models.dto.VideoClipDTO(v.uuid, v.youtubeUrl, v.imageUrl, v.videoClipImageUrl, v.description, " +
            "v.movieTitle, v.durationInSeconds, v.movieDurationInMinutes, v.releaseTimeInUTC, " +
            "(SELECT COUNT(l) FROM Like l WHERE l.id.videoClipId = v.uuid), " +
            "(SELECT COUNT(d) FROM Dislike d WHERE d.id.videoClipId = v.uuid)) " +
            "FROM VideoClip v " +
            "ORDER BY v.releaseTimeInUTC DESC")
    Optional<List<VideoClipDTO>> getNVideoClips(Pageable pageable);


    @Query("SELECT new com.example.videocliprating.models.dto.VideoClipDTO(" +
            "v.uuid, v.youtubeUrl, v.imageUrl, v.videoClipImageUrl, v.description, " +
            "v.movieTitle, v.durationInSeconds, v.movieDurationInMinutes, v.releaseTimeInUTC, " +
            "(SELECT COUNT(l) FROM Like l WHERE l.id.videoClipId = v.uuid), " +
            "(SELECT COUNT(d) FROM Dislike d WHERE d.id.videoClipId = v.uuid)) " +
            "FROM VideoClip v " +
            "WHERE v.movieTitle = :movieTitle")
    Optional<VideoClipDTO> getVideoClipByMovieTitle(@Param("movieTitle") String movieTitle);

    @Query("SELECT new com.example.videocliprating.models.dto.VideoClipDTO(" +
            "v.uuid, v.youtubeUrl, v.imageUrl, v.videoClipImageUrl, v.description, " +
            "v.movieTitle, v.durationInSeconds, v.movieDurationInMinutes, v.releaseTimeInUTC, " +
            "(SELECT COUNT(l) FROM Like l WHERE l.id.videoClipId = v.uuid), " +
            "(SELECT COUNT(d) FROM Dislike d WHERE d.id.videoClipId = v.uuid)) " +
            "FROM VideoClip v " +
            "WHERE v.uuid = :uuid")
    Optional<VideoClipDTO> getVideoClipById(@Param("uuid") String uuid);


//    @Query("SELECT new com.example.videocliprating.models.dto.VideoClipWithCommentsDTO(" +
//            "v.uuid, v.youtubeUrl, v.imageUrl, v.videoClipImageUrl, v.description, " +
//            "v.movieTitle, v.durationInSeconds, v.releaseTimeInUTC, " +
//            "(SELECT COUNT(l) FROM Like l WHERE l.id.videoClipId = v.uuid), " +
//            "(SELECT COUNT(d) FROM Dislike d WHERE d.id.videoClipId = v.uuid), " +
//            "c) " +
//            "FROM VideoClip v " +
//            "LEFT JOIN v.comments c " +
//            "WHERE v.movieTitle = :movieTitle")
//    Optional<VideoClipWithCommentsDTO> getVideoClipByMovieTitle(@Param("movieTitle") String movieTitle);

}
