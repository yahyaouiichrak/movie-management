package com.example.movie_management.config;

import com.example.movie_management.model.Movie;
import com.example.movie_management.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final MovieRepository movieRepository;

    public DataInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    public void init() {
        movieRepository.save(new Movie(null, "Inception", "Sci-Fi", 2010));
        movieRepository.save(new Movie(null, "The Dark Knight", "Action", 2008));
        movieRepository.save(new Movie(null, "Interstellar", "Sci-Fi", 2014));
    }
}
