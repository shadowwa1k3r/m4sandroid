package com.osg.loki.m4s;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.osg.loki.m4s.Presenter.SubItemPagePresenter;

/**
 * Created by ergas on 1/11/2018.
 */

public class SubItemPageAdapter extends RecyclerView.Adapter<SubItemPageListHolder> {
    private final SubItemPagePresenter mPresenter;
    public SubItemPageAdapter(SubItemPagePresenter presenter){
        this.mPresenter=presenter;
    }
    @Override
    public SubItemPageListHolder onCreateViewHolder(ViewGroup parent,int viewType){
        return new SubItemPageListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subitemmodel,parent,false));
    }

    @Override
    public void onBindViewHolder(SubItemPageListHolder holder, int position){
        mPresenter.onBindSubItemPageHolderViewAtPosition(position,holder);
    }
    @Override
    public int getItemCount(){
        return mPresenter.getListItemsCount();
    }
}
