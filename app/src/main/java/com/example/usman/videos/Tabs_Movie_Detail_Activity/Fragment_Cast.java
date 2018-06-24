package com.example.usman.videos.Tabs_Movie_Detail_Activity;

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

import com.example.usman.videos.ADAPTERS.Movie_Cast_Adapter;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.Main_Activities.Activity_Cast;
import com.example.usman.videos.POJO.CastCrew;
import com.example.usman.videos.POJO.Cast_one;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;
import com.example.usman.videos.UTILITIES.Session_Management;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    Session_Management session_management;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_simple,container,false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rv_fragment_cast) ;
            session_management=new Session_Management();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        movieid=sharedPreferences.getInt("MOVIE_ID",0);
             id=String.valueOf(movieid);
        Set_Adapter();
        return v;
    }
    public void Set_Adapter()
    {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        final rx.Observable<CastCrew> call=apiInterface.getcast(id, Global.key);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CastCrew>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CastCrew castCrew) {
                        list =castCrew.getCast();
                        adapter=new Movie_Cast_Adapter(getContext(), list, new Listener() {
                            @Override
                            public void onItemClick(View v, int position) {

                                Intent intent = new Intent(getActivity().getBaseContext(), Activity_Cast.class);
                                session_management.cast_id(list.get(position).getId(),getContext());
                                getActivity().startActivity(intent);
                            }
                        });
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        recyclerView.setAdapter(adapter);

                    }
                });
    }
}

