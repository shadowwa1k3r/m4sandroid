package com.osg.loki.m4s.Dagger;

import android.support.annotation.NonNull;

import com.osg.loki.m4s.Presenter.NewTestPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NewTestPresenterModule {
    @Provides
    @NonNull
    @Singleton
    public NewTestPresenter provideNewTestPresenter(){
        return new NewTestPresenter();
    }
}
