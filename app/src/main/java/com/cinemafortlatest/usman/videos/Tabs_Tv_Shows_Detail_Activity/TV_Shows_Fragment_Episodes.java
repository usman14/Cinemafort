package com.cinemafortlatest.usman.videos.Tabs_Tv_Shows_Detail_Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinemafortlatest.usman.videos.ADAPTERS.Tv_Shows_Episode_Adapter;
import com.cinemafortlatest.usman.videos.Main_Activities.Activity_Cast;
import com.cinemafortlatest.usman.videos.INTERFACES.ApiInterface;
import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.Seasons;
import com.cinemafortlatest.usman.videos.POJO.Tv_Shows_Detail_Basic;
import com.cinemafortlatest.usman.videos.R;
import com.cinemafortlatest.usman.videos.UTILITIES.ApiClient;
import com.cinemafortlatest.usman.videos.UTILITIES.Global;
import com.cinemafortlatest.usman.videos.UTILITIES.Session_Management;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usman on 5/3/2017.
 */

public class TV_Shows_Fragment_Episodes extends Fragment {
    SharedPreferences sharedPreferences;
    int tv_show_id;
    Tv_Shows_Episode_Adapter adapter;
    RecyclerView rv;
    Session_Management session_management;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_simple,container,false);
        rv =(RecyclerView)v.findViewById(R.id.rv_fragment_cast);
        session_management=new Session_Management();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        tv_show_id =sharedPreferences.getInt("TV_SHOW_ID",0);
        Get_Data();
        return v;
    }
    private void Get_Data() {

        final ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
        rx.Observable<Tv_Shows_Detail_Basic> result=apiservice.get_tv_show_episodes(String.valueOf(tv_show_id), Global.key);
        result.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Tv_Shows_Detail_Basic>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Tv_Shows_Detail_Basic tv_shows_detail_basic) {
                        Tv_Shows_Detail_Basic castcrew=tv_shows_detail_basic;
                        final Seasons[] cast=castcrew.getSeasons();
                        final List<Seasons> list= Arrays.asList(cast);
                        Collections.reverse(list);

                        adapter=new Tv_Shows_Episode_Adapter(getContext(), list, new Listener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Intent intent=new Intent(getActivity().getBaseContext(),Activity_Cast.class);
                                session_management.cast_id(Integer.parseInt(list.get(position).getId()),getContext());

                                getActivity().startActivity(intent);
                            }
                        });
                        rv.setLayoutManager(new GridLayoutManager(getContext(),2));

                        rv.setAdapter(adapter);
                    }
                });
    }
}