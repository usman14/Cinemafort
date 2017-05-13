package com.example.usman.videos.Main_Fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.POJO.Realm_Session_Id;
import com.example.usman.videos.POJO.Token_new;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;

import java.util.Calendar;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_WebApi extends Activity {


    private WebView webView;
    Realm realm;

    Activity activity;
    private ProgressDialog progDailog;
    String url,token;
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_web_api);
        realm=Realm.getDefaultInstance();
        realm.init(this);
        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        token=intent.getStringExtra("token");
        activity = this;

        progDailog = ProgressDialog.show(activity, "Loading", "Please wait...", true);
        progDailog.setCancelable(false);


        webView = (WebView) findViewById(R.id.wv_WebApi_Authorise);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                final String[] tokennew = new String[1];
                progDailog.dismiss();
                if (url.contains("allow"))
                {

                    ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
                    Call<Token_new> result=apiInterface.getsession(Global.key,token);
                    result.enqueue(new Callback<Token_new>() {
                        @Override
                        public void onResponse(Call<Token_new> call, Response<Token_new> response) {
                          tokennew[0] =response.body().getSession_id();

                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Realm_Session_Id realm_session_id=realm.createObject(Realm_Session_Id.class);
                                    realm_session_id.setSession_id(tokennew[0]);
                                }
                            });
                            Long count=realm.where(Realm_Session_Id.class).count();
                            Toast.makeText(getApplication(),count.toString(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Token_new> call, Throwable t) {

                        }
                    });
                }

            }
        });

        webView.loadUrl(url);

    }
}