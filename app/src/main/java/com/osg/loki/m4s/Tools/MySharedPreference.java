package com.osg.loki.m4s.Tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ergas on 2/7/2018.
 */

public class MySharedPreference {
  public static final String PREFS_NAME = "MY_PREF";
  public static final String PREFS_KEY = "STATUS";

  public MySharedPreference(){
      super();
  }

  public void save(Context context,String text){
      SharedPreferences settings;
      SharedPreferences.Editor editor;

      settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
      editor = settings.edit();

      editor.putString(PREFS_KEY,text);

      editor.commit();
  }

  public String getValue(Context context){
      SharedPreferences settings;
      String text;
      settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
      text=settings.getString(PREFS_KEY,null);
      return text;
  }

}
