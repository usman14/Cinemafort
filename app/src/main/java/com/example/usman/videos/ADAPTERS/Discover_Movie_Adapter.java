package com.example.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Discover_Results;
import com.example.usman.videos.POJO.Search_Movie_Results;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.Utilites;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Created by usman on 4/29/2017.
 */

public class Discover_Movie_Adapter  extends RecyclerView.Adapter<Discover_Movie_Adapter.Search> {

    List<Discover_Results> list;
    Context context;
    Listener listener;

    public  Discover_Movie_Adapter(Context context, List<Discover_Results> list, Listener listener)
    {
        this.context=context;
        this.list=list;
        this.listener=listener;
    }
    @Override
    public Discover_Movie_Adapter.Search onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.rv_item_main_activity_movie,parent,false);
        return new Discover_Movie_Adapter.Search(v);    }

    @Override
    public void onBindViewHolder(Discover_Movie_Adapter.Search holder, final int position) {

        if(list.get(position).getBackdrop_path()!=null)
        {
            Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+list.get(position).getBackdrop_path()).fit().into(holder.imageView);

        }
        else
        {
            holder.imageView.setBackgroundResource(R.drawable.movie);


        }
        holder.name.setText(list.get(position).getOriginal_title());
        Utilites utilites=new Utilites();
        String [] genre=list.get(position).getGenre_ids();
        int size=genre.length;
        if(size!=0)
        {
            StringBuilder stringBuilder = new StringBuilder();
            final CharSequence[] items =
                    {"ALL","Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary"
                            ,"Drama","Family","Fantasy","History","Horror","Music","Mystery","Romance","Science Fiction","TV Movie","Thriller","War","Western"};

            final Integer [] item={28,12,16,35,80,99,18,10751,14,36,27,10402,9648,10749,878,10770,53,10752,37};


            for(int as=0;as<size;as++)
            {
                //stringBuilder.append(utilites.Genre_name(Integer.valueOf(genre[as]))+",");
                stringBuilder.append(items[as+1]+",");
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));

            holder.genre.setText(stringBuilder.toString());
            holder.date.setText(utilites.year(list.get(position).getRelease_date()));
            holder.name.setText(list.get(position).getTitle());
            holder.rating.setText(list.get(position).getVote_average());
        }
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


    public class Search extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date,name,genre,rating;
        ConstraintLayout relativeLayout;
        public Search(View v) {
            super(v);
            imageView=(ImageView)v.findViewById(R.id.img_view_movie_adapter);
            date=(TextView)v.findViewById(R.id.tv_movie_adapter_year);
            name=(TextView)v.findViewById(R.id.tv_movie_adapter_name);
            genre=(TextView)v.findViewById(R.id.tv_movie_adapter_genre);
            rating=(TextView)v.findViewById(R.id.tv_movie_adapter_name_rating);
            relativeLayout=(ConstraintLayout) v.findViewById(R.id.rl_main_activity_movie);

        }
    }
}