package com.example.usman.videos.Tabs_Tv_Shows_Detail_Activity;

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.example.usman.videos.Realm_Objects.Realm_Favourite_List;
import com.example.usman.videos.Realm_Objects.Realm_Watch_List;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;
import com.example.usman.videos.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usman on 5/3/2017.
 */

public class TV_Shows_Fragment_Info extends Fragment {
    TextView date, director, budget, revenue, ratingone, ratingtwo, description;
    SharedPreferences sharedPreferences;
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
    String year;
    String title;
    String genre;
    String image_path;
    String rating;
    int tv_show_id;
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
        tv_show_id = sharedPreferences.getInt("TV_SHOW_ID", 0);
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
        rx.Observable<Tv_Shows_Detail_Basic> call = apiService.get_tv_show_detail_basic(number, Global.key);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Tv_Shows_Detail_Basic>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Tv_Shows_Detail_Basic tv_shows_detail_basic) {
                        ratingone.setText(String.valueOf(tv_shows_detail_basic.getVote_average()));
                        description.setText(tv_shows_detail_basic.getOverview());
                        date.setText(tv_shows_detail_basic.getFirst_air_date());
                        director.setText(tv_shows_detail_basic.getLast_air_date());
                        title=tv_shows_detail_basic.getOriginal_name();
                        image_path=tv_shows_detail_basic.getBackdrop_path();
                        rating=tv_shows_detail_basic.getVote_average();
                        tv_show_id=Integer.parseInt(tv_shows_detail_basic.getId());
                        Networks[] list=tv_shows_detail_basic.getNetworks();
                        List<Networks> listed=Arrays.asList(list);
                        StringBuilder stringbuilder=new StringBuilder();
                        for(int a=0;a<listed.size();a++)
                        {
                            stringbuilder.append(listed.get(a).getName());
                        }

                        budget.setText(stringbuilder.toString());
                        Created_by[] createdby=tv_shows_detail_basic.getCreated_by();
                        List<Created_by> listcreate=Arrays.asList(createdby);
                        int sizecreate=listcreate.size();
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int a=0;a<sizecreate;a++)
                        {
                            stringBuilder.append(listcreate.get(a).getName().toString()+", ");
                        }
                        revenue.setText(stringBuilder.toString());


                    }
                });
    }

    public void Set_Trailer() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        //int TV_SHOW_ID= getArguments().getInt("id");
        rx.Observable<Tv_Shows_trailer> call = apiService.get_tv_show_trailer(String.valueOf(tv_show_id), Global.key);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Tv_Shows_trailer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Tv_Shows_trailer tv_shows_trailer) {
                        Tv_Shows_trailer_Results[] results =tv_shows_trailer.getResults();
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
                });
    }

    public void Set_Simliar_Movies() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        rx.Observable<Tv_Shows_Similiar> call = apiService.get_tv_show_similiar_tv_shows(String.valueOf(tv_show_id), Global.key);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Tv_Shows_Similiar>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Tv_Shows_Similiar tv_shows_similiar) {
                        Tv_Shows_Similiar_Results[] results = tv_shows_similiar.getResults();
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
                            Float rateing=ratingBar.getRating();
                            Value values=new Value(rateing*2);
                            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                            rx.Observable<Rating> call = apiInterface.give_rating("application/json;charset=utf-8",tv_show_id,Global.key,realm_session_id.getSession_id(), values);
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
                Add_Tv_Show();
            }
        });
    }

    public void Add_Tv_Show()
    {

        final CharSequence[] items =
                {"Watch List","Favourite List"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==1)
                {
                    Long count=realm.where(Realm_Favourite_List.class).equalTo("TV_SHOW_ID",tv_show_id).count();
                    if(count==0)
                    {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                Realm_Favourite_List realm_favourite_list=realm.createObject(Realm_Favourite_List.class);
                                realm_favourite_list.setTitle(title);
                                realm_favourite_list.setTV_SHOW_ID(tv_show_id);
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
                    Long count=realm.where(Realm_Watch_List.class).equalTo("TV_SHOW_ID",tv_show_id).count();

                    if(count==0)
                    {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                Realm_Watch_List realm_favourite_list=realm.createObject(Realm_Watch_List.class);
                                realm_favourite_list.setTitle(title);
                                realm_favourite_list.setTV_SHOW_ID(tv_show_id);
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

