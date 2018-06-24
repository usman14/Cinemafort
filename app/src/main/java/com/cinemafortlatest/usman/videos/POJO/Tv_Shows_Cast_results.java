package com.cinemafortlatest.usman.videos.POJO;

/**
 * Created by usman on 5/3/2017.
 */

class Tv_Shows_Cast_results {
    private String id;

    private String order;

    private String credit_id;

    private String name;

    private String profile_path;

    private String character;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getOrder ()
    {
        return order;
    }

    public void setOrder (String order)
    {
        this.order = order;
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

    public String getProfile_path ()
    {
        return profile_path;
    }

    public void setProfile_path (String profile_path)
    {
        this.profile_path = profile_path;
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
        return "ClassPojo [id = "+id+", order = "+order+", credit_id = "+credit_id+", name = "+name+", profile_path = "+profile_path+", character = "+character+"]";
    }
}