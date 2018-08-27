package com.osg.loki.m4s.Contracts;

import com.osg.loki.m4s.MvpPresenter;
import com.osg.loki.m4s.MvpView;
import com.osg.loki.m4s.SubItemPageAdapter;

/**
 * Created by ergas on 1/11/2018.
 */

public interface SubItemPageContract {
    interface View extends MvpView{

        void setAdapter(SubItemPageAdapter adapter);
        void setTitle(String title);
        void close();
    }
    interface Presenter extends MvpPresenter<View>{
        String[] onItemSelected(int position);
        void loadItemList(int position);
    }
}
