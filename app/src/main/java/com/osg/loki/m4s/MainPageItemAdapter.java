package com.osg.loki.m4s;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.osg.loki.m4s.Presenter.MainPagePresenter;

/**
 * Created by ergas on 1/9/2018.
 */

public class MainPageItemAdapter extends RecyclerView.Adapter<MainPageItemListHolder> {
    private final MainPagePresenter mPresenter;

    public MainPageItemAdapter(MainPagePresenter presenter){
        this.mPresenter=presenter;
    }

    @Override
    public MainPageItemListHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new MainPageItemListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mainpageitemmodel,parent,false));
    }

    @Override
    public void onBindViewHolder(MainPageItemListHolder holder, int position){
        mPresenter.onBindMainPageHolderViewAtPosition(position,holder);
    }

    @Override
    public int getItemCount(){
        return mPresenter.getListItemsCount();
    }



}
