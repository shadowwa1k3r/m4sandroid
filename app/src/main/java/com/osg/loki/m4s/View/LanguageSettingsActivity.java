package com.osg.loki.m4s.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.osg.loki.m4s.R;

public class LanguageSettingsActivity extends LocalizationActivity {

    private RadioButton rus,uzb;
    private Button make;
    private String lang;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_settings);
        rus = findViewById(R.id.ru);
        uzb = findViewById(R.id.uzb);
        make = findViewById(R.id.change);
        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_TOKEN = "token";
        final String PREF_LANG = "lang";
        final String DOESNT_EXIST = "-1";
        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if ((getCurrentLanguage().getLanguage().equals("en"))||(getCurrentLanguage().getLanguage().equals("ru"))){
            rus.setChecked(true);
            lang = "ru";
        } else {
            uzb.setChecked(true);
            lang = "uz";
        }
        uzb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    rus.setChecked(false);
                    lang = "uz";
                }
            }
        });
        rus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    uzb.setChecked(false);
                    lang = "ru";
                }
            }
        });
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage(lang);
                prefs.edit().putString(PREF_LANG,lang).apply();
                NewSettings.fa.recreate();
                finish();
            }
        });
    }
}
