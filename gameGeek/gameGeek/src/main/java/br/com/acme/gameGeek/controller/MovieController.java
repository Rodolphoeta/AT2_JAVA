package br.com.acme.gameGeek.controller;

import br.com.acme.gameGeek.model.Movie;
import br.com.acme.gameGeek.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    MovieService movieService;

    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false) String popular, @RequestParam(required = false) String nome) throws IOException, URISyntaxException, InterruptedException {
        if (popular != null && nome != null){
            List<Movie> allMoviesPopulares = movieService.filmesPopulares();
            List<Movie> movies = allMoviesPopulares.stream().filter(file -> file.getTitle().contains(nome)).toList();
            return ResponseEntity.ok().body(movies);
        } else if (popular != null) {
            List<Movie> movies = movieService.filmesPopulares();
            return ResponseEntity.ok().body(movies);
        } else if (nome != null) {
            List<Movie> allMovies = movieService.getAll();
            List<Movie> movies = allMovies.stream().filter(file -> file.getTitle().contains(nome)).toList();
            return ResponseEntity.ok().body(movies);
        } else {
            List<Movie> movies = movieService.getAll();
            return ResponseEntity.ok().body(movies);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getId(@PathVariable Long id) {
        Movie byId = movieService.getById(id);
        return ResponseEntity.ok().body(byId);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Movie movie) {
        movieService.create(movie);
        return ResponseEntity.ok().body("Filme adicionado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        movieService.deleteById(id);
        return ResponseEntity.ok().body("Filme deletado com máximo sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity editar(@PathVariable Long id, @RequestBody Movie filme) {
        movieService.update(id, filme);
        return ResponseEntity.ok().body("Filme editado com máximo sucesso!");
    }
}
