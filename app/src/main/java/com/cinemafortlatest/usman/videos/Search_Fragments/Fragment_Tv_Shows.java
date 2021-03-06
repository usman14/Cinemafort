package com.cinemafortlatest.usman.videos.Search_Fragments;

import android.content.Intent;
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

import com.cinemafortlatest.usman.videos.ADAPTERS.Search_Tv_Shows_Adapter;
import com.cinemafortlatest.usman.videos.Main_Activities.Activity_Tv_Shows_Detail;
import com.cinemafortlatest.usman.videos.INTERFACES.ApiInterface;
import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.Search_Tv_Shows;
import com.cinemafortlatest.usman.videos.POJO.Search_Tv_Shows_Results;
import com.cinemafortlatest.usman.videos.R;
import com.cinemafortlatest.usman.videos.UTILITIES.ApiClient;
import com.cinemafortlatest.usman.videos.UTILITIES.Global;

import java.util.ArrayList;
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

public class Fragment_Tv_Shows extends Fragment {
    RecyclerView rv;
    Search_Tv_Shows_Adapter search_actors_adapter;
    String value;
    SharedPreferences sharedPreferences;
    List<Search_Tv_Shows_Results> list;
    Search_Tv_Shows_Results[] arraylist;
    Listener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_simple,container,false);
        rv=(RecyclerView)v.findViewById(R.id.rv_fragment_cast);
        setHasOptionsMenu(true);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        /**value=sharedPreferences.getString("search_value","");
        if(!value.equals(""))
        {
            //value="rock";
            Init(value);
        }*/
        return v;
    }

    private void Init(final String s) {
        final ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        rx.Observable<Search_Tv_Shows> call=apiInterface.getsearch_tvshows(s, Global.key);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Search_Tv_Shows>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Search_Tv_Shows search_tv_shows) {
                        arraylist=search_tv_shows.getResults();
                        list = new ArrayList<>();
                        list= Arrays.asList(arraylist);
                        //arraylist= list.toArray(arraylist);
                        search_actors_adapter=new Search_Tv_Shows_Adapter(getContext(), list, new Listener() {
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
                        rv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rv.setAdapter(search_actors_adapter);
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
