package com.izuking.udacity.android.watchmovies.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Izuking on 4/14/2017.
 */
public class JSONObject {
    private static final String RESULT = "results";
    private static final String ID = "id";
    private static final String ORIGINALTITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String POSTERPATH = "poster_path";
    private static final String RELEASEDATE = "release_date";
    private static final String VOTEAVERAGE = "vote_average";

    private List<JSONResponse> parsedMovies;
    private JSONResponse response;


    public List<JSONResponse> getMovieList(String jsonResponse) throws JSONException {
        org.json.JSONObject object = new org.json.JSONObject(jsonResponse);
        JSONArray data = object.getJSONArray(RESULT);
        parsedMovies = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            org.json.JSONObject movieInfo = (org.json.JSONObject) data.get(i);
            response = new JSONResponse();

            response.setId(movieInfo.getInt(ID));
            response.setOriginal_title(movieInfo.getString(ORIGINALTITLE));
            response.setOverview(movieInfo.getString(OVERVIEW));
            response.setPoster_path(movieInfo.getString(POSTERPATH));
            response.setRelease_date(movieInfo.getString(RELEASEDATE));
            response.setVote_average(movieInfo.getString(VOTEAVERAGE));


            parsedMovies.add(response);

        }

        return parsedMovies;
    }

    public class JSONResponse {
        private String original_title;
        private String poster_path;
        private String overview;
        private String vote_average;
        private String release_date;
        private int id;


        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getVote_average() {
            return vote_average;
        }

        public void setVote_average(String vote_average) {
            this.vote_average = vote_average;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }


    }
}
