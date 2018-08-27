package com.osg.loki.m4s;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.osg.loki.m4s.Presenter.SearchPresenter;

/**
 * Created by ergas on 2/28/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchListHolder>{
    private final SearchPresenter mPresenter;
    public SearchAdapter(SearchPresenter presenter){
        mPresenter=presenter;
    }

    @Override
    public SearchListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item,parent,false));
    }

    @Override
    public void onBindViewHolder(SearchListHolder holder, int position) {
        mPresenter.onBindSubItemPageHolderViewAtPosition(position,holder);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getListItemsCount();
    }
}
