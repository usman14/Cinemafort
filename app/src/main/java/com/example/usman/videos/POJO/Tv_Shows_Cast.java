package com.example.usman.videos.POJO;

/**
 * Created by usman on 5/3/2017.
 */

public class Tv_Shows_Cast {
    private String id;

    private Tv_Shows_Cast_results[] cast;

    private Crew[] crew;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Tv_Shows_Cast_results[] getCast ()
    {
        return cast;
    }

    public void setCast (Tv_Shows_Cast_results[] cast)
    {
        this.cast = cast;
    }

    public Crew[] getCrew ()
    {
        return crew;
    }

    public void setCrew (Crew[] crew)
    {
        this.crew = crew;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", cast = "+cast+", crew = "+crew+"]";
    }
}
