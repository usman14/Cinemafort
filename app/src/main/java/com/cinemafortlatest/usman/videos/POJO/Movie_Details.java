package com.cinemafortlatest.usman.videos.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by usman on 4/15/2017.
 */

public class Movie_Details {
    @SerializedName("release_date")
    String release_date;
    @SerializedName("original_language")
    String original_language;
    @SerializedName("budget")
    int budget;
    @SerializedName("runtime")
    int runtime;

    @SerializedName("genres")

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }





    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }



    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


}
