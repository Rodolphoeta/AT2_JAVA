package br.com.acme.gameGeek.model;

import lombok.Data;

import java.util.List;

@Data
public class PayloadMovie {
    private int page;
    private List<Movie> results;
    private long total_pages;
    private long total_results;
}
