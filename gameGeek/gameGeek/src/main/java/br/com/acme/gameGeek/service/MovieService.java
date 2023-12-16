package br.com.acme.gameGeek.service;

import br.com.acme.gameGeek.model.Movie;
import br.com.acme.gameGeek.model.PayloadMovie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.*;

@Service
public class MovieService {
    private static Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
    private Map<Long, Movie> movies = filmes();
    private Long lastId = (long) movies.size();

    private Map<Long, Movie> filmes() {
        Movie movie1 = Movie
                .builder()
                .adult(false)
                .backdrop_path("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQdRMYceE60sg0MFewT_470b9AF5sl_E9tlbreGPA9LVVrRwA2m")
                .genre_ids(new ArrayList<>())
                .id(1L)
                .original_language("pt-BR")
                .original_title("Até que a Sorte Nos Separe")
                .overview("Tino é um pai de família que tem sua rotina transformada ao ganhar na loteria. Em dez anos, o fanfarrão e sua mulher Jane gastam todo o dinheiro com uma vida de ostentação. Quando descobre que está falido, ele é obrigado a aceitar a ajuda de seu vizinho Amauri, um consultor financeiro nada divertido e extremamente econômico. Jane engravida do terceiro filho e Tino faz de tudo para esconder da esposa que estão na pior, pois a recomendação médica é que a grávida evite fortes emoções.")
                .popularity(2057.53)
                .poster_path("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQdRMYceE60sg0MFewT_470b9AF5sl_E9tlbreGPA9LVVrRwA2m")
                .release_date("2012-10-05")
                .title("Até que a Sorte Nos Separe")
                .video("https://youtu.be/UZiOpdzutaU")
                .vote_average(6.95)
                .vote_count(12597)
                .build();
        Movie movie2 = Movie
                .builder()
                .adult(false)
                .backdrop_path("")
                .genre_ids(new ArrayList<>())
                .id(2L)
                .original_language("")
                .original_title("Movie 2")
                .overview("")
                .popularity(0)
                .poster_path("")
                .release_date("")
                .title("Movie 2")
                .video("")
                .vote_average(0)
                .vote_count(0)
                .video("")
                .build();
        Movie movie3 = Movie
                .builder()
                .adult(false)
                .backdrop_path("")
                .genre_ids(new ArrayList<>())
                .id(3L)
                .original_language("")
                .original_title("Movie 3")
                .overview("")
                .popularity(0)
                .poster_path("")
                .release_date("")
                .title("Movie 3")
                .video("")
                .vote_average(0)
                .vote_count(0)
                .video("")
                .build();

        Map<Long, Movie> movies = new HashMap<>();
        movies.put(1L, movie1);
        movies.put(2L, movie2);
        movies.put(3L, movie3);
        return movies;
    }

    public List<Movie> getAll() {
        return movies.values().stream().toList();
    }

    public Movie getById(long id) {
        Movie movie = movies.get(id);
        return movie;
    }

    public void deleteById(long id) {
        movies.remove(id);
    }

    public void update(long id, Movie movie) {
        movie.setId(id);
        movies.replace(id, movie);
    }

    public void create(Movie movie) {
        Long id = (long) this.movies.values().size() + 1;
        movie.setId(id);
        movies.put(movie.getId(), movie);
    }

    public List<Movie> filmesPopulares() throws IOException, InterruptedException, URISyntaxException {
        String URL = "https://api.themoviedb.org/3/movie/popular?language=pt-US&page=1";

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .version(HttpClient.Version.HTTP_2)
                .uri(new URI(URL))
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YzhkNWI3OWU5NGIyODA2YThhODFhZGJlZWNkZjhlNyIsInN1YiI6IjY1N2E1MmM5NGQyM2RkMDBjNmFhZTk5ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.76eOjrSI2IV0W-aAyNObgXwuA9DTkE_cANOxeWirLfc")
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        LOGGER.info(String.valueOf(response.statusCode()));

        ObjectMapper mapper = JsonMapper.builder().build();
        PayloadMovie payloadMovie = mapper.readValue(response.body(), PayloadMovie.class);
        List<Movie> populares = payloadMovie.getResults();

        return populares;
    }
}
