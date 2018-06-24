package com.cinemafortlatest.usman.videos.Main_Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cinemafortlatest.usman.videos.ADAPTERS.Discover_Movie_Adapter;
import com.cinemafortlatest.usman.videos.Main_Activities.Activity_Movie_Detail;
import com.cinemafortlatest.usman.videos.INTERFACES.ApiInterface;
import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.Main_Activities.Activity_Search;
import com.cinemafortlatest.usman.videos.POJO.Discover_Pojo;
import com.cinemafortlatest.usman.videos.POJO.Discover_Results;
import com.cinemafortlatest.usman.videos.R;
import com.cinemafortlatest.usman.videos.UTILITIES.ApiClient;
import com.cinemafortlatest.usman.videos.UTILITIES.Global;
import com.cinemafortlatest.usman.videos.UTILITIES.Utilites;

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
 * Created by usman on 4/29/2017.
 */

public class Fragment_Advanced_Search extends Fragment {
    Button btn_yearone, btn_yeartwo, btn_category,btn_sorting;
    RecyclerView rv;
    List<Discover_Results> list;
    Discover_Results[] arraylist;
    Discover_Movie_Adapter adapter;
    int category;
    String sorting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.search_advanced,container,false);
        setHasOptionsMenu(true);
        Get_Widgets(v);
        Get_Results();
        return v;
    }



    private void Get_Widgets(View v) {
        btn_yearone =(Button) v.findViewById(R.id.btn_fragment_discover_year_one);
        btn_yeartwo =(Button) v.findViewById(R.id.btn_fragment_discover_year_two);

        btn_category=(Button) v.findViewById(R.id.btn_fragment_discover_category);

        btn_sorting =(Button) v.findViewById(R.id.btn_fragment_discover_genre);
        rv=(RecyclerView)v.findViewById(R.id.rv_new_fragment_discover);
        sorting="popularity.desc";

        btn_yearone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                for(int a=2017;a>=1905;a--)
                {
                    list.add(String.valueOf(a));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);


                builder.setItems(cs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    btn_yearone.setText(cs[which]);
                        Get_Results();

                    }
                });

                AlertDialog alert = builder.create();
                WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
                wmlp.gravity = Gravity.TOP | Gravity.LEFT;
                wmlp.x = 10;   //x position
                wmlp.y = 100;   //y position
                alert.show();
                alert.getWindow().setLayout(350, 900);
            }
        });

        btn_yeartwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                for(int a=2017;a>=1905;a--)
                {
                    list.add(String.valueOf(a));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);


                builder.setItems(cs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    btn_yeartwo.setText(cs[which]);
                        Get_Results();

                    }
                });

                AlertDialog alert = builder.create();
                WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
                wmlp.gravity = Gravity.TOP | Gravity.LEFT;
                wmlp.x = 150;   //x position
                wmlp.y = 100;   //y position
                alert.show();
                alert.getWindow().setLayout(350, 900);
            }
        });

        btn_sorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();

                final CharSequence[] items =
                        {"Release Date","Popularity", "Revenue"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);


                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    btn_sorting.setText(items[which]);
                       sorting= Utilites.Sorting_Order(which);

                        Get_Results();

                    }
                });

                AlertDialog alert = builder.create();
                WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
                wmlp.gravity = Gravity.TOP | Gravity.RIGHT;
                wmlp.x = 100;   //x position
                wmlp.y = 100;   //y position
                alert.show();
                alert.getWindow().setLayout(350, 900);
             }
        });btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                for(int a=2017;a>=1905;a--)
                {
                    list.add(String.valueOf(a));
                }
                final CharSequence[] items =
                        {"ALL","Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary"
                ,"Drama","Family","Fantasy","History","Horror","Music","Mystery","Romance","Science Fiction","TV Movie","Thriller","War","Western"};

                final Integer [] item={28,12,16,35,80,99,18,10751,14,36,27,10402,9648,10749,878,10770,53,10752,37};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);


                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    btn_category.setText(items[which]);
                        if(which!=0)
                        {
                            category=item[which-1];
                            Log.d("file", "onCreate() Restoring previous state");

                        }

                        else
                        {
                            category=0;

                        }
                        Get_Results();

                    }
                });

                AlertDialog alert = builder.create();
                WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
                wmlp.gravity = Gravity.TOP | Gravity.LEFT;
                wmlp.x = 150;   //x position
                wmlp.y = 100;   //y position
                alert.show();
                alert.getWindow().setLayout(350, 900);
             }
        });

    }

    public void Get_Results() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        rx.Observable<Discover_Pojo> result;
        if (Integer.valueOf(btn_yearone.getText().toString()) >= (Integer.parseInt(btn_yeartwo.getText().toString()))) {
            btn_yearone.setText(btn_yeartwo.getText());
        }
        if (Integer.valueOf(btn_yeartwo.getText().toString()) <= (Integer.parseInt(btn_yearone.getText().toString()))) {
            btn_yeartwo.setText(btn_yearone.getText());
        }
        if (category == 0) {
            result = apiInterface.getdiscover(Global.key, "en-US", btn_sorting.getText().toString(), false, false,
                    Integer.valueOf(btn_yearone.getText().toString()),
                    Integer.valueOf(btn_yeartwo.getText().toString()), sorting);
        } else {
            result = apiInterface.getdiscover_genre(Global.key, "en-US", btn_sorting.getText().toString(), false, false,
                    Integer.valueOf(btn_yearone.getText().toString()),
                    Integer.valueOf(btn_yeartwo.getText().toString()), category, sorting);
            Log.d("cat",String.valueOf(category));

        }
result.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Discover_Pojo>() {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNext(Discover_Pojo discover_pojo) {
        arraylist = discover_pojo.getResults();
        list = new ArrayList<>();
        list = Arrays.asList(arraylist);
        arraylist = list.toArray(arraylist);
        adapter = new Discover_Movie_Adapter(getContext(), list, new Listener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), Activity_Movie_Detail.class);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("MOVIE_ID", Integer.parseInt(list.get(position).getId()));
                editor.commit();
                startActivity(intent);
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
});

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favourites, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item=menu.findItem(R.id.action_settings);
        item.setVisible(false);

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


                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}

