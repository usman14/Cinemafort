package com.cinemafortlatest.usman.videos.POJO;

import com.cinemafortlatest.usman.videos.POJO.Movie;
import com.cinemafortlatest.usman.videos.POJO.moviedate;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Movies_Getupcoming {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("dates")
    private List<moviedate> dates;
    public List<moviedate> getDates() {return dates;}

    public void setDates(List<moviedate> dates) {this.dates = dates;}


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
