package com.example.usman.videos.Cast_Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usman.videos.INTERFACES.ApiInterface;
import com.example.usman.videos.POJO.Person;
import com.example.usman.videos.R;
import com.example.usman.videos.UTILITIES.ApiClient;
import com.example.usman.videos.UTILITIES.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usman on 4/18/2017.
 */

public class Cast_Info  extends Fragment {
    TextView born,birthplace,descripition;
    int castid;
    SharedPreferences sharedPreferences;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.cast_activity_fragment_info,container,false);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
        castid =sharedPreferences.getInt("cast_id",0);
        birthplace=(TextView)v.findViewById(R.id.tv_cast_activity_fragment_info_birthplace_1);
        born=(TextView)v.findViewById(R.id.tv_cast_activity_fragment_info_born_1);
        descripition=(TextView)v.findViewById(R.id.tv_cast_activity_fragment_info_desc);
        Get_Data();
        return v;

    }
    public void Get_Data()
    {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        rx.Observable<Person> callresult=apiInterface.getperson(castid, Global.key);
        callresult.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Person>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Person person) {
                        descripition.setText(person.getBiography());
                        birthplace.setText(person.getPlace_of_birth());
                        born.setText(person.getBirthday());
                    }
                });

    }
}
