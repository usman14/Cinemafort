package com.example.usman.videos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import com.example.usman.videos.ADAPTERS.Pager_Adapter;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.MAIN_ACTIVITY_TABS.Tab1Fragment;
import com.example.usman.videos.MAIN_ACTIVITY_TABS.Tab2Fragment;
import com.example.usman.videos.MAIN_ACTIVITY_TABS.Tab3Fragment;
import com.example.usman.videos.MAIN_ACTIVITY_TABS.Tab4Fragment;
import com.example.usman.videos.MAIN_ACTIVITY_TABS.Tab5Fragment;
import com.example.usman.videos.Search_Fragments.Actors;
import com.example.usman.videos.Search_Fragments.Movies;
import com.example.usman.videos.Search_Fragments.Tv_Shows;

import java.util.List;
import java.util.Vector;

/**
 * Created by usman on 4/24/2017.
 */

public class Activity_Search extends AppCompatActivity implements TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener {
    TabHost tabHost;
    ViewPager viewPager;
    Pager_Adapter myFragmentPagerAdapter;
    Listener listener;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Initialize_Viewpager();
        initializeTabHost();

    }
    private void Initialize_Viewpager() {
        List<Fragment> fragments=new Vector<>();
        fragments.add(new Actors());
        fragments.add(new Movies());
        fragments.add(new Tv_Shows());
        // fragments.add(new Tab3Fragment());
        this.myFragmentPagerAdapter = new Pager_Adapter(
                getSupportFragmentManager(), fragments);
        this.viewPager = (ViewPager) super.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myFragmentPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);

        onRestart();


    }
    private void initializeTabHost() {

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        String[] data = {"Actors","Movies","TV Shows"};

        for (int i = 0; i <= 2; i++) {

            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(data[i]);
            tabSpec.setIndicator(data[i]);
            tabSpec.setContent(new Activity_Search.FakeContent(this));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
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

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.main, menu);

       final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                //UserFeedback.show( "SearchOnQueryTextSubmit: " + query);
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        return true;
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
}
