package com.jd.controller;

import com.jd.model.VideoGame;
import com.jd.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videogames")
public class VideoGameController {

    @Autowired
    private VideoGameRepository videoGameRepository;

    // Get all video games
    @GetMapping
    public List<VideoGame> getAllVideoGames() {
        List<VideoGame> videoGames = videoGameRepository.findAll();
        System.out.println("Fetched games: " + videoGames);
        return videoGames;
    }

    // Get a specific video game by id
    @GetMapping("/{id}")
    public Optional<VideoGame> getVideoGameById(@PathVariable int id) {
        return videoGameRepository.findById(id);
    }
}
