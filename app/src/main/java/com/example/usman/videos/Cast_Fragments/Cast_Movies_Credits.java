package com.example.usman.videos.Cast_Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usman.videos.ADAPTERS.Cast_Movies_Adapter;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Cast_1;
import com.example.usman.videos.POJO.Cast_Movies;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by usman on 4/24/2017.
 */

public class Cast_Movies_Credits extends Fragment {
    RecyclerView rv;
    Cast_Movies_Adapter adapter;
    SharedPreferences sharedPreferences;
    int value;
    List<Cast_1> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_simple,container,false);
        rv=(RecyclerView)v.findViewById(R.id.rv_fragment_cast) ;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        value=sharedPreferences.getInt("cast_id",0);
        Init();
        return v;
    }

    private void Init() {
        final ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
        Call<Cast_Movies> result=apiservice.getcast_movie(value, Global.key);
        result.enqueue(new Callback<Cast_Movies>() {
            @Override
            public void onResponse(Call<Cast_Movies> call, Response<Cast_Movies> response) {
                Cast_1[] result=response.body().getCast();
                list= Arrays.asList(result);
                adapter=new Cast_Movies_Adapter(getContext(), list, new Listener() {
                    @Override
                    public void onItemClick(View v, int position) {

                    }
                });
                rv.setLayoutManager(new GridLayoutManager(getContext(),3));
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Cast_Movies> call, Throwable t) {

            }
        });

    }
}
