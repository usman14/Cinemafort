package com.example.usman.videos.INTERFACES;

import android.renderscript.Sampler;

import com.example.usman.videos.POJO.CastCrew;
import com.example.usman.videos.POJO.Cast_1;
import com.example.usman.videos.POJO.Cast_Movies;
import com.example.usman.videos.POJO.Cast_Tv_Shows;
import com.example.usman.videos.POJO.Cast_one;
import com.example.usman.videos.POJO.Discover_Pojo;
import com.example.usman.videos.POJO.MoviesResponse;
import com.example.usman.videos.POJO.MovieMovie;
import com.example.usman.videos.POJO.Person;
import com.example.usman.videos.POJO.Popular_People;
import com.example.usman.videos.POJO.Rate;
import com.example.usman.videos.POJO.Rating;
import com.example.usman.videos.POJO.Review;
import com.example.usman.videos.POJO.Review_Results;
import com.example.usman.videos.POJO.Search_Actors;
import com.example.usman.videos.POJO.Search_Movies;
import com.example.usman.videos.POJO.Search_Tv_Shows;
import com.example.usman.videos.POJO.Similiar_Movies;
import com.example.usman.videos.POJO.Token_new;
import com.example.usman.videos.POJO.Trailer;
import com.example.usman.videos.POJO.Tv_Shows_Detail_Basic;
import com.example.usman.videos.POJO.Tv_Shows_Detail_Basic_results;
import com.example.usman.videos.POJO.Tv_Shows_Popular;
import com.example.usman.videos.POJO.Tv_Shows_Similiar;
import com.example.usman.videos.POJO.Tv_Shows_Similiar_Results;
import com.example.usman.videos.POJO.Tv_Shows_trailer;
import com.example.usman.videos.POJO.Tv_Shows_trailer_Results;
import com.example.usman.videos.Value;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @Headers("Content-Type: application/json;charset=utf-8")

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieMovie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MoviesResponse> getnowplaying(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MoviesResponse> getupcoming(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MoviesResponse> getpopular(@Query("api_key") String apiKey);

    @GET("authentication/token/new")
    Call<Token_new> gettoken(@Query("api_key") String apiKey);

    @GET("authentication/session/new")
    Call<Token_new> getsession(@Query("api_key") String apiKey,@Query("request_token") String token);

    @GET("movie/{movie_id}/videos")
    Call<Trailer> gettrailer(@Path("movie_id") String token,@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Call<Similiar_Movies>getsimiliarmovies(@Path("movie_id") String id, @Query("api_key") String apikey);

    @GET("movie/{movie_id}/credits")
    Call<CastCrew> getcast(@Path("movie_id") String id,@Query("api_key") String key);

    @POST("movie/{movie_id}/rating")
    Call<Rate> getrate(@Header("Content-Type")String header,@Path("movie_id") int id,@Query("api_key")String key,
                       @Query("session_id") String sessionid);

    @GET("movie/{movie_id}/reviews")
    Call<Review> getreview(@Path("movie_id") int id,@Query("api_key") String key );

    @GET("person/{person_id}")
    Call<Person> getperson(@Path("person_id")int personid,@Query("api_key") String apikey);

    @GET("person/{person_id}/movie_credits")
    Call<Cast_Movies> getcast_movie(@Path("person_id") int id, @Query("api_key") String key);

    @GET("person/{person_id}/tv_credits")
    Call<Cast_Tv_Shows> getcast_tvshows(@Path("person_id") int id, @Query("api_key") String key);

    @GET("search/tv")
    Call<Search_Tv_Shows> getsearch_tvshows(@Query("query") String query, @Query("api_key") String key);

    @GET("search/person")
    Call<Search_Actors> getsearch_person(@Query("query") String query, @Query("api_key") String key);

    @GET("search/movie")
    Call<Search_Movies> getsearch_movie(@Query("query") String query, @Query("api_key") String key);

    @GET("discover/movie")
    Call<Discover_Pojo> getdiscover(@Query("api_key") String apikey,@Query("language") String language
            ,@Query("sort_by") String pop,@Query("include_adult")Boolean adult,@Query("include_video")Boolean video,
                                    @Query("primary_release_date.gte")int dateone
    ,@Query("primary_release_date.lte") int datetwo,@Query("sort_by")String sortby);

    @GET("discover/movie")
    Call<Discover_Pojo> getdiscover_genre(@Query("api_key") String apikey,@Query("language") String language
            ,@Query("sort_by") String pop,@Query("include_adult")Boolean adult,@Query("include_video")Boolean video,
                                    @Query("primary_release_date.gte")int dateone
            ,@Query("primary_release_date.lte") int datetwo,@Query("with_genres")int genre,@Query("sort_by")String sortby);

    @POST("movie/{movie_id}/rating")
    Call<Rating> give_rating(@Header("Content-type")String header,@Path("movie_id") int movieid,@Query("api_key") String apikey,
                             @Query("session_id")String sessionid, @Body Value value);

    @GET("tv/airing_today")
    Call<Tv_Shows_Popular> get_tvshow_airingtoday(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<Tv_Shows_Popular> get_tvshow_popular(@Query("api_key") String apiKey);

    @GET("tv/on_the_air")
    Call<Tv_Shows_Popular> get_tvshow_tvontheair(@Query("api_key") String apiKey);

    @GET("tv/top_rated")
    Call<Tv_Shows_Popular> get_tvshow_toprated(@Query("api_key") String apiKey);


    @GET("tv/{tv_id}")
    Call<Tv_Shows_Detail_Basic> get_tv_show_detail_basic(@Path("tv_id") String query, @Query("api_key") String key);
    @GET("tv/{tv_id}/videos")

    Call<Tv_Shows_trailer> get_tv_show_trailer(@Path("tv_id") String query, @Query("api_key") String key);

    @GET("tv/{tv_id}/similar")

    Call<Tv_Shows_Similiar> get_tv_show_similiar_tv_shows(@Path("tv_id") String query, @Query("api_key") String key);

    @GET("tv/{tv_id}/credits")

    Call<CastCrew> get_tv_show_cast(@Path("tv_id") String query, @Query("api_key") String key);

    @GET("person/popular")
    Call<Popular_People> get_popular_person(@Query("api_key") String apiKey);
    @GET("tv/{tv_id}")
    Call<Tv_Shows_Detail_Basic> get_tv_show_episodes(@Path("tv_id") String query, @Query("api_key") String key);
}
