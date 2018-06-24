package com.cinemafortlatest.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.Seasons;
import com.cinemafortlatest.usman.videos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by usman on 5/3/2017.
 */

public class Tv_Shows_Episode_Adapter extends RecyclerView.Adapter<Tv_Shows_Episode_Adapter.Episod> {

    List<Seasons> list;
    Listener listener;
    Context context;
    public Tv_Shows_Episode_Adapter(Context context, List<Seasons> list, Listener listener)
    {
        this.list=list;
        this.context=context;
        this.listener=listener;
    }
    @Override
    public Tv_Shows_Episode_Adapter.Episod onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_tv_shows_episodes,parent,false);
        return new Tv_Shows_Episode_Adapter.Episod(v);
    }

    @Override
    public void onBindViewHolder(Tv_Shows_Episode_Adapter.Episod holder, int position) {
        holder.episodes.setText("No of Episodes : "+list.get(position).getEpisode_count());
        holder.season.setText("Season Number : "+list.get(position).getSeason_number());
        holder.date.setText("First Air Date :"+list.get(position).getAir_date());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+list.get(position).getPoster_path()).fit().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Episod extends RecyclerView.ViewHolder {
        TextView episodes,season,date;
        ImageView imageView;
        public Episod(View itemView) {
            super(itemView);

            season=(TextView)itemView.findViewById(R.id.tv_rv_tv_shows_episodes_season_number);
            episodes=(TextView)itemView.findViewById(R.id.tv_rv_tv_shows_episodes_number_of_episodes);
            date=(TextView)itemView.findViewById(R.id.tv_rv_tv_shows_episodes_air_date);
            imageView=(ImageView)itemView.findViewById(R.id.img_view_rv_tv_shows_episodes_air_date);
        }
    }
}
