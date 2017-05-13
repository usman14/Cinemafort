package com.example.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Movie;
import com.example.usman.videos.POJO.Tv_Shows_Popular_Results;
import com.example.usman.videos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by usman on 5/3/2017.
 */

public class Tv_Shows_List_Adapter_GridView extends RecyclerView.Adapter <Tv_Shows_List_Adapter_GridView.Movie_holder>{

    Context context;
    List<Tv_Shows_Popular_Results> list;
    Listener listener;
    public Tv_Shows_List_Adapter_GridView(Context context, List<Tv_Shows_Popular_Results> list, Listener listener)
    {
        this.context=context;
        this.list=list;
        this.listener=listener;

    }
    @Override
    public Tv_Shows_List_Adapter_GridView.Movie_holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_movie_gridview,parent,false);

        return new Tv_Shows_List_Adapter_GridView.Movie_holder(v);
    }

    @Override
    public void onBindViewHolder(Tv_Shows_List_Adapter_GridView.Movie_holder holder, final int position) {
        Tv_Shows_Popular_Results movie=list.get(position);
        holder.name.setText(movie.getOriginal_name());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w154"+movie.getBackdrop_path()).fit().into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class Movie_holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date,name,genre;

        public Movie_holder(View v) {
            super(v);
            imageView=(ImageView)v.findViewById(R.id.img_view_movie_adapter_gridview);
            name=(TextView)v.findViewById(R.id.tv_movie_adapter_name_gridview);



        }
    }
}

