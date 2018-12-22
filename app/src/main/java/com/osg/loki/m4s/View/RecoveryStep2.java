package com.osg.loki.m4s.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.osg.loki.m4s.R;
import com.osg.loki.m4s.SplashScreenActivity;
import com.osg.loki.m4s.Tools.Auth;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecoveryStep2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";



    private String mParam1;

    @BindView(R.id.sign)
    Button ok;
    @BindView(R.id.confirm)
    EditText confirm;
    @BindView(R.id.password)
    EditText password;

    private Retrofit mRetrofit;
    private Auth service;
    public RecoveryStep2() {
        // Required empty public constructor
    }


    public static RecoveryStep2 newInstance(String param1) {
        RecoveryStep2 fragment = new RecoveryStep2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recovery_step2,container,false);
        ButterKnife.bind(this,view);
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://app.fvv.uz/")
//                .baseUrl("http://192.168.1.107:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = mRetrofit.create(Auth.class);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!password.getText().toString().equals("")||confirm.getText().toString().equals("")){
                    Call setPasswd = service.newPasswd(mParam1,password.getText().toString());
                    setPasswd.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.code()==200){
                                Intent intent = new Intent(getActivity(),SplashScreenActivity.class);
                                startActivity(intent);
//                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(),R.string.server_error+response.code(),Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(getContext(),R.string.server_error+t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                } else
                {
                    Toast.makeText(getContext(),R.string.empty_fields     ,Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

}
