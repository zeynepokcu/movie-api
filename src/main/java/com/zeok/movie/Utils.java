package com.zeok.movie;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;

public class Utils {
	
	private static Movie movie = new Movie();
    private static HttpURLConnection con;
	
	private Utils() {
    }
	
	public static List<String> readFile(String path) {
        List<String> list = new ArrayList<>();
        try (FileReader fr = new FileReader(path); BufferedReader br = new BufferedReader(fr)) {
            String line = br.readLine();
            while (line != null) {
            	list.add(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public static boolean writeToFile(String msg, String path) {
        try (FileWriter fw = new FileWriter(path, true); BufferedWriter bw = new BufferedWriter(fw)) {
            File file = new File(path);
            if(!file.exists()) file.createNewFile();
            bw.write(msg);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
	
	// movie object to values
    public static String movieToTxt(Movie movie) {
        return String.format("%s,%s,%s,%s,%s%n", movie.getTitle(), movie.getYear(), movie.getImdbId(), movie.getType());
    }

    // json object to movie object
    public static Movie jsonToMovie(JSONObject json) throws JSONException {
    	movie.setTitle(json.getString("Title"));
    	movie.setYear(json.getString("Year"));
    	movie.setImdbId(json.getString("imdbID"));
    	movie.setType(json.getString("Type"));
        return movie;
    }
    
    // values to movie object
    public static Movie txtToMovie(String txt) {
        String[] values = txt.split(",");
        movie.setTitle(values[0]);
        movie.setYear(values[1]);
        movie.setImdbId(values[2]);
        movie.setType(values[3]);        
        return movie;
    }
	
	private static void getConnection(String requestUrl, String accessToken) throws IOException {
        URL url = new URL(requestUrl);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");
        con.setRequestProperty(HttpHeaders.AUTHORIZATION, accessToken);
    }

    public static JSONObject sendGetRequest(String requestUrl, String accessToken) throws JSONException, IOException {
    	getConnection(requestUrl, accessToken);
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = br.readLine()) != null) response.append(inputLine);
        }
        return new JSONObject(response.toString());
    }
}
