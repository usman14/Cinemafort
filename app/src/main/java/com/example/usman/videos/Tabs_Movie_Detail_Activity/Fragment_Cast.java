package com.example.usman.videos.Tabs_Movie_Detail_Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usman.videos.ADAPTERS.Movie_Cast_Adapter;
import com.example.usman.videos.ADAPTERS.Movie_Review_Adapter;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.Main_Activities.Activity_Cast;
import com.example.usman.videos.Main_Activities.Activity_Movie_Detail;
import com.example.usman.videos.POJO.CastCrew;
import com.example.usman.videos.POJO.Cast_one;
import com.example.usman.videos.POJO.Review;
import com.example.usman.videos.POJO.Review_Results;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by usman on 5/4/2017.
 */

public class Fragment_Cast extends Fragment{

        int movieid;
    List<Cast_one> list;
    Movie_Cast_Adapter adapter;
        SharedPreferences sharedPreferences;
        RecyclerView recyclerView;
        String id;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_simple,container,false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rv_fragment_cast) ;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        movieid=sharedPreferences.getInt("movie_id",0);
             id=String.valueOf(movieid);
        Set_Adapter();
        return v;
    }
    public void Set_Adapter()
    {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        final Call<CastCrew> result=apiInterface.getcast(id, Global.key);
        result.enqueue(new Callback<CastCrew>() {
            @Override
            public void onResponse(Call<CastCrew> call, Response<CastCrew> response) {
                list =response.body().getCast();
                adapter=new Movie_Cast_Adapter(getContext(), list, new Listener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        Intent intent = new Intent(getActivity().getBaseContext(), Activity_Cast.class);
                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("cast_id", list.get(position).getId());
                        editor.commit();
                        getActivity().startActivity(intent);
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<CastCrew> call, Throwable t) {

            }
        });
    }
}
