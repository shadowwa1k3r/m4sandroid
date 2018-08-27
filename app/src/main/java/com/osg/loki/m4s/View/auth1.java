package com.osg.loki.m4s.View;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.osg.loki.m4s.Model.AccountCredentials;
import com.osg.loki.m4s.Model.Token;
import com.osg.loki.m4s.R;
import com.osg.loki.m4s.Tools.Auth;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link auth1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class auth1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.signup)
    TextView signup;
    @BindView(R.id.signin)
    Button signin;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.pwd)
    TextView password;
    @BindView(R.id.shtor)
    FrameLayout shtor;
    @BindView(R.id.loading)
    ProgressBar loading;

    private Retrofit mRetrofit;
    private Auth service;


    public auth1() {
        // Required empty public constructor
    }

    public static auth1 newInstance(String param1, String param2) {
        auth1 fragment = new auth1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_lay,container,false);
        ButterKnife.bind(this,view);

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_TOKEN = "token";
        final String DOESNT_EXIST = "-1";

        final SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String pToken =prefs.getString(PREF_TOKEN,DOESNT_EXIST);
        if (!pToken.equals(DOESNT_EXIST)){
            Intent main=new Intent(getActivity(),MainActivity.class);
            main.putExtra("token",pToken);
            shtor.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            startActivity(main);
            getActivity().finish();
        }


        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://app.fvv.uz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = mRetrofit.create(Auth.class);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!phone.getText().equals("")&&!password.getText().equals("")){
                    shtor.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.VISIBLE);


                Call<Token> signin = service.login(new AccountCredentials(phone.getText().toString(),password.getText().toString()));
                signin.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if(response.code()==200){
                        Log.i("token", "onResponse: "+response.message()+" "+response.code());
                        Log.i("token", "onResponse: "+response.body().getToken());

                        Intent main=new Intent(getActivity(),MainActivity.class);
                        main.putExtra("token",response.body().getToken());
                        shtor.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                        prefs.edit().putString(PREF_TOKEN,response.body().getToken()).apply();

                        startActivity(main);
                        getActivity().finish();

                        } else
                        {
                            shtor.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);
                            Log.i("token", "onResponse: "+response.message()+" "+response.code());
                            Toast.makeText(getContext(),"Проблема при авторизации",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Log.e("token", "onFailure: "+t.getMessage() );
                        shtor.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Проблема при авторизации",Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {
                    Toast.makeText(getContext(),"Введите номер телефона и пароль",Toast.LENGTH_LONG).show();
                }
            }

        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.container,new auth2()).addToBackStack(null).commit();
            }
        });

    /*    Dialog dialog = new Dialog(getActivity());
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });*/
        return view;
    }



}
