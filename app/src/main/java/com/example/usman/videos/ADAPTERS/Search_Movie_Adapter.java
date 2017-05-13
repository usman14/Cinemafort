package com.example.usman.videos.ADAPTERS;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Search_Actors;
import com.example.usman.videos.POJO.Search_Movie_Results;
import com.example.usman.videos.POJO.Search_Movies;
import com.example.usman.videos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by usman on 4/24/2017.
 */

public class Search_Movie_Adapter extends RecyclerView.Adapter<Search_Movie_Adapter.Search> {

    List<Search_Movie_Results> list;
    Context context;
    Listener listener;

    public  Search_Movie_Adapter(Context context, List<Search_Movie_Results> list, Listener listener)
    {
        this.context=context;
        this.list=list;
        this.listener=listener;
    }
    @Override
    public Search_Movie_Adapter.Search onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.rv_item_movie_gridview,parent,false);
        return new Search_Movie_Adapter.Search(v);    }

    @Override
    public void onBindViewHolder(Search_Movie_Adapter.Search holder, final int position) {
        //Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+list.get(position).getBackdrop_path()).fit().into(holder.imageView);
        holder.textView.setText(list.get(position).getOriginal_title());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,position);
            }
        });
        if(list.get(position).getPoster_path()!=null)
        {
            Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+list.get(position).getPoster_path()).fit().into(holder.imageView);

        }
        else
        {
            holder.imageView.setBackgroundResource(R.drawable.people);


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Search extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        RelativeLayout relativeLayout;
        public Search(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.img_view_movie_adapter_gridview);
            textView=(TextView)itemView.findViewById(R.id.tv_movie_adapter_name_gridview);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.rl_rv_item_movie_gridview);
        }
    }
}
