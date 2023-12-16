package br.com.acme.gameGeek.model;

import lombok.Data;

@Data
public class MovieResponse {
    private String title;
    private String overview;
    private double average;

    public MovieResponse(String title, String overview, double average) {
        this.title = title;
        this.overview = overview;
        this.average = average;
    }
}
