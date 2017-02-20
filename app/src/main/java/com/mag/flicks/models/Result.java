package com.mag.flicks.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data public class Result {
    int page;
    ArrayList<Movie> results;
    Dates dates;
    int total_pages;
    int total_results;

    public Result(){
        results = new ArrayList<>();
    }

    @Data public class Dates{
        String maximum;
        String minimum;

        public Dates(){

        }
    }

    public Movie getMovieByPosition(int position){
        if (results==null || results.size()<1)
            return null;

        return results.get(position);
    }

}
