package com.example.videocliprating.repositories;

import com.example.videocliprating.models.Dislike;
import com.example.videocliprating.models.Dislike.DislikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DislikeRepository extends JpaRepository<Dislike, DislikeId> {
    Optional<Dislike> findById(DislikeId id);

    void deleteById(DislikeId id);
}
