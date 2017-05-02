package com.example.usman.videos.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by usman on 4/18/2017.
 */

public class moviedate {
    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }
    public moviedate(String maximum, String minimum)
    {
        this.maximum=maximum;
        this.minimum=minimum;
    }

    @SerializedName("maximum")
    private String maximum;
    @SerializedName("minimum")
    private String minimum;
}
