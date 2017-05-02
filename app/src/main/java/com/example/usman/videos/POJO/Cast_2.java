package com.example.usman.videos.POJO;

/**
 * Created by usman on 4/24/2017.
 */

public class Cast_2
{
    private String id;

    private String episode_count;

    private String credit_id;

    private String name;

    private String poster_path;

    private String original_name;

    private String first_air_date;

    private String character;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getEpisode_count ()
    {
        return episode_count;
    }

    public void setEpisode_count (String episode_count)
    {
        this.episode_count = episode_count;
    }

    public String getCredit_id ()
    {
        return credit_id;
    }

    public void setCredit_id (String credit_id)
    {
        this.credit_id = credit_id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getPoster_path ()
    {
        return poster_path;
    }

    public void setPoster_path (String poster_path)
    {
        this.poster_path = poster_path;
    }

    public String getOriginal_name ()
    {
        return original_name;
    }

    public void setOriginal_name (String original_name)
    {
        this.original_name = original_name;
    }

    public String getFirst_air_date ()
{
    return first_air_date;
}

    public void setFirst_air_date (String  first_air_date)
    {
        this.first_air_date = first_air_date;
    }

    public String getCharacter ()
    {
        return character;
    }

    public void setCharacter (String character)
    {
        this.character = character;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", episode_count = "+episode_count+", credit_id = "+credit_id+", name = "+name+", poster_path = "+poster_path+", original_name = "+original_name+", first_air_date = "+first_air_date+", character = "+character+"]";
    }
}

