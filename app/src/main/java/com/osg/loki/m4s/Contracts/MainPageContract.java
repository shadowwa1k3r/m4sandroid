package com.osg.loki.m4s.Contracts;

import com.osg.loki.m4s.MainPageItemAdapter;
import com.osg.loki.m4s.MvpPresenter;
import com.osg.loki.m4s.MvpView;

/**
 * Created by ergas on 1/9/2018.
 */

public interface MainPageContract {
    interface View extends MvpView {

        void setAdapter(MainPageItemAdapter adapter);



        void close();
    }

    interface Presenter extends MvpPresenter<View> {
        void onItemSelected(int position);
        void loadItemList();
    }

}
