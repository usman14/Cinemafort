package com.cinemafortlatest.usman.videos.Tabs_Tv_Shows_Fragment;

import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cinemafortlatest.usman.videos.ADAPTERS.Tv_Shows_List_Adapter;
import com.cinemafortlatest.usman.videos.ADAPTERS.Tv_Shows_List_Adapter_GridView;
import com.cinemafortlatest.usman.videos.Main_Activities.Activity_Search;
import com.cinemafortlatest.usman.videos.Main_Activities.Activity_Tv_Shows_Detail;
import com.cinemafortlatest.usman.videos.INTERFACES.ApiInterface;
import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.MoviesResponse;
import com.cinemafortlatest.usman.videos.POJO.Tv_Shows_Popular;
import com.cinemafortlatest.usman.videos.POJO.Tv_Shows_Popular_Results;
import com.cinemafortlatest.usman.videos.R;
import com.cinemafortlatest.usman.videos.UTILITIES.ApiClient;
import com.cinemafortlatest.usman.videos.UTILITIES.Session_Management;

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

public class Fragment_Airing_Today extends Fragment {
    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    RecyclerView rv;
    Tv_Shows_List_Adapter adapter;
    Tv_Shows_List_Adapter_GridView gridview_adapter;
    boolean isProductViewAsList;
    List<Tv_Shows_Popular_Results> list;
    SharedPreferences sharedPreferences;
    private Menu menu;
    ProgressDialog progressDialog;
    Session_Management session_management;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_simple, container, false);
        rv = (RecyclerView) v.findViewById(R.id.rv_fragment_cast);
        session_management=new Session_Management();

        setHasOptionsMenu(true);
        isProductViewAsList=true;
        progressDialog = ProgressDialog.show(getContext(), "",
                "Loading", true);
        //progressDialog.setMessage("Downloading Videos");
        progressDialog.show();
        Make_list(v);
        return v;
    }
    public void Make_list(View v)
    {
        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
        }
        else
        {

            final ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            rx.Observable<Tv_Shows_Popular> call = apiService.get_tvshow_airingtoday(API_KEY);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Tv_Shows_Popular>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onNext(Tv_Shows_Popular moviesResponse) {
                            progressDialog.hide();
                            Tv_Shows_Popular_Results[] list_object;
                            list_object=moviesResponse.getResults();
                            list= Arrays.asList(list_object);
                            Recyler_Creater_List_View(list);
                        }
                    });
        }
    }

    public void Recyler_Creater_List_View(final List<Tv_Shows_Popular_Results> list)
    {
        adapter=new Tv_Shows_List_Adapter(getContext(), list, new Listener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent=new Intent(getActivity().getBaseContext(),Activity_Tv_Shows_Detail.class);
                session_management.tv_show_id(Integer.parseInt(list.get(position).getId()),getContext());

                getActivity().startActivity(intent);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    public void Recyler_Creater1(final List<Tv_Shows_Popular_Results> list)
    {
        gridview_adapter=new Tv_Shows_List_Adapter_GridView(getContext(), list, new Listener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent=new Intent(getActivity().getBaseContext(),Activity_Tv_Shows_Detail.class);
                session_management.tv_show_id(Integer.parseInt(list.get(position).getId()),getContext());

                getActivity().startActivity(intent);
            }
        });
        rv.setLayoutManager(new GridLayoutManager(getContext(), 3));

        rv.setAdapter(gridview_adapter);
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
                if (isProductViewAsList ==true)
                {
                    isProductViewAsList =false;
                    Recyler_Creater1(list);
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.listview));

                } else {

                    Recyler_Creater_List_View(list);
                    isProductViewAsList=true;
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.grid_view));

                }

                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
