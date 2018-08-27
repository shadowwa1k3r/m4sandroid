package com.osg.loki.m4s.Dagger;

import android.support.annotation.NonNull;

import com.osg.loki.m4s.Presenter.SubItemFullInfoPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ergas on 1/12/2018.
 */
@Module
public class SubItemFullInfoPresenterModule  {
    @Provides
    @NonNull
    @Singleton
    public SubItemFullInfoPresenter provideSubItemFullInfoPresenter(){
        return new SubItemFullInfoPresenter();
    }
}
