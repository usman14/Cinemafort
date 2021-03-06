package com.cinemafortlatest.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.Similiar_Movie_Results;
import com.cinemafortlatest.usman.videos.POJO.Tv_Shows_Similiar_Results;
import com.cinemafortlatest.usman.videos.R;
import com.cinemafortlatest.usman.videos.UTILITIES.Utilites;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by usman on 5/3/2017.
 */

public class Tv_Shows_Similiar_Shows_Adapter  extends RecyclerView.Adapter<Tv_Shows_Similiar_Shows_Adapter.Similiar_movie> {

    List<Tv_Shows_Similiar_Results> list;
    Listener listener;
    Context context;

    public Tv_Shows_Similiar_Shows_Adapter(Context context,List<Tv_Shows_Similiar_Results> list,Listener listener)
    {
        this.list=list;
        this.listener=listener;
        this.context=context;

    }

    @Override
    public Tv_Shows_Similiar_Shows_Adapter.Similiar_movie onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_fragment_info_similiar_movies,parent,false);
        return new Tv_Shows_Similiar_Shows_Adapter.Similiar_movie(v);
    }

    @Override
    public void onBindViewHolder(Tv_Shows_Similiar_Shows_Adapter.Similiar_movie holder, final int position) {
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+list.get(position).getBackdrop_path()).fit().into(holder.imageView);
        holder.title.setText(list.get(position).getOriginal_name());
        holder.year.setText(Utilites.year(list.get(position).getFirst_air_date()));
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

    public class Similiar_movie extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,year;
        RelativeLayout relativeLayout;

        public Similiar_movie(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.img_view_fragment_info_similiar_movies);
            title=(TextView)itemView.findViewById(R.id.tv_fragment_info_similiar_movies_title);
            year=(TextView)itemView.findViewById(R.id.tv_fragment_info_similiar_movies_year);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.rl_fragment_info_similiar_movies);
        }
    }
}

