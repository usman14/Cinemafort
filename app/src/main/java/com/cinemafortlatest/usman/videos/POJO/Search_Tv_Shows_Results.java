package com.cinemafortlatest.usman.videos.POJO;

/**
 * Created by usman on 4/24/2017.
 */

public class Search_Tv_Shows_Results {
    private String backdrop_path;

    private String vote_average;

    private String original_name;

    private String first_air_date;

    private String id;

    private String[] origin_country;

    private String overview;

    private String original_language;

    private String[] genre_ids;

    private String name;

    private String vote_count;

    private String poster_path;

    private String popularity;

    public String getBackdrop_path ()
    {
        return backdrop_path;
    }

    public void setBackdrop_path (String backdrop_path)
    {
        this.backdrop_path = backdrop_path;
    }

    public String getVote_average ()
    {
        return vote_average;
    }

    public void setVote_average (String vote_average)
    {
        this.vote_average = vote_average;
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

    public void setFirst_air_date (String first_air_date)
    {
        this.first_air_date = first_air_date;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String[] getOrigin_country ()
    {
        return origin_country;
    }

    public void setOrigin_country (String[] origin_country)
    {
        this.origin_country = origin_country;
    }

    public String getOverview ()
    {
        return overview;
    }

    public void setOverview (String overview)
    {
        this.overview = overview;
    }

    public String getOriginal_language ()
    {
        return original_language;
    }

    public void setOriginal_language (String original_language)
    {
        this.original_language = original_language;
    }

    public String[] getGenre_ids ()
    {
        return genre_ids;
    }

    public void setGenre_ids (String[] genre_ids)
    {
        this.genre_ids = genre_ids;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getVote_count ()
    {
        return vote_count;
    }

    public void setVote_count (String vote_count)
    {
        this.vote_count = vote_count;
    }

    public String getPoster_path ()
    {
        return poster_path;
    }

    public void setPoster_path (String poster_path)
    {
        this.poster_path = poster_path;
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
        return "ClassPojo [backdrop_path = "+backdrop_path+", vote_average = "+vote_average+", original_name = "+original_name+", first_air_date = "+first_air_date+", id = "+id+", origin_country = "+origin_country+", overview = "+overview+", original_language = "+original_language+", genre_ids = "+genre_ids+", name = "+name+", vote_count = "+vote_count+", poster_path = "+poster_path+", popularity = "+popularity+"]";
    }
}
