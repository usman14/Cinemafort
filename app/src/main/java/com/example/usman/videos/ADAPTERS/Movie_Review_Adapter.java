package com.example.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Review_Results;
import com.example.usman.videos.R;

import java.util.List;

/**
 * Created by usman on 4/21/2017.
 */

public class Movie_Review_Adapter extends RecyclerView.Adapter<Movie_Review_Adapter.Review> {

    List<Review_Results> list;
    Context context;
    Listener listener;

    public Movie_Review_Adapter(Context context, List<Review_Results> list, Listener listener)
    {
        this.list=list;
        this.context=context;
        this.listener=listener;

    }

    @Override
    public Movie_Review_Adapter.Review onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_fragment_review,parent,false);
        return new Review(v);
    }

    @Override
    public void onBindViewHolder(Movie_Review_Adapter.Review holder, int position) {
    holder.author.setText(list.get(position).getAuthor());
        holder.review.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Review extends RecyclerView.ViewHolder {

        TextView author,review;
        public Review(View itemView) {
            super(itemView);
            author=(TextView)itemView.findViewById(R.id.tv_fragment_review_author_name);
            review=(TextView)itemView.findViewById(R.id.tv_fragment_review_review);
        }
    }
}
