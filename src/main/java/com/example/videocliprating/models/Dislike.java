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
@Table(name = "dislikes_table")
public class Dislike {

    @EmbeddedId
    private DislikeId id;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DislikeId implements java.io.Serializable {

        @Column(nullable = false)
        private String username;

        @Column(nullable = false)
        private String videoClipId;
    }
}
