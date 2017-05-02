package com.example.usman.videos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usman.videos.ADAPTERS.Discover_Movie_Adapter;
import com.example.usman.videos.ADAPTERS.Search_Movie_Adapter;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Discover_Pojo;
import com.example.usman.videos.POJO.Discover_Results;
import com.example.usman.videos.POJO.Search_Movie_Results;
import com.example.usman.videos.POJO.Search_Movies;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;
import com.example.usman.videos.UTILITIES.Utilites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by usman on 4/29/2017.
 */

public class Discover extends AppCompatActivity {
    TextView tv_yearone, tv_yeartwo, tv_category,tv_sorting;
    RecyclerView rv;
    List<Discover_Results> list;
    Discover_Results[] arraylist;
    Discover_Movie_Adapter adapter;
    int category;
    String sorting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_discover);
        Get_Widgets();
       Get_Results();
    }

    private void Get_Widgets() {
        tv_yearone =(TextView)findViewById(R.id.tv_fragment_discover_year_one);
        tv_yeartwo =(TextView)findViewById(R.id.tv_fragment_discover_year_two);
        tv_category =(TextView)findViewById(R.id.tv_fragment_discover_category);
        rv=(RecyclerView)findViewById(R.id.rv_fragment_discover);
        tv_sorting=(TextView)findViewById(R.id.tv_fragment_discover_sorting);
        sorting="popularity.desc";
        tv_sorting.setText("Sort");
        tv_yearone.setText("2017");
        tv_yeartwo.setText("2017");
        tv_category.setText("ALL");

        tv_yearone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                for(int a=2017;a>=1905;a--)
                {
                    list.add(String.valueOf(a));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Discover.this);
                final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);


                builder.setItems(cs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    tv_yearone.setText(cs[which]);
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

        tv_yeartwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                for(int a=2017;a>=1905;a--)
                {
                    list.add(String.valueOf(a));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Discover.this);
                final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);


                builder.setItems(cs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    tv_yeartwo.setText(cs[which]);
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

        tv_sorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();

                final CharSequence[] items =
                        {"Release Date","Popularity", "Revenue"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Discover.this);
                final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);


                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    tv_sorting.setText(items[which]);
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
        });tv_category.setOnClickListener(new View.OnClickListener() {
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

                AlertDialog.Builder builder = new AlertDialog.Builder(Discover.this);
                final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);


                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    tv_category.setText(items[which]);
                        if(which!=0)
                        {
                            category=item[which-1];
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

    public void Get_Results()
    {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Boolean adult=false;
        Boolean video=false;
        Call<Discover_Pojo> result;
        if(Integer.valueOf(tv_yearone.getText().toString())>=(Integer.parseInt(tv_yeartwo.getText().toString())))
        {
            tv_yearone.setText(tv_yeartwo.getText());
        }
        if(Integer.valueOf(tv_yeartwo.getText().toString())<=(Integer.parseInt(tv_yearone.getText().toString())))
        {
            tv_yeartwo.setText(tv_yearone.getText());
        }
        if(category==0)
        {
      result=apiInterface.getdiscover(Global.key,"en-US",tv_sorting.getText().toString(),false,false,
                    Integer.valueOf(tv_yearone.getText().toString()),
                    Integer.valueOf(tv_yeartwo.getText().toString()),sorting);
        }
        else
        { result=apiInterface.getdiscover_genre(Global.key,"en-US",tv_sorting.getText().toString(),false,false,
                Integer.valueOf(tv_yearone.getText().toString()),
                Integer.valueOf(tv_yeartwo.getText().toString()),category,sorting);

        }

        result.enqueue(new Callback<Discover_Pojo>() {
            @Override
            public void onResponse(Call<Discover_Pojo> call, Response<Discover_Pojo> response) {
                int as=response.code();
                arraylist=response.body().getResults();
                list = new ArrayList<>();
                list= Arrays.asList(arraylist);
                arraylist= list.toArray(arraylist);
                adapter=new Discover_Movie_Adapter(Discover.this, list, new Listener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent=new Intent(Discover.this,Activity_Movie_Detail.class);
                        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(Discover.this);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putInt("movie_id",Integer.parseInt(list.get(position).getId()));
                        editor.commit();
                        //intent.putExtra("id",list.get(position).getId());
                        startActivity(intent);
                    }
                });

                rv.setLayoutManager(new LinearLayoutManager(Discover.this));
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<Discover_Pojo> call, Throwable t) {
                Toast.makeText(Discover.this,"s",Toast.LENGTH_LONG).show();

            }
        });

            }
}

