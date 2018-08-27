package com.osg.loki.m4s.Contracts;

import com.osg.loki.m4s.MvpPresenter;
import com.osg.loki.m4s.MvpView;
import com.osg.loki.m4s.SearchAdapter;

/**
 * Created by ergas on 2/28/2018.
 */

public interface SearchContract {
    interface View extends MvpView{
        void setAdapter(SearchAdapter adapter);
    }
    interface Presenter extends MvpPresenter<View>{
        void close();
        void onSearchTextChanged(String text);
    }
}
