package com.osg.loki.m4s.View;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.osg.loki.m4s.Model.MainPageModel;
import com.osg.loki.m4s.Model.Wikimodel;
import com.osg.loki.m4s.R;
import com.osg.loki.m4s.SplashScreenActivity;
import com.osg.loki.m4s.Tools.Urls;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewSettings extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    android.support.v7.app.AlertDialog mAlertDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Activity fa;

    private Button conf,logout,upgrade,lang;
    private MainPageModel baza;


    public NewSettings() {
        // Required empty public constructor
    }

    public static NewSettings newInstance(String param1, String param2) {
        NewSettings fragment = new NewSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fa = this.getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_new_settings, container, false);
        logout = view.findViewById(R.id.exit);
        conf = view.findViewById(R.id.conf);
        upgrade = view.findViewById(R.id.upgrade);
        lang = view.findViewById(R.id.lang);
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.168.1.102:8000/")
                .baseUrl("https://app.fvv.uz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Urls service = retrofit.create(Urls.class);

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),R.string.developing,Toast.LENGTH_LONG).show();
            }
        });
        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_TOKEN = "token";
        final String DOESNT_EXIST = "-1";
        final SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.edit().putString(PREF_TOKEN,DOESNT_EXIST).apply();
                Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
                intent.putExtra("re","1");
                startActivity(intent);

                getActivity().finish();
            }
        });
        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LanguageSettingsActivity.class));
            }
        });

        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Call<List<Wikimodel>> sync = service.syncwiki(prefs.getString(PREF_TOKEN,DOESNT_EXIST),1);
                sync.enqueue(new Callback<List<Wikimodel>>() {
                    @Override
                    public void onResponse(Call<List<Wikimodel>> call, Response<List<Wikimodel>> response) {
                        Log.d("sync", "onResponse: "+" "+response.code());
                        if (response.code()==200) {

                            baza = new MainPageModel(Realm.getDefaultInstance());
                            baza.setHelpList(response.body());
                            Log.d("sync", "onResponse: "+" "+response.body().size());
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle(R.string.updating_db)
                                    .setMessage(R.string.db_updated).setPositiveButton("OK", null).show();
                            alertDialog.show();
                          Log.e("checkupdateddb", "onClick: " + baza.getHelpList("uz","ЧС: Как действовать?").get(0).getContent()+" "+response.body().get(0).getImage() + " size=" + baza.getHelpList("uz","ЧС: Как действовать?").size());
                        }
                        else if (response.code()==304){
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle(R.string.updating_db)
                                    .setMessage(R.string.update_not_required).setPositiveButton("OK", null).show();
                            alertDialog.show();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle(R.string.updating_db)
                                    .setMessage(R.string.server_error_update).setPositiveButton("OK",null).show();
                            alertDialog.show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Wikimodel>> call, Throwable t) {
                        Log.d("sync", "onResponse: "+t.getMessage());
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle(R.string.updating_db)
                                .setMessage(R.string.network_error).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).show();
                        alertDialog.show();
                    }
                });
                /*List<Wikimodel> list = new ArrayList<>();
                list.add(new Wikimodel(1,"title1","1kontent","1image","bir salat"));
                list.add(new Wikimodel(2,"title2","2kontent","2image","2bir salat"));
*/



            }
        });

        return view;
    }


}
