package com.osg.loki.m4s.Model;

import io.realm.RealmObject;

/**
 * Created by ergas on 8/28/2018.
 */

public class Wikimodel extends RealmObject {
    private String title, content,last_modified,image;
    private int id;


    public Wikimodel() {
    }

    public Wikimodel(int id,String title, String content, String image, String last_modified) {
        this.content = content;
        this.title=title;
        this.last_modified = last_modified;
        this.image = image;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
