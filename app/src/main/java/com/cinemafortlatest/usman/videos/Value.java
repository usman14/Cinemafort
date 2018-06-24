package com.cinemafortlatest.usman.videos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by usman on 5/15/2017.
 */

public class Value {


        @SerializedName("value")
        float value;

        public Value(float id ) {
            this.value = id;
        }
    }

