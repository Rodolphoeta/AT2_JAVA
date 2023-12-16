package br.com.acme.gameGeek.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    private boolean adult;
    private String backdrop_path;
    private List<Integer> genre_ids;
    private long id;
    private String original_language;
    private String original_title;
    private String overview;
    public double popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private String video;
    private double vote_average;
    private double vote_count;
}
