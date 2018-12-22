package com.osg.loki.m4s.Contracts;

import com.osg.loki.m4s.DataBase.NewTest;
import com.osg.loki.m4s.MvpPresenter;
import com.osg.loki.m4s.MvpView;

public interface NewTestContract {
    interface View extends MvpView{
        public void setData(NewTest test,int count);
        public void setQuestion(NewTest test,int count,int current);
        public void testEnd();
        public void showResult(float percentage,int count,int correct_count,int wrong_count);

    }
    interface Presenter extends MvpPresenter<View>{
        public void loadData(String lang);
        public void onAnswerSelected(String answer);
        public void nextQ(String prevAnswer);
        public void prepareResult();
    }
}
