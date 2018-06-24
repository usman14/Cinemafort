package com.cinemafortlatest.usman.videos.UTILITIES;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by USMAN on 23/06/2018.
 */
public class Session_Management extends AppCompatActivity{

    SharedPreferences sharedPreferences;


    public void movie_id(Integer id, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("MOVIE_ID",id);
        editor.commit();

    }

    public void cast_id(Integer id, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("CAST_ID",id);
        editor.commit();

    }

    public void tv_show_id(int id, Context context) {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("TV_SHOW_ID",id);
        editor.commit();

    }
}
