package com.example.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Movie;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.Utilites;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by usman on 4/18/2017.
 */

public class Movie_List_Adapter extends RecyclerView.Adapter <Movie_List_Adapter.Movie_holder>{

    Context context;
    List<Movie> list;
    Listener listener;
    public Movie_List_Adapter(Context context, List<Movie> list, Listener listener)
    {
        this.context=context;
        this.list=list;
        this.listener=listener;

    }
    @Override
    public Movie_holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_main_activity_movie,parent,false);

        return new Movie_holder(v);
    }

    @Override
    public void onBindViewHolder(Movie_holder holder, final int position) {
        Movie movie=list.get(position);
        Utilites utilites=new Utilites();

        holder.date.setText(utilites.year(list.get(position).getReleaseDate()));
        holder.name.setText(movie.getTitle());
        holder.rating.setText(movie.getVoteAverage().toString());
        int size=movie.getGenreIds().size();
        StringBuilder stringBuilder = new StringBuilder();
        for(int as=0;as<size;as++)
        {
            if(utilites.Genre_name(movie.getGenreIds().get(as))!="null")
            {
                String asdf=utilites.Genre_name(movie.getGenreIds().get(as));
                if(asdf!=null)
                {
                    stringBuilder.append(utilites.Genre_name(movie.getGenreIds().get(as))+",");

                }

            }

        }

            //stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            holder.genre.setText(stringBuilder.toString());

        Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+movie.getPosterPath()).fit().into(holder.imageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
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
        TextView date,name,genre,rating;
        RelativeLayout relativeLayout;

        public Movie_holder(View v) {
            super(v);
            imageView=(ImageView)v.findViewById(R.id.img_view_movie_adapter);
            date=(TextView)v.findViewById(R.id.tv_movie_adapter_year);
            name=(TextView)v.findViewById(R.id.tv_movie_adapter_name);
            genre=(TextView)v.findViewById(R.id.tv_movie_adapter_genre);
            relativeLayout=(RelativeLayout)v.findViewById(R.id.rl_main_activity_movie);
            rating=(TextView)v.findViewById(R.id.tv_movie_adapter_name_rating);



        }
    }
}