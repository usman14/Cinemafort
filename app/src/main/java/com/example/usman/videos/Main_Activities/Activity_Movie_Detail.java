package com.example.usman.videos.Main_Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.usman.videos.ADAPTERS.Pager_Adapter;
import com.example.usman.videos.Cast_Fragments.Cast_Info;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.Tabs_Movie_Detail_Activity.Fragment_Cast;
import com.example.usman.videos.Tabs_Movie_Detail_Activity.Fragment_Info;
import com.example.usman.videos.Tabs_Movie_Detail_Activity.Fragment_Reviews;
import com.example.usman.videos.POJO.MovieMovie;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;
import com.example.usman.videos.UTILITIES.Utilites;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    ProgressDialog progressDialog;
    String homepage,title_name;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);

    } @Override
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
    int movie_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_const);
        Get_Widgets();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = ProgressDialog.show(Activity_Movie_Detail.this, "",
                "Loading", true);
        //progressDialog.setMessage("Downloading Videos");
        progressDialog.show();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        movie_id =sharedPreferences.getInt("MOVIE_ID",0);
        Initialize_Viewpager();
        initializeTabHost();
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        rx.Observable<MovieMovie> call = apiService.getMovieDetails(movie_id, Global.key);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieMovie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieMovie movieMovie) {
                        progressDialog.hide();

                        if(movieMovie.getHomepage()!=null)
                        {
                            homepage=movieMovie.getHomepage();

                        }
                        movieMovie.getHomepage();
                        title_name=movieMovie.getOriginalTitle();
                        Picasso.with(Activity_Movie_Detail.this).load("http://image.tmdb.org/t/p/w500"+movieMovie.getBackdropPath()).fit().into(img_view_movie_detail);
                        Picasso.with(Activity_Movie_Detail.this).load("http://image.tmdb.org/t/p/w500"+movieMovie.getPosterPath()).fit().into(img_view_movie_detail_one);
                        year.setText(Utilites.year(movieMovie.getReleaseDate()));
                        duration.setText(String.valueOf(movieMovie.getRuntime())+" minutes");
                        title.setText(movieMovie.getTitle());
                        StringBuilder stringBuilder=new StringBuilder();
                        for(int a=0;a<movieMovie.getGenres().size();a++)
                        {
                            stringBuilder.append(movieMovie.getGenres().get(a).getName()+",");
                        }
                        String withoutcomma=stringBuilder.toString();
                        if(withoutcomma.endsWith(",")) {

                            withoutcomma= withoutcomma.substring(0, withoutcomma.length() - 1);
                        }
                        category.setText(withoutcomma);
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
        fragments.add(new Fragment_Info());
        fragments.add(new Fragment_Cast());
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
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {

            final TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i)
                    .findViewById(android.R.id.title);

            // Look for the title view to ensure this is an indicator and not a divider.(I didn't know, it would return divider too, so I was getting an NPE)
            if (tv == null)
                continue;
            else
                tv.setTextColor(0xFFFFFFFF);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        switch (item.getItemId()) {

          case R.id.action_share:

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Check out " + title_name +", Link :" + homepage );
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
              return true;

            case R.id.action_go_to_home:
                Intent intent = new Intent(getApplicationContext(), Activity_Main.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
                 return true;
        }
        return super.onOptionsItemSelected(item);
    }

}



