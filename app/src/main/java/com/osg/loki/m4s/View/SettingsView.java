package com.osg.loki.m4s.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.osg.loki.m4s.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsView extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.menu)
    ImageView menu;

    public SettingsView() {
        // Required empty public constructor
    }

    public static SettingsView newInstance(String param1, String param2) {
        SettingsView fragment = new SettingsView();
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
        View settings = inflater.inflate(R.layout.settings_page,container,false);
        ButterKnife.bind(this,settings);

        back.setOnClickListener(this);


        return settings;
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:getActivity().getSupportFragmentManager().popBackStack();break;
        }
    }

}
