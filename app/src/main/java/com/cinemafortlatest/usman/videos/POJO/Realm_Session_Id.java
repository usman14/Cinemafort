package com.cinemafortlatest.usman.videos.POJO;

import io.realm.RealmObject;

/**
 * Created by usman on 5/1/2017.
 */

public class Realm_Session_Id extends RealmObject {
    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    String session_id;

}
