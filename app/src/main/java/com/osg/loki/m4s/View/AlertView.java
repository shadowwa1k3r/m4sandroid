package com.osg.loki.m4s.View;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.osg.loki.m4s.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlertView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String token;


    private final static int GPS_ENABLE_REQUEST=15;

    @BindView(R.id.call)
    Button call;
    @BindView(R.id.map)
    Button map;

    public AlertView() {
        // Required empty public constructor
    }


    public static AlertView newInstance(String param1) {
        AlertView fragment = new AlertView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token= getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View AV = inflater.inflate(R.layout.alert_lay,container,false);

        ButterKnife.bind(this,AV);



        try{
            int off= Settings.Secure.getInt(getActivity().getContentResolver(),Settings.Secure.LOCATION_MODE);
            if (off==0){
                showGPSDiabledDialog();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }







        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:1050"));
                try {
                    getContext().startActivity(intent);
                }
                catch (SecurityException e){
                    e.printStackTrace();
                }
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,alertDataForms.newInstance(token)).addToBackStack(null).commit();
            }
        });

        return AV;
    }

    public void showGPSDiabledDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("GPS отключён!");
        builder.setMessage("GPS отключён, чтобы использовать все возможности программы пожалуйста включите GPS устройство");
        builder.setPositiveButton("Включить GPS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_ENABLE_REQUEST);
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog mGPSDialog = builder.create();
        mGPSDialog.show();
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == GPS_ENABLE_REQUEST)
        {
            showGPSDiabledDialog();
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
