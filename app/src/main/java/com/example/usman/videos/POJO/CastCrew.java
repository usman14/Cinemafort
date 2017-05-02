
package com.example.usman.videos.POJO;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CastCrew implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private List<Cast_one> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;
    private final static long serialVersionUID = 8714964834295219390L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Cast_one> getCast() {
        return cast;
    }

    public void setCast(List<Cast_one> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

}
