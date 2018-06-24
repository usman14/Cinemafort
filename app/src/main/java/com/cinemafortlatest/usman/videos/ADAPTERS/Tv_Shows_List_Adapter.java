package com.cinemafortlatest.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.Movie;
import com.cinemafortlatest.usman.videos.POJO.Tv_Shows_Popular_Results;
import com.cinemafortlatest.usman.videos.R;
import com.cinemafortlatest.usman.videos.UTILITIES.Utilites;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Created by usman on 5/3/2017.
 */

public class Tv_Shows_List_Adapter extends RecyclerView.Adapter <Tv_Shows_List_Adapter.Movie_holder>{

    Context context;
    List<Tv_Shows_Popular_Results> list;
    Listener listener;
    public Tv_Shows_List_Adapter(Context context, List<Tv_Shows_Popular_Results> list, Listener listener)
    {
        this.context=context;
        this.list=list;
        this.listener=listener;

    }
    @Override
    public Tv_Shows_List_Adapter.Movie_holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_main_activity_movie,parent,false);

        return new Tv_Shows_List_Adapter.Movie_holder(v);
    }

    @Override
    public void onBindViewHolder(Tv_Shows_List_Adapter.Movie_holder holder, final int position) {
        Tv_Shows_Popular_Results movie=list.get(position);
        Utilites utilites=new Utilites();

        holder.date.setText(utilites.year(list.get(position).getFirst_air_date()));
        holder.name.setText(movie.getOriginal_name());
        holder.rating.setText(movie.getVote_average());
        String [] array=movie.getGenre_ids();
        List<String> stringlist= Arrays.asList(array);
        int size=stringlist.size();

        StringBuilder stringBuilder = new StringBuilder();
        for(int as=0;as<size;as++)
        {

                String asdf=utilites.Genre_name(Integer.parseInt(stringlist.get(as)));
                if(asdf!=null)
                {
                    stringBuilder.append(utilites.Genre_name(Integer.parseInt(stringlist.get(as)))+",");

                }
        }
        String withoutcomma=stringBuilder.toString();
        if(withoutcomma.endsWith(",")) {

            withoutcomma= withoutcomma.substring(0, withoutcomma.length() - 1);
        }
        //stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        holder.genre.setText(withoutcomma);

        //stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));

        Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+movie.getBackdrop_path()).fit().into(holder.imageView);
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
        ConstraintLayout relativeLayout;

        public Movie_holder(View v) {
            super(v);
            imageView=(ImageView)v.findViewById(R.id.img_view_movie_adapter);
            date=(TextView)v.findViewById(R.id.tv_movie_adapter_year);
            name=(TextView)v.findViewById(R.id.tv_movie_adapter_name);
            genre=(TextView)v.findViewById(R.id.tv_movie_adapter_genre);
            relativeLayout=(ConstraintLayout) v.findViewById(R.id.rl_main_activity_movie);
            rating=(TextView)v.findViewById(R.id.tv_movie_adapter_name_rating);



        }
    }
}