package com.osg.loki.m4s.Dagger;

import com.osg.loki.m4s.View.MainActivity;
import com.osg.loki.m4s.View.MapView;
import com.osg.loki.m4s.View.NewTestView;
import com.osg.loki.m4s.View.SearchingView;
import com.osg.loki.m4s.View.SubItemFullInfo;
import com.osg.loki.m4s.View.SubItemPageView;
import com.osg.loki.m4s.View.TestPageView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ergas on 1/10/2018.
 */

@Component(modules = {SearchPresenterModule.class,MainPagePresenterModule.class,SubItemPagePresenterModule.class,SubItemFullInfoPresenterModule.class,MapPagePresenterModule.class,TestPagePresenterModule.class,NewTestPresenterModule.class})

@Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(SubItemPageView subItemPageView);
    void inject(SubItemFullInfo subItemFullInfo);
    void inject(MapView mapView);
    void inject(TestPageView testPageView);
    void inject(SearchingView searchView);
    void inject(NewTestView newTestView);

}
