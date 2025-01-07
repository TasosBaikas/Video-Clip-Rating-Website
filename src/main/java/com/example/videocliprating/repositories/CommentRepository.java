package com.example.videocliprating.repositories;

import com.example.videocliprating.models.Comment;
import com.example.videocliprating.models.VideoClip;
import com.example.videocliprating.models.dto.VideoClipDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("SELECT c FROM Comment c " +
            "JOIN c.videoClip v " +
            "WHERE v.movieTitle = :movieTitle")
    Optional<List<Comment>> getCommentsByMovieTitle(@Param("movieTitle") String movieTitle);

}
