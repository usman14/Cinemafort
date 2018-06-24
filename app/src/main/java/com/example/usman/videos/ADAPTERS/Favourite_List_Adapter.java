package com.example.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Movie;
import com.example.usman.videos.R;
import com.example.usman.videos.Realm_Objects.Realm_Favourite_List;
import com.example.usman.videos.UTILITIES.Utilites;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by usman on 4/18/2017.
 */

public class Favourite_List_Adapter extends RecyclerView.Adapter <Favourite_List_Adapter.Movie_holder>{

    Context context;
    List<Realm_Favourite_List> list;
    Listener listener;
    public Favourite_List_Adapter(Context context, List<Realm_Favourite_List> list, Listener listener)
    {
        this.context=context;
        this.list=list;
        this.listener=listener;

    }
    @Override
    public Movie_holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_main_activity_movie,parent,false);
       // View itemView = mLayoutInflater.inflate(R.layout.view_item, parent, false);
        // work here if you need to control height of your items
        // keep in mind that parent is RecyclerView in this case
        //int height = parent.getMeasuredHeight();
        //int width = parent.getMeasuredWidth();
        //v.setLayoutParams(new RecyclerView.LayoutParams(2*width, height));

        return new Movie_holder(v);

    }

    @Override
    public void onBindViewHolder(Movie_holder holder, final int position) {
        Realm_Favourite_List movie=list.get(position);
        Utilites utilites=new Utilites();

      //  holder.date.setText(utilites.year(list.get(position).getYear()));
        holder.name.setText(movie.getTitle());

        Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+movie.getImage_path()).fit().into(holder.imageView);
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
