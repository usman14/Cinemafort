package com.cinemafortlatest.usman.videos.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by usman on 4/18/2017.
 */

public class Token_new implements Serializable{

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    @SerializedName("request_token")
    @Expose
    public String request_token;

    @SerializedName("session_id")
    @Expose
    public String session_id;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @SerializedName("success")
    @Expose
    public boolean success;
}
