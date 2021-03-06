package com.cinemafortlatest.usman.videos.Main_Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import com.cinemafortlatest.usman.videos.ADAPTERS.Pager_Adapter;
import com.cinemafortlatest.usman.videos.R;
import com.cinemafortlatest.usman.videos.Tabs_Tv_Shows_Fragment.Fragment_On_The_Air;
import com.cinemafortlatest.usman.videos.Tabs_Tv_Shows_Fragment.Fragment_Airing_Today;
import com.cinemafortlatest.usman.videos.Tabs_Tv_Shows_Fragment.Fragment_Popular;
import com.cinemafortlatest.usman.videos.Tabs_Tv_Shows_Fragment.Fragment_Top_Rated;

import java.util.List;
import java.util.Vector;

/**
 * Created by usman on 5/3/2017.
 */

public class Fragment_TV_Shows extends Fragment

        implements TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener{

    TabHost tabHost;
    ViewPager viewPager;
    View v;
    Pager_Adapter myFragmentPagerAdapter;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v=  inflater.inflate(R.layout.fragment_tv_shows,null);

        Initialize_Viewpager(v);
        initializeTabHost(v);

        return v;}

    private void Initialize_Viewpager(View v) {
        List<Fragment> fragments=new Vector<>();
        fragments.add(new Fragment_Airing_Today());
        fragments.add(new Fragment_Top_Rated());
        fragments.add(new Fragment_Popular());
        fragments.add(new Fragment_On_The_Air());
        this.myFragmentPagerAdapter = new Pager_Adapter(
                getChildFragmentManager(), fragments);
        this.viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myFragmentPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);


    }
    private void initializeTabHost(View v) {

        tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup();
        String[] data = {"AIRING TODAY","ON THE AIR","POPULAR","TOP RATED"};

        for (int i = 0; i <= 3; i++) {

            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(data[i]);
            tabSpec.setIndicator(data[i]);
            tabSpec.setContent(new Fragment_TV_Shows.FakeContent(getContext()));
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
        HorizontalScrollView hScrollView = (HorizontalScrollView) v.findViewById(R.id.hScrollView_tvshows);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()
                - (hScrollView.getWidth() - tabView.getWidth()) / 2;
        hScrollView.smoothScrollTo(scrollPos, 0);

    }
}