package com.example.movie_management;

import com.example.movie_management.model.Movie;
import com.example.movie_management.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();
        movieRepository.save(new Movie(null, "Movie 1", "Genre 1", 2021));
        movieRepository.save(new Movie(null, "Movie 2", "Genre 2", 2022));
    }

    @Test
    public void testGetAllMovies() throws Exception {
        mockMvc.perform(get("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Movie 1")))
                .andExpect(jsonPath("$[1].title", is("Movie 2")));
    }

    @Test
    public void testGetMovieById() throws Exception {
        Movie movie = movieRepository.findAll().get(0);

        mockMvc.perform(get("/api/movies/" + movie.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(movie.getTitle())));
    }

    @Test
    public void testCreateMovie() throws Exception {
        Movie newMovie = new Movie(null, "Movie 3", "Genre 3", 2023);

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMovie)))
                .andExpect(status().isCreated()) // Changed to isCreated()
                .andExpect(jsonPath("$.title", is("Movie 3")))
                .andExpect(jsonPath("$.genre", is("Genre 3")));
    }


}
