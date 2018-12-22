package com.osg.loki.m4s.Model;

import io.realm.RealmObject;

/**
 * Created by ergas on 8/28/2018.
 */

public class Wikimodel extends RealmObject {
    private String title, content,last_modified,image,lang,cat;
    private int id;


    public Wikimodel() {
    }

    public Wikimodel(int id,String title, String content, String image, String last_modified,String lang,String cat) {
        this.content = content;
        this.lang = lang;
        this.title=title;
        this.last_modified = last_modified;
        this.image = image;
        this.id = id;
        this.cat = cat;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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
