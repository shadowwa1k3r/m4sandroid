package com.osg.loki.m4s.View;


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

import com.osg.loki.m4s.Model.Token;
import com.osg.loki.m4s.Model.UserRegisterModel;
import com.osg.loki.m4s.R;
import com.osg.loki.m4s.Tools.Auth;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link auth2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class auth2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.sign)
    Button signin;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.password)
    TextView password;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.confirm)
    TextView confirm_code;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.shtor)
    FrameLayout shtor;

    private Retrofit mRetrofit;
    private Auth service;


    public auth2() {
        // Required empty public constructor
    }

    public static auth2 newInstance(String param1, String param2) {
        auth2 fragment = new auth2();
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
        View view = inflater.inflate(R.layout.auth_lay2,container,false);
        ButterKnife.bind(this,view);


        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_TOKEN = "token";
        final String DOESNT_EXIST = "-1";

        final SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://app.fvv.uz/")
                //.baseUrl("http://192.168.1.102:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = mRetrofit.create(Auth.class);
        confirm_code.setVisibility(View.INVISIBLE);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirm_code.isEnabled()){
                    if(!confirm_code.getText().toString().equals("")) {
                        Call<Token> confirm = service.confirm(phone.getText().toString(), confirm_code.getText().toString());
                        confirm.enqueue(new Callback<Token>() {
                            @Override
                            public void onResponse(Call<Token> call, Response<Token> response) {
                                Log.i("confrim", "onResponse: " + response.code() + response.message() + response.body() + response.errorBody());
                                if (response.code() == 202) {
                                    Log.i("confirm", "onResponse: " + response.body().getToken());
                                    Toast.makeText(getContext(), "Registered as:" + response.body().getToken(), Toast.LENGTH_LONG).show();
                                    Intent main = new Intent(getActivity(), MainActivity.class);
                                    main.putExtra("token", response.body().getToken());
                                    prefs.edit().putString(PREF_TOKEN, response.body().getToken()).apply();
                                    startActivity(main);
                                    getActivity().finish();
                                }
                                if (response.code() == 406) {
                                    Toast.makeText(getContext(), "Пользователь с таким номером телефона уже существует!", Toast.LENGTH_LONG).show();
                                } else {
                                    Log.e("confirm", "onResponse:  code " + response.code() + "response " + response.body());
                                }
                            }

                            @Override
                            public void onFailure(Call<Token> call, Throwable t) {
                                Log.e("confirm", "onFailure: " + t.getMessage());
                            }
                        });
                    } else {
                        Toast.makeText(getContext(),"Введите код потдверждение",Toast.LENGTH_LONG).show();
                    }
                } else {
                    if(!phone.getText().toString().equals("")&&!password.getText().toString().equals("")){
                    loading.setVisibility(View.VISIBLE);
                    shtor.setVisibility(View.VISIBLE);
                    Call<ResponseBody> signup = service.signup(new UserRegisterModel(phone.getText().toString(), password.getText().toString()));
                    signup.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.i("signup", "onResponse: "+response.code());
                            if (response.code()==201){
                                username.setEnabled(false);
                                password.setEnabled(false);
                                phone.setEnabled(false);
                                confirm_code.setEnabled(true);
                                confirm_code.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(),"Введите код потдверждение",Toast.LENGTH_LONG).show();
                                loading.setVisibility(View.GONE);
                                shtor.setVisibility(View.GONE);
                            }
                            else if (response.code()==406){
                                Toast.makeText(getContext(),"Пользователь с таким номером телефона уже существует!",Toast.LENGTH_LONG).show();
                                loading.setVisibility(View.GONE);
                                shtor.setVisibility(View.GONE);
                            }
                            else {
                                Toast.makeText(getContext(),"Ошибка при регистрации",Toast.LENGTH_LONG).show();
                                loading.setVisibility(View.GONE);
                                shtor.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("signup", "onFailure: "+t.getMessage() );
                            Toast.makeText(getContext(),"Ошибка сети",Toast.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                            shtor.setVisibility(View.GONE);
                        }
                    });
                }
                else {
                        Toast.makeText(getContext(),"Введите номер телефона и пароль",Toast.LENGTH_LONG).show();
                    }
                }




                /*Intent main=new Intent(getActivity(),MainActivity.class);
                startActivity(main);
                getActivity().finish();*/
            }
        });
        return view;
    }

}
