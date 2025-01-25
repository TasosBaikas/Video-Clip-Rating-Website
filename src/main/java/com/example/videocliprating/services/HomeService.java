package com.example.videocliprating.services;

import com.example.videocliprating.helpers.comparators.VideoClipsUpComingReleasesComparator;
import com.example.videocliprating.models.dto.VideoClipDTO;
import com.example.videocliprating.repositories.VideoClipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    private final VideoClipRepository videoClipRepository;

    @Autowired
    public HomeService(VideoClipRepository videoClipRepository) {
        this.videoClipRepository = videoClipRepository;
    }

    public List<VideoClipDTO> getAllVideoClips(){

        return videoClipRepository.getVideoClips()
                .orElse(new ArrayList<>())
                .stream().sorted(new VideoClipsUpComingReleasesComparator())
                .collect(Collectors.toList());
    }

}
