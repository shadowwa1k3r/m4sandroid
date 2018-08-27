package com.osg.loki.m4s.DataBase;

import io.realm.RealmObject;

/**
 * Created by ergas on 1/11/2018.
 */

public class MenuItem4List extends RealmObject {
    private String text;
    private int img,id;

    public MenuItem4List(){}
    public MenuItem4List(int id,String text,int img){
        this.text=text;
        this.id=id;
        this.img=img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
