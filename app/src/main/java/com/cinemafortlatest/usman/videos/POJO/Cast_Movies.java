package com.cinemafortlatest.usman.videos.POJO;

/**
 * Created by usman on 4/24/2017.
 */

public class Cast_Movies
{
    private String id;

    private Cast_1[] cast;

    private Crew[] crew;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Cast_1[] getCast ()
    {
        return cast;
    }

    public void setCast (Cast_1[] cast)
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
