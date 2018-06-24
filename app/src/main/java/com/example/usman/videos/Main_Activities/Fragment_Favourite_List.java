package com.example.usman.videos.Main_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usman.videos.ADAPTERS.Favourite_List_Adapter;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.R;
import com.example.usman.videos.Realm_Objects.Realm_Favourite_List;
import com.example.usman.videos.UTILITIES.Session_Management;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by USMAN on 23/06/2018.
 */
public class Fragment_Favourite_List extends Fragment {
    RecyclerView recyclerView;
    Session_Management session_management;
    Realm realm;
    List<Realm_Favourite_List> list;
    Favourite_List_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler_view_simple, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_fragment_cast);
        session_management = new Session_Management();
        realm.init(getContext());

        realm = Realm.getDefaultInstance();
        Update_Videos_Found();
        return v;
    }

    private void Update_Videos_Found() {
        final RealmResults<Realm_Favourite_List> Allreports = realm.where(Realm_Favourite_List.class).findAll().sort("app_id", Sort.DESCENDING);
        adapter=new Favourite_List_Adapter(getActivity(),Allreports, new Listener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent=new Intent(getActivity().getBaseContext(),Activity_Movie_Detail.class);
                if(Allreports.get(position).getTv_show_id()==0)
                {
                    session_management.movie_id(Allreports.get(position).getMovie_id(),getContext());
                    intent=new Intent(getActivity().getBaseContext(),Activity_Movie_Detail.class);
                }
                else
                {
                    session_management.tv_show_id(Allreports.get(position).getTv_show_id(),getContext());
                    intent=new Intent(getActivity().getBaseContext(),Activity_Tv_Shows_Detail.class);


                }
                getActivity().startActivity(intent);
            }

        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);

    }


}

