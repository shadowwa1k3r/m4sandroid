package com.osg.loki.m4s.View;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.osg.loki.m4s.R;
import com.osg.loki.m4s.Tools.Auth;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecoveryStep extends AppCompatActivity {

    @BindView(R.id.confirm)
    EditText confirm_code;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.sign)
    Button confirm;
    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.retry)
    TextView retry;

    private Retrofit mRetrofit;
    private Auth service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recovery_step1);
        ButterKnife.bind(this);
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://app.fvv.uz/")
//                .baseUrl("http://192.168.1.107:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = mRetrofit.create(Auth.class);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm.setEnabled(true);
                retry.setVisibility(View.INVISIBLE);
                new CountDownTimer(120000,1000){
                    @Override
                    public void onTick(long l) {
                        timer.setVisibility(View.VISIBLE);
                        timer.setText((l/60000)+":"+((l%60000)/1000));
                    }

                    @Override
                    public void onFinish() {
                        retry.setVisibility(View.VISIBLE);
                        confirm.setEnabled(false);
                    }
                }.start();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!phone.getText().toString().equals("")){
                if (!(confirm_code.getVisibility()==View.VISIBLE)){
                    Call checkphone = service.checkPhone(phone.getText().toString());
                    checkphone.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
//                            Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
                            if (response.code()==200){
                                confirm_code.setVisibility(View.VISIBLE);
//                                Toast.makeText(getApplicationContext(),confirm_code.getVisibility(),Toast.LENGTH_LONG).show();

                                new CountDownTimer(120000,1000){
                                    @Override
                                    public void onTick(long l) {
                                        timer.setVisibility(View.VISIBLE);
                                        timer.setText((l/60000)+":"+((l%60000)/1000));
                                    }

                                    @Override
                                    public void onFinish() {
                                        retry.setVisibility(View.VISIBLE);
                                        confirm.setEnabled(false);
                                    }
                                }.start();

                               /* phone.setFocusable(false);
                                phone.setEnabled(false);
                                phone.setInputType(InputType.TYPE_NULL);*/
                            } else {
                                Toast.makeText(getApplicationContext(),getString(R.string.server_error)+response.code(),Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(getApplicationContext(),R.string.server_error+t.getMessage(),Toast.LENGTH_LONG).show();
                            Log.e("error", "onFailure: "+t.getMessage() );
                        }
                    });
                }
                else {
                    if(!confirm_code.getText().toString().equals("")){
                        Call confirmCode = service.confirmPhone(phone.getText().toString(),confirm_code.getText().toString());
                        confirmCode.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if (response.code()==200){
                                    getSupportFragmentManager().beginTransaction().replace(R.id.content_container,RecoveryStep2.newInstance(phone.getText().toString())).addToBackStack(null).commit();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),R.string.server_error+response.code(),Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Toast.makeText(getApplicationContext(),R.string.server_error+t.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(),R.string.confirm_code,Toast.LENGTH_LONG).show();
                    }

                }
                } else {
                    Toast.makeText(getApplicationContext(),R.string.phone_number,Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
