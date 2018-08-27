package com.osg.loki.m4s.Dagger;

import android.support.annotation.NonNull;

import com.osg.loki.m4s.Presenter.TestPagePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ergas on 1/25/2018.
 */
@Module
public class TestPagePresenterModule {

    @Provides
    @NonNull
    @Singleton
    public TestPagePresenter provideTestPagePresenter(){return new TestPagePresenter();}
}
