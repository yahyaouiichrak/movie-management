package com.example.movie_management.service;

import com.example.movie_management.model.Movie;
import java.util.List;

public interface MovieService {
    Movie saveMovie(Movie movie);
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    void deleteMovie(Long id);
}
