package com.osg.loki.m4s.DataBase;

import io.realm.RealmObject;

/**
 * Created by ergas on 1/25/2018.
 */

public class Test extends RealmObject{
    private int test_id;
    private String test_name;
    private String test_description;
    private int q_id;
    private String q_content;
    private String a1;
    private String a2;
    private String a3;
    private String a4;
    private String a5;
    private int a;

    public Test() {
    }

    public Test(int test_id, String test_name, String test_description, int q_id, String q_content, String a1, String a2 , String a3, String a4,String a5, int a){
        this.test_id=test_id;
        this.test_name=test_name;
        this.test_description=test_description;
        this.q_id=q_id;
        this.q_content=q_content;
        this.a1=a1;
        this.a2=a2;
        this.a3=a3;
        this.a4=a4;
        this.a5=a5;
        this.a=a;
    }

    public String getTest_description() {
        return test_description;
    }

    public void setTest_description(String test_description) {
        this.test_description = test_description;
    }

    public int getTest_id() { return test_id; }

    public void setTest_id(int test_id) { this.test_id = test_id; }

    public String getTest_name() { return test_name; }

    public void setTest_name(String test_name) { this.test_name = test_name; }

    public int getQ_id() { return q_id; }

    public void setQ_id(int q_id) { this.q_id = q_id; }

    public String getQ_content() { return q_content; }

    public void setQ_content(String q_content) { this.q_content = q_content; }

    public String getA1() { return a1; }

    public void setA1(String a1) { this.a1 = a1; }

    public String getA2() { return a2; }

    public void setA2(String a2) { this.a2 = a2; }

    public String getA3() { return a3; }

    public void setA3(String a3) { this.a3 = a3; }

    public String getA4() { return a4; }

    public void setA4(String a4) { this.a4 = a4; }

    public int getA() { return a; }

    public void setA(int a) { this.a = a; }

    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }
}
