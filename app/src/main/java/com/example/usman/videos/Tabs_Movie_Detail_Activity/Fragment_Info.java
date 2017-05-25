package com.example.usman.videos.Tabs_Movie_Detail_Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usman.videos.ADAPTERS.Movie_Similiar_Movie_Adapter;
import com.example.usman.videos.ADAPTERS.Movie_Trailer_Adapter;
import com.example.usman.videos.Main_Activities.Activity_Movie_Detail;
import com.example.usman.videos.Main_Fragments.Fragment_WebApi;
import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.INTERFACES.Listener;
import com.example.usman.videos.POJO.MovieMovie;
import com.example.usman.videos.POJO.Rating;
import com.example.usman.videos.POJO.Realm_Session_Id;
import com.example.usman.videos.POJO.Results;
import com.example.usman.videos.POJO.Similiar_Movie_Results;
import com.example.usman.videos.POJO.Similiar_Movies;
import com.example.usman.videos.POJO.Token_new;
import com.example.usman.videos.POJO.Trailer;
import com.example.usman.videos.R;
import com.example.usman.videos.Realm_Objects.Realm_Favourite_List;
import com.example.usman.videos.Realm_Objects.Realm_Watch_List;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;
import com.example.usman.videos.Value;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usman on 4/18/2017.
 */

