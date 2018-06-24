package com.cinemafortlatest.usman.videos.POJO;

/**
 * Created by usman on 4/22/2017.
 */

public class Person
{
    private String birthday;

    private String adult;

    private String biography;

    private String homepage;

    private String profile_path;

    private String id;

    private String[] also_known_as;

    private String deathday;

    private String imdb_id;

    private String name;

    private String gender;

    private String place_of_birth;

    private String popularity;

    public String getBirthday ()
    {
        return birthday;
    }

    public void setBirthday (String birthday)
    {
        this.birthday = birthday;
    }

    public String getAdult ()
    {
        return adult;
    }

    public void setAdult (String adult)
    {
        this.adult = adult;
    }

    public String getBiography ()
    {
        return biography;
    }

    public void setBiography (String biography)
    {
        this.biography = biography;
    }

    public String getHomepage ()
    {
        return homepage;
    }

    public void setHomepage (String homepage)
    {
        this.homepage = homepage;
    }

    public String getProfile_path ()
    {
        return profile_path;
    }

    public void setProfile_path (String profile_path)
    {
        this.profile_path = profile_path;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String[] getAlso_known_as ()
    {
        return also_known_as;
    }

    public void setAlso_known_as (String[] also_known_as)
    {
        this.also_known_as = also_known_as;
    }

    public String getDeathday ()
    {
        return deathday;
    }

    public void setDeathday (String deathday)
    {
        this.deathday = deathday;
    }

    public String getImdb_id ()
    {
        return imdb_id;
    }

    public void setImdb_id (String imdb_id)
    {
        this.imdb_id = imdb_id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getPlace_of_birth ()
    {
        return place_of_birth;
    }

    public void setPlace_of_birth (String place_of_birth)
    {
        this.place_of_birth = place_of_birth;
    }

    public String getPopularity ()
    {
        return popularity;
    }

    public void setPopularity (String popularity)
    {
        this.popularity = popularity;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [birthday = "+birthday+", adult = "+adult+", biography = "+biography+", homepage = "+homepage+", profile_path = "+profile_path+", id = "+id+", also_known_as = "+also_known_as+", deathday = "+deathday+", imdb_id = "+imdb_id+", name = "+name+", gender = "+gender+", place_of_birth = "+place_of_birth+", popularity = "+popularity+"]";
    }
}

