package com.example.movie_management.service;

import com.example.movie_management.model.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie saveMovie(Movie movie);
    //Optional<Movie> getMovieById(Long id);
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    void deleteMovie(Long id);
}
