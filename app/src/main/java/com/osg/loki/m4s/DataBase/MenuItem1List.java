package com.osg.loki.m4s.DataBase;

import io.realm.RealmObject;

/**
 * Created by ergas on 1/11/2018.
 */

public class MenuItem1List extends RealmObject {
    private String text;
    private int img;
    private int id;

    public MenuItem1List(int id ,String text,int img){
        this.text=text;
        this.img=img;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MenuItem1List(){

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
