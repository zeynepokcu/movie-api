package com.zeok.movie;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocalRepository implements ILocalRepository {
	@Value("${local.storage.file}")
    private String filePath;
	
    @Override
    public boolean addToList(Movie movie) {
    	// check if movie is null
        if(movie == null) return false;
        // check if movie is saved
        if(getDetails(movie.getImdbId()) != null) return true;

        return Utils.writeToFile(Utils.movieToTxt(movie), filePath);
    }

    @Override
    public Movie getDetails(String movieId) {
        return Utils.readFile(filePath).stream()
                .map(Utils::txtToMovie)
                .filter(movie -> movie.getImdbId().toLowerCase(Locale.ROOT).equals(movieId.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElse(null);
    }

}
