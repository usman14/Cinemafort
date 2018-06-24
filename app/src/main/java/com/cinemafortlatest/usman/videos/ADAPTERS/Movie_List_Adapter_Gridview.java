package com.cinemafortlatest.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.Movie;
import com.cinemafortlatest.usman.videos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by usman on 4/18/2017.
 */

public class Movie_List_Adapter_Gridview extends RecyclerView.Adapter <Movie_List_Adapter_Gridview.Movie_holder>{

    Context context;
    List<Movie> list;
    Listener listener;
    public Movie_List_Adapter_Gridview(Context context, List<Movie> list, Listener listener)
    {
        this.context=context;
        this.list=list;
        this.listener=listener;

    }
    @Override
    public Movie_holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_movie_gridview,parent,false);

        return new Movie_holder(v);
    }

    @Override
    public void onBindViewHolder(Movie_holder holder, final int position) {
        Movie movie=list.get(position);
        holder.name.setText(movie.getTitle());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w154"+movie.getPosterPath()).fit().into(holder.imageView);
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
