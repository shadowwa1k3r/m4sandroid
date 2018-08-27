package com.osg.loki.m4s.Presenter;

import com.osg.loki.m4s.Contracts.SearchContract;
import com.osg.loki.m4s.Model.MainPageModel;
import com.osg.loki.m4s.Model.ResultDataModel;
import com.osg.loki.m4s.PresenterBase;
import com.osg.loki.m4s.SearchAdapter;
import com.osg.loki.m4s.View.SearchHolderView;

import java.util.List;

import io.realm.Realm;

/**
 * Created by ergas on 2/28/2018.
 */

public class SearchPresenter extends PresenterBase<SearchContract.View> implements SearchContract.Presenter {


    private SearchAdapter mAdapter;
    private List<ResultDataModel> resultList;
    private MainPageModel baza = new MainPageModel(Realm.getDefaultInstance());


    @Override
    public void viewIsReady() {

        try{
            getView().setAdapter(mAdapter);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void close() {

    }

    @Override
    public void onSearchTextChanged(String text) {
        resultList=baza.getResult(text);
        mAdapter=new SearchAdapter(this);
        getView().setAdapter(mAdapter);
    }

    public void onBindSubItemPageHolderViewAtPosition(int position, SearchHolderView view) {
        view.setHeader(String.valueOf(resultList.get(position).getId()));
        view.setFooter(resultList.get(position).getFooter());
        view.setIcon(resultList.get(position).getIcon());
    }
    public int getListItemsCount(){
        return resultList.size();
    }
}
