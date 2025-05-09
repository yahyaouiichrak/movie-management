package com.example.movie_management.actuator;

import com.example.movie_management.model.Movie;
import com.example.movie_management.service.MovieService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MovieManagementMetrics {

    private final MovieService movieService;
    private final MeterRegistry meterRegistry;

    @Autowired
    public MovieManagementMetrics(MovieService movieService, MeterRegistry meterRegistry) {
        this.movieService = movieService;
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void registerMetrics() {
        // Total number of movies
        Gauge.builder("moviemanagement.movies.count",
                        () -> movieService.getAllMovies().size())
                .description("Total number of movies in the catalog")
                .register(meterRegistry);

        // Number of movies by genre
        Gauge.builder("moviemanagement.movies.bygenre",
                        () -> getMoviesCountByGenre())
                .description("Number of movies by genre")
                .register(meterRegistry);

        // Average year of movies
        Gauge.builder("moviemanagement.movies.avgyear",
                        () -> getAverageMovieYear())
                .description("Average year of movies")
                .register(meterRegistry);
    }

    private int getMoviesCountByGenre() {
        List<Movie> allMovies = movieService.getAllMovies();
        Map<String, Long> moviesByGenre = allMovies.stream()
                .collect(Collectors.groupingBy(Movie::getGenre, Collectors.counting()));
        return moviesByGenre.size();
    }

    private double getAverageMovieYear() {
        List<Movie> allMovies = movieService.getAllMovies();
        if (allMovies.isEmpty()) {
            return 0;
        }
        return allMovies.stream()
                .mapToInt(Movie::getYear)
                .average()
                .orElse(0);
    }
}
