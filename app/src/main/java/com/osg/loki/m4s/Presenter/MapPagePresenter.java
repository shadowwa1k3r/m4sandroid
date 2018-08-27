package com.osg.loki.m4s.Presenter;

import com.osg.loki.m4s.Contracts.MapMageContract;
import com.osg.loki.m4s.Model.MainPageModel;
import com.osg.loki.m4s.PresenterBase;
import com.osg.loki.m4s.Tools.State;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by ergas on 1/17/2018.
 */

public class MapPagePresenter extends PresenterBase<MapMageContract.View> implements MapMageContract.Presenter {

    private ArrayList<State> states=new ArrayList<>();

    private MainPageModel baza = new MainPageModel(Realm.getDefaultInstance());


    @Override
    public void viewIsReady() {
        getView().setPolygons(states);
        getView().setMarkers();
    }

    @Override
    public void loadItemList() {
        states=baza.getStates();
    }
}
