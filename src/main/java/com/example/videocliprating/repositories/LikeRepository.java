package com.example.videocliprating.repositories;

import com.example.videocliprating.models.Like;
import com.example.videocliprating.models.Like.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
    Optional<Like> findById(LikeId id);

    void deleteById(LikeId id);
}
