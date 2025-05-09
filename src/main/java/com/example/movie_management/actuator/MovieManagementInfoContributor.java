package com.example.movie_management.actuator;

import com.example.movie_management.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovieManagementInfoContributor implements InfoContributor {

    private final MovieService movieService;

    @Autowired
    public MovieManagementInfoContributor(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> movieDetails = new HashMap<>();
        movieDetails.put("totalMovies", movieService.getAllMovies().size());

        // Group movies by genre
        Map<String, Long> moviesByGenre = new HashMap<>();
        movieService.getAllMovies().forEach(movie -> {
            String genre = movie.getGenre() != null ? movie.getGenre() : "Uncategorized";
            moviesByGenre.put(genre, moviesByGenre.getOrDefault(genre, 0L) + 1);
        });
        movieDetails.put("moviesByGenre", moviesByGenre);

        builder.withDetail("movieCatalog", movieDetails);
    }
}
