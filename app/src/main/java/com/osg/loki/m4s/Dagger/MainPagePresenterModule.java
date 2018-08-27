package com.osg.loki.m4s.Dagger;

import android.support.annotation.NonNull;

import com.osg.loki.m4s.Presenter.MainPagePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ergas on 1/10/2018.
 */
@Module
public class MainPagePresenterModule {

    @Provides
    @NonNull
    @Singleton
    public MainPagePresenter provideMainPresenter(){
        return new com.osg.loki.m4s.Presenter.MainPagePresenter();
    }
}
