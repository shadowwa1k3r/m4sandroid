package com.osg.loki.m4s;

/**
 * Created by ergas on 1/9/2018.
 */

public abstract class PresenterBase <T extends MvpView> implements MvpPresenter<T> {
    private T view;

    @Override
    public void attachView(T mvpView){
        view=mvpView;
    }

    @Override
    public void detachView(){
        view=null;
    }

    public T getView(){
        return view;
    }

    protected boolean isViewAttached(){
        return view!=null;
    }


    @Override
    public void destroy(){
        
    }
}
