package com.example.movie_management.controller;

import com.example.movie_management.model.Movie;
import com.example.movie_management.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movie Controller", description = "Operations pertaining to movies in the catalog")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Get all movies", description = "Returns a list of all movies in the catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved movies",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) })
    })
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }



    @Operation(summary = "Get a movie by ID", description = "Returns a single movie identified by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved movie",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(
            @Parameter(description = "ID of the movie to be retrieved") @PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @Operation(summary = "Create a new movie", description = "Adds a new movie to the catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie successfully created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Movie> saveMovie(
            @Parameter(description = "Movie object to be added") @Valid @RequestBody Movie movie) {
        movie.setId(null);
        Movie createdMovie = movieService.saveMovie(movie);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }


    @Operation(summary = "Delete a movie", description = "Removes a movie from the catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movie successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(
            @Parameter(description = "ID of the movie to be deleted") @PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}