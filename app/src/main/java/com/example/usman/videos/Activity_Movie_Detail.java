package com.example.usman.videos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.usman.videos.ADAPTERS.Pager_Adapter;
import com.example.usman.videos.Cast_Fragments.Cast_Info;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.MOVIE_DETAILS_ACTIVITY_TABS.Fragment_Info;
import com.example.usman.videos.MOVIE_DETAILS_ACTIVITY_TABS.Fragment_Reviews;
import com.example.usman.videos.POJO.MovieMovie;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;
import com.example.usman.videos.UTILITIES.Utilites;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by usman on 4/18/2017.
 */

public class Activity_Movie_Detail extends AppCompatActivity implements TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener{
    ImageView img_view_movie_detail,img_view_movie_detail_one;
    TextView year,duration,title,category;
    TabHost tabHost;
    ViewPager viewPager;
    Pager_Adapter myFragmentPagerAdapter;
    SharedPreferences sharedPreferences;
    Context context;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);

    }




    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int a=tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(a);
        HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.hScrollView);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()
                - (hScrollView.getWidth() - tabView.getWidth()) / 2;
        hScrollView.smoothScrollTo(scrollPos, 0);

    }

    class FakeContent implements TabHost.TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }
    int value;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Get_Widgets();
        Intent intent = getIntent();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        value=sharedPreferences.getInt("movie_id",0);
             //value=intent.getIntExtra("id",0);
            String id=String.valueOf(value);
        //Toast.makeText(Activity_Movie_Detail.this,id,Toast.LENGTH_SHORT).show();
        Initialize_Viewpager();
        initializeTabHost();
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieMovie> call = apiService.getMovieDetails(value, Global.key);
        call.enqueue(new Callback<MovieMovie>() {
            @Override
            public void onResponse(Call<MovieMovie> call, Response<MovieMovie> response) {
                int statusCode = response.code();
                Picasso.with(Activity_Movie_Detail.this).load("http://image.tmdb.org/t/p/w500"+response.body().getBackdropPath()).fit().into(img_view_movie_detail);
                Picasso.with(Activity_Movie_Detail.this).load("http://image.tmdb.org/t/p/w500"+response.body().getPosterPath()).fit().into(img_view_movie_detail_one);
                year.setText(Utilites.year(response.body().getReleaseDate()));
                duration.setText(String.valueOf(response.body().getRuntime())+" minutes");
                title.setText(response.body().getTitle());
                StringBuilder stringBuilder=new StringBuilder();
                for(int a=0;a<response.body().getGenres().size();a++)
                {
                    stringBuilder.append(response.body().getGenres().get(a).getName().toString()+",");
                }
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                category.setText(stringBuilder.toString());

            }

            @Override
            public void onFailure(Call<MovieMovie> call, Throwable t) {

            }
        });
        }
       public void Get_Widgets()
        {
            img_view_movie_detail=(ImageView)findViewById(R.id.img_view_movie_detail);
            img_view_movie_detail_one=(ImageView)findViewById(R.id.img_view_movie_detail_1);
            year=(TextView)findViewById(R.id.tv_movie_detail_year);
            duration=(TextView)findViewById(R.id.tv_movie_detail_duration);
            title=(TextView)findViewById(R.id.tv_movie_detail_title);
            category=(TextView)findViewById(R.id.tv_movie_detail_category);

        }
    private void Initialize_Viewpager() {
        List<Fragment> fragments=new Vector<>();
        Bundle bundle=new Bundle();
        bundle.putInt("id",value);
        Cast_Info fragment_cast=new Cast_Info();
        Fragment_Reviews fragment_reviews=new Fragment_Reviews();
        Fragment_Info fragment_info=new Fragment_Info();
        fragment_cast.setArguments(bundle);
        fragment_info.setArguments(bundle);
        fragment_reviews.setArguments(bundle);
        fragments.add(new Fragment_Info());
        fragments.add(new Cast_Info());
        fragments.add(new Fragment_Reviews());
        this.myFragmentPagerAdapter = new Pager_Adapter(getSupportFragmentManager(), fragments);
        this.viewPager = (ViewPager) super.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myFragmentPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);


        onRestart();


    }
    private void initializeTabHost() {

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        String[] data = {"Info","Cast","Reviews"};

        for (int i = 0; i <= 2; i++) {

            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(data[i]);
            tabSpec.setIndicator(data[i]);
            tabSpec.setContent(new Activity_Movie_Detail.FakeContent(this));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
    }

}


