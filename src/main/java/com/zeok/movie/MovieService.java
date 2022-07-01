package com.zeok.movie;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
	
	private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;
    
    @Autowired
    public MovieService(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public List<Movie> searchMovies(String movieName) {
        return remoteRepository.searchMovies(movieName);
    }

    public boolean addToList(String movieId) {
        Movie movie = remoteRepository.getMovie(movieId);
        return localRepository.addToList(movie);
    }
    
    public Movie getDetails(String movieId) {
        // check local
        if(localRepository.getDetails(movieId) != null)
            return localRepository.getDetails(movieId);

        // if movie doesn't exist in local repository, get it from imdb
        return remoteRepository.getMovie(movieId);
    }
}