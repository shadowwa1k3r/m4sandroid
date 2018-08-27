package com.osg.loki.m4s.View;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.osg.loki.m4s.R;
import com.osg.loki.m4s.SplashScreenActivity;

public class NewSettings extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button conf,logout;


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

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"в разработке",Toast.LENGTH_LONG).show();
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

        return view;
    }

}
