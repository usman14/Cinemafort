package com.cinemafortlatest.usman.videos.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cinemafortlatest.usman.videos.INTERFACES.Listener;
import com.cinemafortlatest.usman.videos.POJO.Cast_one;
import com.cinemafortlatest.usman.videos.POJO.Popular_People_Results;
import com.cinemafortlatest.usman.videos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by usman on 5/3/2017.
 */

public class Popular_People_Adapter extends RecyclerView.Adapter<Popular_People_Adapter.Cast> {

        Context context;
        Listener listener;
        List<Popular_People_Results> list;

    public Popular_People_Adapter(Context context, List<Popular_People_Results> list, Listener listener)
        {
            this.context=context;
            this.listener=listener;
            this.list=list;
        }
        @Override
        public Popular_People_Adapter.Cast onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_fragment_info_cast,parent,false);
            return new Popular_People_Adapter.Cast(v);
        }

        @Override
        public void onBindViewHolder(Popular_People_Adapter.Cast holder, final int position) {
            //Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+list.get(position).getCast().get(position).getProfilePath()).fit().into(holder.imageView);
            //holder.name.setText(list.get(position).getCast().get(position).getName());
            //holder.rolename.setText("as " +list.get(position).getCast().get(position).getCharacter());
            //list.get(position).getc1
            if(list.get(position).getProfile_path()!=null)
            {
                Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+list.get(position).getProfile_path()).fit().into(holder.imageView);

            }
            else
            {
                holder.imageView.setBackgroundResource(R.drawable.unavailable_image);


            }

            holder.name.setText(list.get(position).getName());
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

        public class Cast extends RecyclerView.ViewHolder {
            CircleImageView imageView;
            TextView name,rolename;
            RelativeLayout relativeLayout;
            public Cast(View itemView) {
                super(itemView);
                imageView=(CircleImageView)itemView.findViewById(R.id.img_view_fragment_info_cast);
                name=(TextView)itemView.findViewById(R.id.tv_fragment_info_cast_name);
                rolename=(TextView)itemView.findViewById(R.id.tv_fragment_info_cast_role_name);
                relativeLayout=(RelativeLayout)itemView.findViewById(R.id.rl_fragment_info_cast_item);
            }
        }
    }
