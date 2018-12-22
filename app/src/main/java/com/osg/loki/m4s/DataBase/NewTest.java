package com.osg.loki.m4s.DataBase;

import io.realm.RealmObject;

public class NewTest extends RealmObject {
    private String question,answer1,answer2,answer3,answer4,answer,lang;
    private long id;

    public NewTest() {
    }

    public NewTest(String question, String answer1, String answer2, String answer3, String answer4, String answer, String lang, long id) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer = answer;
        this.lang = lang;
        this.id = id;
    }

    public String getLang() {

        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {

        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
