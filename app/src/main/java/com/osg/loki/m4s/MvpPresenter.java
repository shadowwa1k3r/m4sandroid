package com.osg.loki.m4s;

/**
 * Created by ergas on 1/9/2018.
 */

public interface MvpPresenter <V extends MvpView> {

    void attachView(V mvpView);

    void viewIsReady();

    void detachView();

    void destroy();
}
