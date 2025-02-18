package com.jd;


import com.jd.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private VideoGameRepository videoGameRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Video Games in the database: ");
        videoGameRepository.findAll().forEach(videoGame -> System.out.println(videoGame.getId()));
    }
}
