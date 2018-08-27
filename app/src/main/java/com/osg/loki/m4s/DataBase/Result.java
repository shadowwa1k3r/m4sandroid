package com.osg.loki.m4s.DataBase;

/**
 * Created by ergas on 1/29/2018.
 */

public class Result {
    private float percentage;
    private int count;
    private int cor_count;
    private int wrong_count;

    public Result(int count, int cor_count, int wrong_count) {
        this.count = count;
        this.cor_count = cor_count;
        this.wrong_count = wrong_count;
        this.percentage=cor_count*100/count;
    }

    public float getPercentage() {

        return percentage;
    }


    public int getCount() {
        return count;
    }



    public int getCor_count() {
        return cor_count;
    }


    public int getWrong_count() {
        return wrong_count;
    }


}
