package br.com.acme.gameGeek;
import br.com.acme.gameGeek.model.Movie;
import br.com.acme.gameGeek.service.MovieService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MovieServiceTests {
    @Autowired
    MovieService movieService;
    private static Logger LOGGER = LoggerFactory.getLogger(MovieServiceTests.class);

    @Test
    @DisplayName("Deve retornar todos os movies")
    void returnAllCustomers() {
        List<Movie> movies = movieService.getAll();
        assertEquals(4, movies.size());
    }
    @Test
    @DisplayName("Retorna um filme pelo ID")
    void returnMovieId(){
        Movie movie = movieService.getById(2L);
        assertEquals(movie.getTitle(), "Movie 2");
        assertEquals(movie.getId(), 2L);
        LOGGER.info(movie.toString());
    }
    @Test
    @DisplayName("Requisicao externa teste")
    void requisicaoExterna() throws IOException, URISyntaxException, InterruptedException {
        List<Movie> movies = new ArrayList<>();
        assertTrue(movies.size() == 0);
        movies = movieService.filmesPopulares();
        assertTrue(movies.size() > 0);
    }
    @Test
    @DisplayName("Remove um filme pelo id")
    void testRemove(){
        movieService.deleteById(1L);
        List<Movie> movies = movieService.getAll();
        assertEquals(3, movies.size());
    }
    @Test
    @DisplayName("Faz o update de um filme")
    void updateClientTest(){
        Movie movie = movieService.getById(1L);
        movie.setTitle("Filme do Hassum");
        movieService.update(1L, movie);
        Movie updated = movieService.getById(1L);
        assertEquals("Filme do Hassum", updated.getTitle());
    }
    @Test
    @DisplayName("Deve inserir um filme")
    void testInsert() {
        Movie movieAdd = Movie
                .builder()
                .id(4L)
                .original_title("Filme adicionado")
                .title("Filme adicionado")
                .build();

        movieService.create(movieAdd);
        List<Movie> all = movieService.getAll();
        Movie fileAdd = movieService.getById(4L);

        assertEquals(4, all.size());
        assertEquals("Filme adicionado", fileAdd.getTitle());
        assertEquals("Filme adicionado", fileAdd.getOriginal_title());
    }
}
