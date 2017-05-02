package com.example.usman.videos.POJO;

/**
 * Created by usman on 4/20/2017.
 */

public class Trailer
{
    private String id;

    private Results[] results;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Results[] getResults ()
    {
        return results;
    }

    public void setResults (Results[] results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", results = "+results+"]";
    }
}


