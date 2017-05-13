package com.example.usman.videos.Tabs_Tv_Shows_Detail_Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usman.videos.ADAPTERS.Tv_Show_Cast_Adapter;
import com.example.usman.videos.Main_Activities.Activity_Cast;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.CastCrew;
import com.example.usman.videos.POJO.Cast_one;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by usman on 5/3/2017.
 */

public class TV_Shows_Fragment_Cast extends Fragment {
    SharedPreferences sharedPreferences;
    int tv_show_id;
    Tv_Show_Cast_Adapter adapter;
    RecyclerView rv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_simple,container,false);
        rv =(RecyclerView)v.findViewById(R.id.rv_fragment_cast);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        tv_show_id =sharedPreferences.getInt("tvshow_id",0);
        rv =(RecyclerView)v.findViewById(R.id.rv_fragment_cast);
        Get_Data();

        return v;
    }

    private void Get_Data() {

        final ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
        Call<CastCrew> result=apiservice.get_tv_show_cast(String.valueOf(tv_show_id), Global.key);
        result.enqueue(new Callback<CastCrew>() {
            @Override
            public void onResponse(Call<CastCrew> call, Response<CastCrew> response) {
                CastCrew castcrew=response.body();
                final List<Cast_one> cast=castcrew.getCast();
                adapter =new Tv_Show_Cast_Adapter(getContext(), cast, new Listener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent=new Intent(getActivity().getBaseContext(),Activity_Cast.class);
                        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putInt("cast_id",cast.get(position).getId());
                        editor.commit();
                        getActivity().startActivity(intent);
                    }
                });
                rv.setLayoutManager(new LinearLayoutManager(getContext()));

                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CastCrew> call, Throwable t) {

            }
        });


    }
}

