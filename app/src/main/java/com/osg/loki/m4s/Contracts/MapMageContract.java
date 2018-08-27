package com.osg.loki.m4s.Contracts;

import com.osg.loki.m4s.MvpPresenter;
import com.osg.loki.m4s.MvpView;
import com.osg.loki.m4s.Tools.State;

import java.util.ArrayList;

/**
 * Created by ergas on 1/17/2018.
 */

public interface MapMageContract {
    interface View extends MvpView{
        void setPolygons(ArrayList<State> states);
        void setMarkers();

    }
    interface Presenter extends MvpPresenter<View>{
        void loadItemList();
    }
}
