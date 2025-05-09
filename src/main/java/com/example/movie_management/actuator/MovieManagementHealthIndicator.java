package com.example.movie_management.actuator;

import com.example.movie_management.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MovieManagementHealthIndicator implements HealthIndicator {

    private final MovieService movieService;

    @Autowired
    public MovieManagementHealthIndicator(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public Health health() {
        try {
            // Check if we can access the movie repository
            int movieCount = movieService.getAllMovies().size();
            return Health.up()
                    .withDetail("movieCount", movieCount)
                    .withDetail("message", "Movie management service is running normally")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("message", "Movie management service is not available")
                    .build();
        }
    }
}
