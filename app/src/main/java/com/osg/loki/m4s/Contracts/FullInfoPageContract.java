package com.osg.loki.m4s.Contracts;

import com.osg.loki.m4s.MvpPresenter;
import com.osg.loki.m4s.MvpView;

/**
 * Created by ergas on 1/12/2018.
 */

public interface FullInfoPageContract {
    interface View extends MvpView{
        void setContent(String content,int img);

    }
    interface Presenter extends MvpPresenter<View>{
        void loadInfo(int id);
    }


}
