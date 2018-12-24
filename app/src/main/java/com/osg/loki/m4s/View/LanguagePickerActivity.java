package com.osg.loki.m4s.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.osg.loki.m4s.R;
import com.osg.loki.m4s.SplashScreenActivity;

public class LanguagePickerActivity extends LocalizationActivity implements View.OnClickListener {
    private Button ru;
    private Button uz;
    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_picker);
        ru = findViewById(R.id.russian);
        uz = findViewById(R.id.uzbek);
        ru.setOnClickListener(this);
        uz.setOnClickListener(this);
        mContext = super.getApplicationContext();
    }

    @Override
    public void onClick(View view) {
//        String local = "default";
        switch (view.getId()) {

            case R.id.russian:
//                local = "ru";
                setLanguage("en");
                break;
            case R.id.uzbek:
                setLanguage("uz");
//                local = "uz";
                break;
            default:
                break;
        }
        startActivity(new Intent(this,SplashScreenActivity.class));
        finish();
    }
}
