package com.example.movie_management.controller;

import com.example.movie_management.model.Movie;
import com.example.movie_management.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@Import(MovieControllerTest.MockConfig.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public MovieService movieService() {
            return Mockito.mock(MovieService.class);
        }
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(movieService)).build();
    }

    @Test
    public void testGetAllMovies() throws Exception {
        List<Movie> movies = Arrays.asList(
                new Movie(1L, "Movie 1", "Genre 1", 2021),
                new Movie(2L, "Movie 2", "Genre 2", 2022)
        );

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/api/movies")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Movie 1")))
                .andExpect(jsonPath("$[1].title", is("Movie 2")));
    }

    @Test
    public void testGetMovieById_Found() throws Exception {
        Movie movie = new Movie(1L, "Movie 1", "Genre 1", 2021);
        when(movieService.getMovieById(1L)).thenReturn(movie);

        mockMvc.perform(get("/api/movies/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Movie 1")))
                .andExpect(jsonPath("$.genre", is("Genre 1")));
    }

    @Test
    public void testGetMovieById_NotFound() throws Exception {
        when(movieService.getMovieById(3L)).thenReturn(null);

        mockMvc.perform(get("/api/movies/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
