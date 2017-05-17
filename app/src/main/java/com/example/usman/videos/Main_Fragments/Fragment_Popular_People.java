package com.example.usman.videos.Main_Fragments;

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

import com.example.usman.videos.ADAPTERS.Popular_People_Adapter;
import com.example.usman.videos.Main_Activities.Activity_Cast;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Popular_People;
import com.example.usman.videos.POJO.Popular_People_Results;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usman on 5/3/2017.
 */

public class Fragment_Popular_People extends Fragment {
    SharedPreferences sharedPreferences;
    Popular_People_Adapter adapter;
    RecyclerView rv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_simple,container,false);
        rv =(RecyclerView)v.findViewById(R.id.rv_fragment_cast);
        Init();

        return v;
    }

    private void Init() {

        final ApiInterface apiservice= ApiClient.getClient().create(ApiInterface.class);
        rx.Observable<Popular_People> result=apiservice.get_popular_person( Global.key);
        result.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Popular_People>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Popular_People popular_people) {
                        Popular_People_Results[] list=popular_people.getResults();
                        final List<Popular_People_Results> cast= Arrays.asList(list);
                        adapter =new Popular_People_Adapter(getContext(), cast, new Listener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Intent intent=new Intent(getActivity().getBaseContext(),Activity_Cast.class);
                                sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putInt("cast_id",Integer.parseInt(cast.get(position).getId()));
                                editor.commit();
                                getActivity().startActivity(intent);
                            }
                        });
                        rv.setLayoutManager(new LinearLayoutManager(getContext()));

                        rv.setAdapter(adapter);
                    }
                });
    }
}
