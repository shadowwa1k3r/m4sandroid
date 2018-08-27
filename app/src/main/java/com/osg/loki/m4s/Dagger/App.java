package com.osg.loki.m4s.Dagger;

import android.app.Application;

/**
 * Created by ergas on 1/10/2018.
 */

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent(){
        component=buildComponent();

        return component;
    }
    @Override
    public void onCreate(){
        super.onCreate();


    }

    protected static AppComponent buildComponent(){
        return DaggerAppComponent.builder()
                .mainPagePresenterModule(new MainPagePresenterModule())
                .subItemPagePresenterModule(new SubItemPagePresenterModule())
                .subItemFullInfoPresenterModule(new SubItemFullInfoPresenterModule())
                .mapPagePresenterModule(new MapPagePresenterModule())
                .testPagePresenterModule(new TestPagePresenterModule())
                .searchPresenterModule(new SearchPresenterModule())
                .build();
    }
}
