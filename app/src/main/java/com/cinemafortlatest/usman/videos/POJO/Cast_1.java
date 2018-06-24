package com.cinemafortlatest.usman.videos.POJO;

/**
 * Created by usman on 4/24/2017.
 */

public class Cast_1
{
    private String id;

    private String title;

    private String credit_id;

    private String release_date;

    private String original_title;

    private String adult;

    private String poster_path;

    private String character;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getCredit_id ()
    {
        return credit_id;
    }

    public void setCredit_id (String credit_id)
    {
        this.credit_id = credit_id;
    }

    public String getRelease_date ()
    {
        return release_date;
    }

    public void setRelease_date (String release_date)
    {
        this.release_date = release_date;
    }

    public String getOriginal_title ()
    {
        return original_title;
    }

    public void setOriginal_title (String original_title)
    {
        this.original_title = original_title;
    }

    public String getAdult ()
    {
        return adult;
    }

    public void setAdult (String adult)
    {
        this.adult = adult;
    }

    public String getPoster_path ()
    {
        return poster_path;
    }

    public void setPoster_path (String poster_path)
    {
        this.poster_path = poster_path;
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
        return "ClassPojo [id = "+id+", title = "+title+", credit_id = "+credit_id+", release_date = "+release_date+", original_title = "+original_title+", adult = "+adult+", poster_path = "+poster_path+", character = "+character+"]";
    }
}