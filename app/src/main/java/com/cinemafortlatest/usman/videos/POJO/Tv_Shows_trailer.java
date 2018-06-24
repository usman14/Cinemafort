package com.cinemafortlatest.usman.videos.POJO;

/**
 * Created by usman on 5/3/2017.
 */

public class Tv_Shows_trailer {
    private String id;

    private Tv_Shows_trailer_Results[] results;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Tv_Shows_trailer_Results[] getResults ()
    {
        return results;
    }

    public void setResults (Tv_Shows_trailer_Results[] results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", results = "+results+"]";
    }
}


