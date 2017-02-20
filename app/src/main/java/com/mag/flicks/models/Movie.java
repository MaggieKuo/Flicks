package com.mag.flicks.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by perfumekuo on 2017/2/15.
 */

@Data public class Movie{
    long id;
    String poster_path;
    boolean adult;
    String overview;
    String release_date;
    List<Integer> genre_ids;
    String original_title;
    String original_language;
    String title;
    String backdrop_path;
    float popularity;
    int vote_count;
    boolean video;
    float vote_average;

    public Movie(){

    }

}
