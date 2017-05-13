package com.example.usman.videos.Main_Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.usman.videos.ADAPTERS.Pager_Adapter;
import com.example.usman.videos.Cast_Fragments.Cast_Info;
import com.example.usman.videos.Cast_Fragments.Cast_Movies_Credits;
import com.example.usman.videos.Cast_Fragments.Cast_Tv_Shows_Credits;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.Tabs_Movie_Detail_Activity.Fragment_Info;
import com.example.usman.videos.Tabs_Movie_Detail_Activity.Fragment_Reviews;
import com.example.usman.videos.POJO.Person;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by usman on 4/22/2017.
 */

public class Activity_Cast extends AppCompatActivity implements TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener{
    int castid;
    TabHost tabHost;
    ViewPager viewPager;
    Pager_Adapter myFragmentPagerAdapter;
    ImageView imageView,imageView1;
    TextView name;
    int cast_id;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView=(ImageView)findViewById(R.id.img_view_cast_activity);
        name=(TextView)findViewById(R.id.tv_cast_activity_title);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        cast_id =sharedPreferences.getInt("cast_id",0);
        Initialize_Viewpager();
        initializeTabHost();
        Get_Data();

    }

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
    private void Initialize_Viewpager() {
        List<Fragment> fragments=new Vector<>();
        Bundle bundle=new Bundle();
        bundle.putInt("id",castid);
        Cast_Movies_Credits cast_movies_credits=new Cast_Movies_Credits();
        cast_movies_credits.setArguments(bundle);
        Fragment_Reviews fragment_reviews=new Fragment_Reviews();
        Fragment_Info fragment_info=new Fragment_Info();
        fragment_info.setArguments(bundle);
        fragment_reviews.setArguments(bundle);
        fragments.add(new Cast_Info());
        fragments.add(new Cast_Movies_Credits());
        fragments.add(new Cast_Tv_Shows_Credits());
        this.myFragmentPagerAdapter = new Pager_Adapter(getSupportFragmentManager(), fragments);
        this.viewPager = (ViewPager) super.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myFragmentPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);


        onRestart();


    }
    private void initializeTabHost() {

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        String[] data = {"INFO","MOVIES","TV SHOWS"};

        for (int i = 0; i <= 2; i++) {

            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(data[i]);
            tabSpec.setIndicator(data[i]);
            tabSpec.setContent(new Activity_Cast.FakeContent(this));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
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
    public void Get_Data()
    {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<Person> callresult=apiInterface.getperson(cast_id, Global.key);
        callresult.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                name.setText(response.body().getName());
                Picasso.with(Activity_Cast.this).load("http://image.tmdb.org/t/p/w500"+response.body().getProfile_path()).fit().into(imageView);

            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_activity, menu);
        menu.getItem(1).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        switch (item.getItemId()) {

            case R.id.action_go_to_home:
                Intent intent = new Intent(getApplicationContext(), Activity_Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
