package com.cinemafortlatest.usman.videos.Tabs_Movie_Detail_Activity;

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

import com.cinemafortlatest.usman.videos.ADAPTERS.Movie_Review_Adapter;
import com.cinemafortlatest.usman.videos.INTERFACES.ApiInterface;
import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.Review;
import com.cinemafortlatest.usman.videos.POJO.Review_Results;
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
        movieid=sharedPreferences.getInt("MOVIE_ID",0);
        Set_Adapter();
        return v;
    }

    public void Set_Adapter()
    {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        final rx.Observable<Review> result=apiInterface.getreview(movieid,Global.key);
        result.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Review>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Review review) {
                        results =review.getResults();
                        list= Arrays.asList(results);
                        movie_review_adapter=new Movie_Review_Adapter(getContext(), list, new Listener() {
                            @Override
                            public void onItemClick(View v, int position) {

                            }
                        });
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        recyclerView.setAdapter(movie_review_adapter);
                    }
                });
    }
}
