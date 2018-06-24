package com.cinemafortlatest.usman.videos.Cast_Fragments;

import android.content.Intent;
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

import com.cinemafortlatest.usman.videos.ADAPTERS.Cast_Tv_Shows_Adapter;
import com.cinemafortlatest.usman.videos.Main_Activities.Activity_Tv_Shows_Detail;
import com.cinemafortlatest.usman.videos.INTERFACES.ApiInterface;
import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.Cast_2;
import com.cinemafortlatest.usman.videos.POJO.Cast_Tv_Shows;
import com.cinemafortlatest.usman.videos.R;
import com.cinemafortlatest.usman.videos.UTILITIES.ApiClient;
import com.cinemafortlatest.usman.videos.UTILITIES.Global;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usman on 4/24/2017.
 */

public class Cast_Tv_Shows_Credits  extends Fragment {
    RecyclerView rv;
    Cast_Tv_Shows_Adapter adapter;
    SharedPreferences sharedPreferences;
    int cast_id;
    List<Cast_2> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_simple,container,false);
        rv=(RecyclerView)v.findViewById(R.id.rv_fragment_cast) ;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        cast_id =sharedPreferences.getInt("CAST_ID",0);
        Get_Data();
        return v;
    }

    private void Get_Data() {
        final ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
        rx.Observable<Cast_Tv_Shows> result=apiservice.getcast_tvshows(cast_id, Global.key);
        result.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Cast_Tv_Shows>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Cast_Tv_Shows cast_tv_shows) {
                        Cast_2[] list_objects=cast_tv_shows.getCast();
                        list= Arrays.asList(list_objects);
                        adapter=new Cast_Tv_Shows_Adapter(getContext(), list, new Listener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Intent intent=new Intent(getContext(), Activity_Tv_Shows_Detail.class);
                                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getContext());
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putInt("tvshow_id",Integer.parseInt(list.get(position).getId()));
                                editor.commit();
                                getActivity().startActivity(intent);
                            }
                        });
                        rv.setLayoutManager(new GridLayoutManager(getContext(),3));
                        rv.setAdapter(adapter);
                    }
                });
    }
}
