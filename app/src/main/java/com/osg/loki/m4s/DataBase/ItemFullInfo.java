package com.osg.loki.m4s.DataBase;

import io.realm.RealmObject;

/**
 * Created by ergas on 1/12/2018.
 */

public class ItemFullInfo extends RealmObject{
    private String content;
    private int img,id;

    public ItemFullInfo() {
    }

    public ItemFullInfo(int id,String content, int img ) {
        this.content = content;
        this.img = img;

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
