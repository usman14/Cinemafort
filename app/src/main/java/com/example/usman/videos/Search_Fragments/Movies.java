package com.example.usman.videos.Search_Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.usman.videos.ADAPTERS.Search_Actors_Adapter;
import com.example.usman.videos.ADAPTERS.Search_Movie_Adapter;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Search_Actor_Results;
import com.example.usman.videos.POJO.Search_Actors;
import com.example.usman.videos.POJO.Search_Movie_Results;
import com.example.usman.videos.POJO.Search_Movies;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by usman on 4/24/2017.
 */

public class Movies extends Fragment {
    RecyclerView rv;
    Search_Movie_Adapter adapter;
    String value;
    SharedPreferences sharedPreferences;
    List<Search_Movie_Results> list;
    Search_Movie_Results[] arraylist;
    Listener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_simple,container,false);
        rv=(RecyclerView)v.findViewById(R.id.rv_fragment_cast);
        setHasOptionsMenu(true);
        return v;
    }

    private void Init(String s) {
        final ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<Search_Movies> call=apiInterface.getsearch_movie(s, Global.key);
        call.enqueue(new Callback<Search_Movies>() {
            @Override
            public void onResponse(Call<Search_Movies> call, Response<Search_Movies> response) {
                int as=response.code();
                arraylist=response.body().getResults();
                list = new ArrayList<>();
                list= Arrays.asList(arraylist);
                arraylist= list.toArray(arraylist);
                adapter=new Search_Movie_Adapter(getContext(), list, new Listener() {
                    @Override
                    public void onItemClick(View v, int position) {

                    }
                });
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setAdapter(adapter);
            }


            @Override
            public void onFailure(Call<Search_Movies> call, Throwable t) {

            }
        });


    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        /**if (!value.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(value, false);
            searchView.setQueryHint(value);

        }*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                //UserFeedback.show( "SearchOnQueryTextSubmit: " + query);

                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if(!s.equals(""))
                {
                    Init(s);

                }

                return false;
            }
        });

    }

}


