package com.zeok.movie;

import org.springframework.stereotype.Component;

@Component
public interface ILocalRepository {
	Movie getDetails(String movieId);
    boolean addToList(Movie movie);    
}
