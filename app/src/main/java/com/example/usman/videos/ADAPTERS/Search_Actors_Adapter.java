package com.example.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Search_Actor_Results;
import com.example.usman.videos.POJO.Search_Actors;
import com.example.usman.videos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by usman on 4/24/2017.
 */

public class Search_Actors_Adapter extends RecyclerView.Adapter<Search_Actors_Adapter.Search> {

    List<Search_Actor_Results> list;
    Context context;
    Listener listener;

    public  Search_Actors_Adapter(Context context,List<Search_Actor_Results> list,Listener listener)
    {
        this.context=context;
        this.list=list;
        this.listener=listener;
    }
    @Override
    public Search_Actors_Adapter.Search onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.rv_item_fragment_info_cast,parent,false);
        return new Search(v);
    }

    @Override
    public void onBindViewHolder(Search_Actors_Adapter.Search holder, int position) {
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+list.get(position).getProfile_path()).fit().into(holder.imageView);
        holder.textView.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Search extends RecyclerView.ViewHolder {

ImageView imageView;
        TextView textView;
        public Search(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.img_view_fragment_info_cast);
            textView=(TextView)itemView.findViewById(R.id.tv_fragment_info_cast_name);

        }
    }
}
