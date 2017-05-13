package com.example.usman.videos.Tabs_Tv_Shows_Detail_Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usman.videos.ADAPTERS.Tv_Shows_Similiar_Shows_Adapter;
import com.example.usman.videos.ADAPTERS.Tv_Shows_Trailer_Adapter;
import com.example.usman.videos.Main_Activities.Activity_Tv_Shows_Detail;
import com.example.usman.videos.Main_Fragments.Fragment_WebApi;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.Created_by;
import com.example.usman.videos.POJO.Networks;
import com.example.usman.videos.POJO.Rating;
import com.example.usman.videos.POJO.Realm_Session_Id;
import com.example.usman.videos.POJO.Similiar_Movie_Results;
import com.example.usman.videos.POJO.Token_new;
import com.example.usman.videos.POJO.Tv_Shows_Detail_Basic;
import com.example.usman.videos.POJO.Tv_Shows_Similiar;
import com.example.usman.videos.POJO.Tv_Shows_Similiar_Results;
import com.example.usman.videos.POJO.Tv_Shows_trailer;
import com.example.usman.videos.POJO.Tv_Shows_trailer_Results;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by usman on 5/3/2017.
 */

public class TV_Shows_Fragment_Info extends Fragment {
    TextView date, director, budget, revenue, ratingone, ratingtwo, description;
    SharedPreferences sharedPreferences;
    int tv_show_id;
    List<Tv_Shows_trailer_Results> List_trailer;
    List<Similiar_Movie_Results> List_similiar_movies;
    String token;
    Tv_Shows_trailer_Results[] results_trailer;
    RecyclerView rv_trailer;
    RecyclerView rv_similiar_movie;
    Tv_Shows_Trailer_Adapter movie_Trailer_Adapter;
    Tv_Shows_Similiar_Shows_Adapter movie_Similiar_Movie_Adapter;
    ImageButton rate, add;
    FrameLayout frameLayout;
    Realm realm;
    String number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tv_show_fragment_info, container, false);
        Get_Widgets(v);
        Realm.init(getContext());
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        tv_show_id = sharedPreferences.getInt("tvshow_id", 0);
        number=String.valueOf(tv_show_id);
        Set_Text();
        Set_Trailer();
        Set_Simliar_Movies();
        Get_Token();
        Rate();
        return v;
    }

    public void Get_Widgets(View v) {
        ratingone = (TextView) v.findViewById(R.id.tv_movie_detail_one_rating_one);
        ratingtwo = (TextView) v.findViewById(R.id.tv_movie_detail_one_rating_two);
        date = (TextView) v.findViewById(R.id.tv_movie_detail_one_dvd_release_date_one);
        director = (TextView) v.findViewById(R.id.tv_movie_detail_one_director_one);
        budget = (TextView) v.findViewById(R.id.tv_movie_detail_one_budget_one);
        revenue = (TextView) v.findViewById(R.id.tv_movie_detail_one_revenue_one);
        description = (TextView) v.findViewById(R.id.tv_movie_detail_one_descrip);
        rv_trailer = (RecyclerView) v.findViewById(R.id.recyclerview_movie_detail_trailer);
        rv_similiar_movie = (RecyclerView) v.findViewById(R.id.recyclerview_movie_detail_similiar_movies);
        rate = (ImageButton) v.findViewById(R.id.img_btn_movie_detail_rate);
        add = (ImageButton) v.findViewById(R.id.img_view_movie_detail_add);

    }

    public void Set_Text() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Tv_Shows_Detail_Basic> call = apiService.get_tv_show_detail_basic(number, Global.key);
        call.enqueue(new Callback<Tv_Shows_Detail_Basic>() {
            @Override
            public void onResponse(Call<Tv_Shows_Detail_Basic> call, Response<Tv_Shows_Detail_Basic> response) {
                int statusCode = response.code();
                ratingone.setText(String.valueOf(response.body().getVote_average()));
                ratingtwo.setText(String.valueOf(response.body().getVote_count()));
                description.setText(response.body().getOverview());
                date.setText(response.body().getFirst_air_date());
                director.setText(response.body().getLast_air_date());
                Networks[] list=response.body().getNetworks();
                List<Networks> listed=Arrays.asList(list);
                StringBuilder stringbuilder=new StringBuilder();
                for(int a=0;a<listed.size();a++)
                {
                    stringbuilder.append(listed.get(a).getName());
                }

                budget.setText(stringbuilder.toString());
                Created_by[] createdby=response.body().getCreated_by();
                List<Created_by> listcreate=Arrays.asList(createdby);
                int sizecreate=listcreate.size();
                StringBuilder stringBuilder = new StringBuilder();
                for(int a=0;a<sizecreate;a++)
                {
                 stringBuilder.append(listcreate.get(a).getName().toString()+", ");
                }
                revenue.setText(stringBuilder.toString());


            }

            @Override
            public void onFailure(Call<Tv_Shows_Detail_Basic> call, Throwable t) {

            }
        });
    }

    public void Set_Trailer() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        //int tv_show_id= getArguments().getInt("id");
        Call<Tv_Shows_trailer> call = apiService.get_tv_show_trailer(String.valueOf(tv_show_id), Global.key);
        call.enqueue(new Callback<Tv_Shows_trailer>() {
            @Override
            public void onResponse(Call<Tv_Shows_trailer> call, Response<Tv_Shows_trailer> response) {
                int statusCode = response.code();
                Tv_Shows_trailer_Results[] results = response.body().getResults();
                List_trailer = new ArrayList<>();
                results_trailer = List_trailer.toArray(results);
                List_trailer = Arrays.asList(results);
                movie_Trailer_Adapter = new Tv_Shows_Trailer_Adapter(getContext(), List_trailer, new Listener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://m.youtube.com/watch?v=" +
                                List_trailer.get(position + 1).getKey()));
                        startActivity(intent);
                    }
                });
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rv_trailer.setLayoutManager(horizontalLayoutManagaer);
                //rv_trailer.setLayoutManager(new GridLayoutManager(getContext(),2));

                rv_trailer.setAdapter(movie_Trailer_Adapter);


            }

            @Override
            public void onFailure(Call<Tv_Shows_trailer> call, Throwable t) {

            }
        });
    }

    public void Set_Simliar_Movies() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Tv_Shows_Similiar> call = apiService.get_tv_show_similiar_tv_shows(String.valueOf(tv_show_id), Global.key);
        call.enqueue(new Callback<Tv_Shows_Similiar>() {
            @Override
            public void onResponse(Call<Tv_Shows_Similiar> call, Response<Tv_Shows_Similiar> response) {
                int statusCode = response.code();
                Tv_Shows_Similiar_Results[] results = response.body().getResults();
             final List<Tv_Shows_Similiar_Results>   list = Arrays.asList(results);
                movie_Similiar_Movie_Adapter = new Tv_Shows_Similiar_Shows_Adapter(getContext(), list, new Listener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(getActivity().getBaseContext(), Activity_Tv_Shows_Detail.class);
                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("tvshow_id", Integer.parseInt(list.get(position).getId()));
                        editor.commit();
                        getActivity().startActivity(intent);
                    }
                });
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rv_similiar_movie.setLayoutManager(horizontalLayoutManagaer);

                rv_similiar_movie.setAdapter(movie_Similiar_Movie_Adapter);
            }
            @Override
            public void onFailure(Call<Tv_Shows_Similiar> call, Throwable t) {

            }
        });
    }

    public void Get_Token() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Token_new> token_newCall = apiInterface.gettoken(Global.key);
        token_newCall.enqueue(new Callback<Token_new>() {
            @Override
            public void onResponse(Call<Token_new> call, Response<Token_new> response) {
                token = response.body().request_token;

            }

            @Override
            public void onFailure(Call<Token_new> call, Throwable t) {

            }
        });
    }

    public void Rate() {
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long count=realm.where(Realm_Session_Id.class).count();


                if (count == 0) {
                    final String urls = "https://www.themoviedb.org/authenticate/" + token;
                    Intent intent=new Intent(getActivity().getBaseContext(),Fragment_WebApi.class);
                    intent.putExtra("url",urls);
                    intent.putExtra("token",token);
                    getActivity().startActivity(intent);


                }
                else
                {
                    final Dialog rankDialog = new Dialog(getContext(), R.style.FullHeightDialog);
                    rankDialog.setContentView(R.layout.rating_bar);
                    final RatingBar ratingBar=(RatingBar)rankDialog.findViewById(R.id.ratingbar_rating_bar);
                    rankDialog.setCancelable(true);
                    Button cancel = (Button) rankDialog.findViewById(R.id.btn_rating_bar_cancel);
                    Button rate = (Button) rankDialog.findViewById(R.id.btn_rating_bar_rate);
                    rate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Realm_Session_Id realm_session_id=realm.where(Realm_Session_Id.class).findFirst();
                            realm_session_id.getSession_id();
                            Float rateing=ratingBar.getRating();
                            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                            Call<Rating> token_newCall = apiInterface.give_rating(tv_show_id,Global.key,realm_session_id.getSession_id(),
                                    ratingBar.getRating());
                            token_newCall.enqueue(new Callback<Rating>() {
                                @Override
                                public void onResponse(Call<Rating> call, Response<Rating> response) {
                                    Toast.makeText(getContext(),response.body().getStatus_message(),Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<Rating> call, Throwable t) {

                                }
                            });

                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            rankDialog.dismiss();
                        }
                    });

                    rankDialog.show();



                }

            }
        });
    }
}

