package com.example.movie_management.service;

import com.example.movie_management.model.Movie;
import com.example.movie_management.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
    @Autowired
    private MovieRepository movieRepository;

    @Override
    @CachePut(value = "movies", key = "#movie.id")
    public Movie saveMovie(Movie movie) {
        logger.info("Saving movie with id {} to database", movie.getId());
        return movieRepository.save(movie);
    }

    @Override
    //@Cacheable(value = "movies")
    public List<Movie> getAllMovies() {
        logger.info("Fetching all movies from database");
        return movieRepository.findAll();
    }

    @Override
    @Cacheable(value = "movies", key = "#id")
    public Movie getMovieById(Long id) {
        logger.info("Fetching movie with id {} from database", id);
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    @CacheEvict(value = "movies", key = "#id")
    public void deleteMovie(Long id) {
        logger.info("Deleting movie with id {} from database", id);
        movieRepository.deleteById(id);
    }
}
