package com.osg.loki.m4s.Presenter;

import com.osg.loki.m4s.Contracts.FullInfoPageContract;
import com.osg.loki.m4s.Model.MainPageModel;
import com.osg.loki.m4s.PresenterBase;

import io.realm.Realm;

/**
 * Created by ergas on 1/12/2018.
 */

public class SubItemFullInfoPresenter extends PresenterBase<FullInfoPageContract.View> implements FullInfoPageContract.Presenter {

    private MainPageModel baza = new MainPageModel(Realm.getDefaultInstance());
    private String content;
    private int img;

    @Override
    public void viewIsReady() {
        getView().setContent(content,img);
    }

    @Override
    public void loadInfo(int id) {

        content=baza.getItemContent(id).getContent();
        img = baza.getItemContent(id).getImg();
    }
}
