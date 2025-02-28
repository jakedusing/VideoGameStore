package com.jd.controller;

import com.jd.model.VideoGame;
import com.jd.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
      //  System.out.println("Fetched games: " + videoGames);
        return videoGames;
    }

    // Get a specific video game by id
    @GetMapping("/{id}")
    public Optional<VideoGame> getVideoGameById(@PathVariable int id) {
        return videoGameRepository.findById(id);
    }

    // Update game price or stock
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVideoGame(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Optional<VideoGame> optionalGame = videoGameRepository.findById(id);
        if (optionalGame.isPresent()) {
            VideoGame game = optionalGame.get();
            updates.forEach((key, value) -> {
                if ("price".equals(key)) game.setPrice(Double.valueOf(value.toString()));
                if ("stock".equals(key)) game.setStock(Integer.parseInt(value.toString()));
            });
            videoGameRepository.save(game);
            return ResponseEntity.ok(game);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video game not found");
    }
}
