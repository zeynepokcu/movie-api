package com.zeok.movie;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface IRemoteRepository {
	List<Movie> searchMovies(String movieName);
    Movie getMovie(String movieId);
}
