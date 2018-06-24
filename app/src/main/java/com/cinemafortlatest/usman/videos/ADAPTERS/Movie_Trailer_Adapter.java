package com.cinemafortlatest.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.Results;
import com.cinemafortlatest.usman.videos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by usman on 4/20/2017.
 */

public class Movie_Trailer_Adapter extends RecyclerView.Adapter <Movie_Trailer_Adapter.Trailer>{

    List<Results> list;
    Context context;
    Listener listener;
    public Movie_Trailer_Adapter(Context context, List<Results> list,Listener listener)
    {
        this.list=list;
        this.context=context;
        this.listener=listener;
    }

    @Override
    public Trailer onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_itemfragment_info_trailer,parent,false);
        return new Trailer(v);
    }

    @Override
    public void onBindViewHolder(Trailer holder, final int position) {
        //Picasso.with(context).load("http://img.youtube.com/vi/"+list.get(position).getKey()).fit().into(holder.thumbnail);
        Picasso.with(context).load("http://i1.ytimg.com/vi/" + list.get(position+1).getKey()+ "/0.jpg").fit().into(holder.thumbnail);

        holder.title.setText(list.get(position+1).getName());
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,position);
            }
        });
    }



    @Override
    public int getItemCount() {
        int size=list.size()-1;
        return size;
    }

    public class Trailer extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView title;
        public Trailer(View itemView) {
            super(itemView);

            thumbnail=(ImageView)itemView.findViewById(R.id.img_view_fragment_info_trailer) ;
            title=(TextView)itemView.findViewById(R.id.tv_fragment_info_trailer);

        }
    }
}
