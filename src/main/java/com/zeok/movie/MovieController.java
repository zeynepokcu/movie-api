package com.zeok.movie;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class MovieController {
	
	private MovieService service;
	
	@Autowired
	public MovieController(MovieService service) {
		this.service = service;
	}
	
	@RequestMapping(path = "/movies/search", method = RequestMethod.GET)
	public List<Movie> searchByName(@RequestParam(name = "movie_name") String movieName) {
		return this.service.searchMovies(movieName);
	}	
	
	@RequestMapping(path = "/movies/addToList", method = RequestMethod.GET)
	public boolean addToListById(@RequestParam(name = "id") String movieId) {
		return this.service.addToList(movieId);
	}
	
	@RequestMapping(path = "/movies/getDetails", method = RequestMethod.GET)
	public Movie getDetailsById(@RequestParam(name = "id") String movieId) {
		Movie movie = this.service.getDetails(movieId);
		return movie;
		
	}
	
}