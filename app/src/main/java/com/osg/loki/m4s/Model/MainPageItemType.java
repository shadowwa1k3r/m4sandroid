package com.osg.loki.m4s.Model;

import io.realm.RealmObject;

/**
 * Created by ergas on 1/9/2018.
 */

public class MainPageItemType extends RealmObject{

    private String title;
    private int iconpath;

    public MainPageItemType(String title, int iconpath){
        this.title=title;
        this.iconpath=iconpath;
    }
    public MainPageItemType(){

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconpath() {
        return iconpath;
    }

    public void setIconpath(int iconpath) {
        this.iconpath = iconpath;
    }


}
