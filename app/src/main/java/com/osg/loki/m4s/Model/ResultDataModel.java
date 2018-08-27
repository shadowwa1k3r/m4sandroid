package com.osg.loki.m4s.Model;

/**
 * Created by ergas on 2/28/2018.
 */

public class ResultDataModel {
    private String header,footer;
    private int icon,id;

    public ResultDataModel(String header, String footer, int icon, int id) {
        this.header = header;
        this.footer = footer;
        this.icon = icon;
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
