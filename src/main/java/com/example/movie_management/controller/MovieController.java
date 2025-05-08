package com.example.movie_management.controller;

import com.example.movie_management.model.Movie;
import com.example.movie_management.repository.MovieRepository;
import com.example.movie_management.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}