package com.example.usman.videos.Tabs_Movie_Detail_Activity;

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

import com.example.usman.videos.ADAPTERS.Movie_Review_Adapter;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
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
 * Created by usman on 4/18/2017.
 */

public class Fragment_Reviews extends Fragment {
    int movieid;
    Review_Results[]results;
    List<Review_Results> list;
    Movie_Review_Adapter movie_review_adapter;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_simple,container,false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rv_fragment_cast) ;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        movieid=sharedPreferences.getInt("movie_id",0);
        Set_Adapter();
        return v;
    }

    public void Set_Adapter()
    {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        final Call<Review> result=apiInterface.getreview(movieid,Global.key);
        result.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                results =response.body().getResults();
                list= Arrays.asList(results);
                movie_review_adapter=new Movie_Review_Adapter(getContext(), list, new Listener() {
                    @Override
                    public void onItemClick(View v, int position) {

                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                recyclerView.setAdapter(movie_review_adapter);

            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }
}
