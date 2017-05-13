package com.example.usman.videos.Tabs_Movie_Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.example.usman.videos.ADAPTERS.Movie_List_Adapter;
import com.example.usman.videos.ADAPTERS.Movie_List_Adapter_Gridview;
import com.example.usman.videos.Main_Activities.Activity_Movie_Detail;
import com.example.usman.videos.Main_Activities.Activity_Search;
import com.example.usman.videos.POJO.Token_new;
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

public class Fragment_Top_Rated extends Fragment {
    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    RecyclerView recyclerView;
    Movie_List_Adapter adapter;
    Movie_List_Adapter_Gridview gridview_adapter;
    boolean isProductViewAsList;
    List<Movie> list;
    String token;
    SharedPreferences sharedPreferences;
    private Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isProductViewAsList=true;
        View v = inflater.inflate(R.layout.recycler_view_simple, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_fragment_cast);
        setHasOptionsMenu(true);
        isProductViewAsList=true;
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Token_new> detail = apiService1.gettoken(API_KEY);
        detail.enqueue(new Callback<Token_new>() {
            @Override
            public void onResponse(Call<Token_new> call, Response<Token_new> response) {
                token=response.body().request_token;
                //Toast.makeText(getContext(),token,Toast.LENGTH_SHORT).show();
                String urls="https://www.themoviedb.org/authenticate/"+token;
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(urls));
                //startActivity(intent);
            }


            @Override
            public void onFailure(Call<Token_new> call, Throwable t) {

            }
        });
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
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

        return v;
    }
    private void setLayoutManager() {


    }

    public void Recyler_Creater(final List<Movie> list)
    {
        adapter=new Movie_List_Adapter(getContext(), list, new Listener() {
            @Override
            public void onItemClick(View v, final int position) {

                Intent intent=new Intent(getActivity().getBaseContext(),Activity_Movie_Detail.class);
                sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("movie_id",list.get(position).getId());
                editor.commit();
                getActivity().startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);
    }
    public void Recyler_Creater1(final List<Movie> list)
    {
        gridview_adapter=new Movie_List_Adapter_Gridview(getContext(), list, new Listener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent=new Intent(getActivity().getBaseContext(),Activity_Movie_Detail.class);
                sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("movie_id",list.get(position).getId());
                editor.commit();
                //intent.putExtra("id",list.get(position).getId());
                getActivity().startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        recyclerView.setAdapter(gridview_adapter);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu=menu;
        inflater.inflate(R.menu.menu_favourites, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                //setLayoutManager();
                Intent intent=new Intent(getActivity().getBaseContext(), Activity_Search.class);
                getActivity().startActivity(intent);

                return true;

            case R.id.action_settings:
                if (isProductViewAsList ==true) {
                    isProductViewAsList =false;
                    Recyler_Creater1(list);
                    //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.listview));

                } else {

                    Recyler_Creater(list);
                    isProductViewAsList=true;
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.grid_view));

                }
                setLayoutManager();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}