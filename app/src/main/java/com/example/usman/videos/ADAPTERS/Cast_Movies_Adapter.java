package com.example.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Cast_1;
import com.example.usman.videos.POJO.Realm_Session_Id;
import com.example.usman.videos.R;
import com.example.usman.videos.Realm_Objects.Realm_Favourite_List;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by usman on 4/24/2017.
 */

public class Cast_Movies_Adapter extends RecyclerView.Adapter<Cast_Movies_Adapter.Movies> {

    List<Cast_1> list;
    Listener listener;
    Context context;

    public Cast_Movies_Adapter(Context context, List<Cast_1> list, Listener listener)
    {
        this.context=context;
        this.list=list;
        this.listener=listener;
    }
    @Override
    public Cast_Movies_Adapter.Movies onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_movie_gridview,parent,false);
        return new Movies(v);
    }

    @Override
    public void onBindViewHolder(Cast_Movies_Adapter.Movies holder, final int position) {
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+list.get(position).getPoster_path()).fit().into(holder.imageView);
        holder.tv.setText(list.get(position).getOriginal_title());
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

    public class Movies extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tv;
        ConstraintLayout relativeLayout;
        public Movies(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.img_view_movie_adapter_gridview);
            tv=(TextView)itemView.findViewById(R.id.tv_movie_adapter_name_gridview);
            relativeLayout=(ConstraintLayout) itemView.findViewById(R.id.rl_rv_item_movie_gridview);
        }
    }
}