public class Fragment_Info extends Fragment {
    TextView date, director, budget, revenue, ratingone, description;
    SharedPreferences sharedPreferences;
    int value;
    List<Results> List_trailer;
    List<Similiar_Movie_Results> List_similiar_movies;
    String token, sessiontoken;
    Results[] results_trailer;
    RecyclerView rv_trailer;
    RecyclerView rv_similiar_movie;
    Movie_Trailer_Adapter movie_Trailer_Adapter;
    Movie_Similiar_Movie_Adapter movie_Similiar_Movie_Adapter;
    ImageButton rate, add;
    String year;
    String title;
    String genre;
    String image_path;
    String rating;
    int movie_id;
    Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_activity_fragment_info, container, false);
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
        value = sharedPreferences.getInt("MOVIE_ID", 0);
        Set_Text();
        Set_Trailer();
        Set_Simliar_Movies();
        Get_Token();
        Rate();
        //getTotalHeightOfRecyclerView(rv_similiar_movie);
        return v;
    }
    private int getTotalHeightOfRecyclerView(RecyclerView recyclerView){

        int totalHeight         = 0;

        int totalCount          = recyclerView.getAdapter().getItemCount();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview        = inflater.inflate(R.layout.detail_activity_fragment_info, null, false);
        contentview.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int height              = contentview.getMeasuredHeight();

        for (int i = 0; i < totalCount; i++) {
            totalHeight += height;
        }

        return totalHeight;
    }

    public void Get_Widgets(View v) {
        ratingone = (TextView) v.findViewById(R.id.tv_movie_detail_one_rating_one);
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
        //int value= getArguments().getInt("id");
        rx.Observable<MovieMovie> call = apiService.getMovieDetails(value, Global.key);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieMovie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieMovie movieMovie) {
                        ratingone.setText(String.valueOf(movieMovie.getVoteAverage()));
                        description.setText(movieMovie.getOverview());
                        date.setText(movieMovie.getReleaseDate());
                        //director.setText(movieMovie.get);
                        budget.setText("$ " + NumberFormat.getNumberInstance(Locale.US).format(movieMovie.getBudget()));
                        revenue.setText("$ " + NumberFormat.getNumberInstance(Locale.US).format(movieMovie.getRevenue()));
                        year=movieMovie.getReleaseDate();
                        title=movieMovie.getTitle();
                        image_path=movieMovie.getBackdropPath();
                        movie_id=movieMovie.getId();
                        rating=movieMovie.getVoteAverage().toString();
                    }
                });
    }

    public void Set_Trailer() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        //int value= getArguments().getInt("id");
        rx.Observable<Trailer> call = apiService.gettrailer(String.valueOf(value), Global.key);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Trailer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Trailer trailer) {
                        Results[] results = trailer.getResults();
                        List_trailer = new ArrayList<>();
                        results_trailer = List_trailer.toArray(results);
                        List_trailer = Arrays.asList(results);
                        movie_Trailer_Adapter = new Movie_Trailer_Adapter(getContext(), List_trailer, new Listener() {
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
                });
    }

    public void Set_Simliar_Movies() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        //int value= getArguments().getInt("id");
        rx.Observable<Similiar_Movies> call = apiService.getsimiliarmovies(String.valueOf(value), Global.key);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Similiar_Movies>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Similiar_Movies similiar_movies) {
                        Similiar_Movie_Results[] results = similiar_movies.getResults();
                        //results_similiar_movies=List_trailer.toArray(results);
                        List_similiar_movies = Arrays.asList(results);
                        //List_trailer.get(1).g
                        movie_Similiar_Movie_Adapter = new Movie_Similiar_Movie_Adapter(getContext(), List_similiar_movies, new Listener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Intent intent = new Intent(getActivity().getBaseContext(), Activity_Movie_Detail.class);
                                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("MOVIE_ID", Integer.parseInt(List_similiar_movies.get(position).getId()));
                                editor.commit();
                                intent.putExtra("MOVIE_ID", Integer.parseInt(List_similiar_movies.get(position).getId()));
                                getActivity().startActivity(intent);
                            }
                        });
                        LinearLayoutManager horizontalLayoutManagaer
                                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        rv_similiar_movie.setLayoutManager(horizontalLayoutManagaer);
                        //rv_trailer.setLayoutManager(new GridLayoutManager(getContext(),2));

                        rv_similiar_movie.setAdapter(movie_Similiar_Movie_Adapter);


                    }
                });
    }

    public void Get_Token() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rx.Observable<Token_new> call = apiInterface.gettoken(Global.key);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Token_new>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Token_new token_new) {
                        token = token_new.request_token;

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
                            Value values=new Value(ratingBar.getRating()*2);
                            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                            rx.Observable<Rating> call = apiInterface.give_rating("application/json;charset=utf-8",value,Global.key,realm_session_id.getSession_id(), values);
                            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<Rating>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(Rating rating) {
                                            Toast.makeText(getContext(),rating.getStatus_message(),Toast.LENGTH_LONG).show();

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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Movie();
            }
        });
    }
    public void Add_Movie()
    {

        final CharSequence[] items =
                {"Watch List","Favourite List"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==1)
                {
                    Long count=realm.where(Realm_Favourite_List.class).equalTo("MOVIE_ID",movie_id).count();
                    if(count==0)
                    {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                Realm_Favourite_List realm_favourite_list=realm.createObject(Realm_Favourite_List.class);
                                realm_favourite_list.setTitle(title);
                                realm_favourite_list.setMOVIE_ID(movie_id);
                                realm_favourite_list.setYear(year);
                                realm_favourite_list.setImage_path(image_path);
                                realm_favourite_list.setRating(rating);
                            }
                        });
                        Toast.makeText(getContext(),"Successfully Added",Toast.LENGTH_LONG).show();


                    }
                    else
                    {
                        Toast.makeText(getContext(),"Already Exists in List",Toast.LENGTH_LONG).show();
                    }

                }

                else
                {
                    Long count=realm.where(Realm_Watch_List.class).equalTo("MOVIE_ID",movie_id).count();

                    if(count==0)
                    {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                Realm_Watch_List realm_favourite_list=realm.createObject(Realm_Watch_List.class);
                                realm_favourite_list.setTitle(title);
                                realm_favourite_list.setMOVIE_ID(value);
                                realm_favourite_list.setYear(year);
                                realm_favourite_list.setImage_path(image_path);
                                realm_favourite_list.setRating(rating);

                            }
                        });
                        Toast.makeText(getContext(),"Successfully Added",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(getContext(),"Already Exists in List",Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

}

