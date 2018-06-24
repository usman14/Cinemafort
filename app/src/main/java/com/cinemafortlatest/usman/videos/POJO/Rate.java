package com.cinemafortlatest.usman.videos.POJO;

import java.io.Serializable;

/**
 * Created by usman on 4/21/2017.
 */

public class Rate implements Serializable {
    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    private int status_code;
    private String status_message;
}
