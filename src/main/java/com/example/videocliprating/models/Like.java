package com.example.videocliprating.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes_table")
public class Like {

    @EmbeddedId
    private LikeId id;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeId implements java.io.Serializable {

        @Column(nullable = false)
        private String username;

        @Column(nullable = false)
        private String videoClipId;

    }
}
