package com.osg.loki.m4s.Dagger;

import android.support.annotation.NonNull;

import com.osg.loki.m4s.Presenter.SubItemPagePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ergas on 1/11/2018.
 */
@Module
public class SubItemPagePresenterModule {

    @Provides
    @NonNull
    @Singleton
    public SubItemPagePresenter provideSubitemPagePresenter(){
        return new SubItemPagePresenter();
    }
}
