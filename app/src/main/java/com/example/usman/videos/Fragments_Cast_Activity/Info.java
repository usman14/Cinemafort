package com.example.usman.videos.Fragments_Cast_Activity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usman.videos.Fragment_WebApi;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.POJO.Person;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by usman on 4/22/2017.
 */

public class Info extends Fragment {
    RecyclerView recyclerView;
    TextView born,birthplace,descripition;
    int personid;
    SharedPreferences sharedPreferences;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.cast_activity_fragment_info,container,false);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
        personid=sharedPreferences.getInt("cast_id",0);
        birthplace=(TextView)v.findViewById(R.id.tv_cast_activity_fragment_info_birthplace_1);
        born=(TextView)v.findViewById(R.id.tv_cast_activity_fragment_info_born_1);
        descripition=(TextView)v.findViewById(R.id.tv_cast_activity_fragment_info_desc);
        imageView=(ImageView)v.findViewById(R.id.img_view_cast_activity_fragment_info_item_images) ;
        Get_Data();
        return v;

    }
    public void Get_Data()
    {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<Person> callresult=apiInterface.getperson(personid, Global.key);
        callresult.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                descripition.setText(response.body().getBiography());
                birthplace.setText(response.body().getPlace_of_birth());
                born.setText(response.body().getBirthday());
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });
    }
}
