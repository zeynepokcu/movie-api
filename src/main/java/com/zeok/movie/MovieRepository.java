package com.zeok.movie;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepository implements IRemoteRepository {
	
	@Value("${collect.api.token}")
    public String accessToken;

    @Override
    public List<Movie> searchMovies(String movieName) {
        List<Movie> movies = new ArrayList<>();

        try {
            String url = String.format("https://api.collectapi.com/imdb/imdbSearchByName?query=%s", movieName);
            JSONObject response = Utils.sendGetRequest(url, accessToken);
            if(response.getBoolean("success")) {
                JSONArray array = response.getJSONArray("result");
                for(int i = 0; i < array.length(); i++) {
                    movies.add(Utils.jsonToMovie(array.getJSONObject(i)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public Movie getMovie(String movieId) {
        try {
            String url = String.format("https://api.collectapi.com/imdb/imdbSearchById?movieId=%s", movieId);
            JSONObject response = Utils.sendGetRequest(url, accessToken);
            if(response.getBoolean("success")) {
                return Utils.jsonToMovie(response.getJSONObject("result"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
}
