package com.example.videocliprating.helpers.comparators;

import com.example.videocliprating.models.dto.VideoClipDTO;

import java.util.Comparator;

/**
 * Comparator for VideoClipDTO objects to sort by releaseTimeInUTC from newest to oldest.
 */
public class VideoClipsUpComingReleasesComparator implements Comparator<VideoClipDTO> {

    @Override
    public int compare(VideoClipDTO o1, VideoClipDTO o2) {
        // Null checks to avoid NullPointerException
        if (o1.getReleaseTimeInUTC() == null && o2.getReleaseTimeInUTC() == null) {
            return 0;
        } else if (o1.getReleaseTimeInUTC() == null) {
            return 1; // Treat null as older
        } else if (o2.getReleaseTimeInUTC() == null) {
            return -1; // Treat null as older
        }

        // Compare by releaseTimeInUTC (newest first)
        return o1.getReleaseTimeInUTC().compareTo(o2.getReleaseTimeInUTC());
    }
}
