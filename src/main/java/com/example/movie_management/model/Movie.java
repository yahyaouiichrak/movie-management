package com.example.movie_management.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    @Column(name = "`year`") // Use backticks to escape the column name
    private int year;

}