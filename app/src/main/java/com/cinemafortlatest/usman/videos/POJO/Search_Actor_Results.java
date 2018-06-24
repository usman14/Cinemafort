package com.cinemafortlatest.usman.videos.POJO;

/**
 * Created by usman on 4/24/2017.
 */

public class Search_Actor_Results {
    private String id;


    private String name;

    private String adult;

    private String popularity;

    private String profile_path;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }



    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getAdult ()
    {
        return adult;
    }

    public void setAdult (String adult)
    {
        this.adult = adult;
    }

    public String getPopularity ()
    {
        return popularity;
    }

    public void setPopularity (String popularity)
    {
        this.popularity = popularity;
    }

    public String getProfile_path ()
    {
        return profile_path;
    }

    public void setProfile_path (String  profile_path)
    {
        this.profile_path = profile_path;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", known_for = "+", name = "+name+", adult = "+adult+", popularity = "+popularity+", profile_path = "+profile_path+"]";
    }
}
