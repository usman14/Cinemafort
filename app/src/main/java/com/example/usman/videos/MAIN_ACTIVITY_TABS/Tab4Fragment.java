package com.example.usman.videos.MAIN_ACTIVITY_TABS;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usman.videos.ADAPTERS.Movie_List_Adapter;
import com.example.usman.videos.ADAPTERS.Movie_List_Adapter_Gridview;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Movie;
import com.example.usman.videos.POJO.MoviesResponse;
import com.example.usman.videos.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tab4Fragment extends Fragment {
    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    RecyclerView recyclerView;
    Movie_List_Adapter adapter;
    Movie_List_Adapter_Gridview gridview_adapter;
    boolean isProductViewAsList;
    List<Movie> list;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_simple, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_fragment_cast);
        setHasOptionsMenu(true);
        isProductViewAsList=true;

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getpopular(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode = response.code();
                list= response.body().getResults();
                Recyler_Creater(list);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });

        /**ImageButton toolbar = (ImageButton) getActivity().findViewById(R.id.imb_btn_toobar_search);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHasOptionsMenu(true);
                setLayoutManager();

            }
        });*/
        return v;
    }
    public void Make_list(View v)
    {
        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
        }
        else
        {
            isProductViewAsList=true;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            final ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    int statusCode = response.code();
                    final List<Movie> movies = response.body().getResults();
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

                    adapter=new Movie_List_Adapter(getContext(), movies, new Listener() {
                        @Override
                        public void onItemClick(View v, int position) {

                        }
                    });
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {

                }
            });
        }
    }
    private void setLayoutManager() {
        if (isProductViewAsList ==true) {
            isProductViewAsList =false;
            Recyler_Creater1(list);
            //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            Recyler_Creater(list);

            isProductViewAsList=true;
        }
    }

    public void Recyler_Creater(List<Movie> list)
    {
        adapter=new Movie_List_Adapter(getContext(), list, new Listener() {
            @Override
            public void onItemClick(View v, int position) {


            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);
    }public void Recyler_Creater1(List<Movie> list)
    {
        gridview_adapter=new Movie_List_Adapter_Gridview(getContext(), list, new Listener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        recyclerView.setAdapter(gridview_adapter);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_favourites, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                setLayoutManager();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
