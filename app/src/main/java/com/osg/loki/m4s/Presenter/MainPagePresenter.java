package com.osg.loki.m4s.Presenter;

import com.osg.loki.m4s.Contracts.MainPageContract;
import com.osg.loki.m4s.MainPageItemAdapter;
import com.osg.loki.m4s.Model.MainPageItemType;
import com.osg.loki.m4s.Model.MainPageModel;
import com.osg.loki.m4s.PresenterBase;
import com.osg.loki.m4s.View.MainPageHolderView;

import java.util.List;

import io.realm.Realm;

/**
 * Created by ergas on 1/9/2018.
 */

public class MainPagePresenter extends PresenterBase<MainPageContract.View> implements MainPageContract.Presenter {
    private MainPageItemAdapter mAdapter;
    //private int position;
    private MainPageModel baza = new MainPageModel(Realm.getDefaultInstance());

    private List<MainPageItemType> mList;

    public MainPagePresenter(){


    }

    @Override
    public void viewIsReady(){
        getView().setAdapter(mAdapter);
    }

    @Override
    public void onItemSelected(int position){

    }
    @Override
    public void loadItemList(){
        mList=baza.getList();
        mAdapter=new MainPageItemAdapter(this);
    }

    public void onBindMainPageHolderViewAtPosition(int position, MainPageHolderView view){
        MainPageItemType listItem = mList.get(position);
        view.setIcon(listItem.getIconpath());
        view.setText(listItem.getTitle());
    }

    public int getListItemsCount(){
        return mList.size();
    }
}
