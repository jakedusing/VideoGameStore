package com.jd.repository;

import com.jd.model.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {
    // You can define custom queries if needed, but JpaRepository provides common methods by default

    @Query("SELECT v FROM VideoGame v")
    List<VideoGame> findAllGames();
}
