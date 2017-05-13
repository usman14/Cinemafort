package com.example.usman.videos.POJO;

/**
 * Created by usman on 5/3/2017.
 */

public class Created_by {
    private String id;

    private String name;

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

    public String getProfile_path ()
    {
        return profile_path;
    }

    public void setProfile_path (String profile_path)
    {
        this.profile_path = profile_path;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", name = "+name+", profile_path = "+profile_path+"]";
    }
}

