package com.osg.loki.m4s.Tools;

import io.realm.RealmObject;

/**
 * Created by ergas on 1/16/2018.
 */

public class State extends RealmObject {
    private String src,name,fillColor;
    private int strokeColor;

    public State(){

    }
    public State(String src,String name,String fillColor,int strokeColor){
        this.src=src;
        this.name=name;
        this.fillColor=fillColor;
        this.strokeColor=strokeColor;

    }
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }
}
