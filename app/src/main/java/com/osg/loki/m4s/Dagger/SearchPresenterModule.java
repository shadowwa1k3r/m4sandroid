package com.osg.loki.m4s.Dagger;

import android.support.annotation.NonNull;

import com.osg.loki.m4s.Presenter.SearchPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ergas on 2/28/2018.
 */
@Module
public class SearchPresenterModule {
    @Provides
    @NonNull
    @Singleton
    public SearchPresenter provideSearchPresenter(){
        return new SearchPresenter();
    }

}
