package com.osg.loki.m4s.Dagger;

import android.support.annotation.NonNull;

import com.osg.loki.m4s.Presenter.MapPagePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ergas on 1/18/2018.
 */
@Module
public class MapPagePresenterModule {
    @Provides
    @NonNull
    @Singleton
    public MapPagePresenter provideMapPagePresenter(){
        return new MapPagePresenter();
    }
}
