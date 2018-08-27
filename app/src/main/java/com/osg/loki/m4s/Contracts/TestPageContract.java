package com.osg.loki.m4s.Contracts;

import com.osg.loki.m4s.DataBase.Test;
import com.osg.loki.m4s.MvpPresenter;
import com.osg.loki.m4s.MvpView;

/**
 * Created by ergas on 1/25/2018.
 */

public interface TestPageContract {
    interface View extends MvpView{
        public void setData(Test test,int cnt);
        public void setQuestion(Test test,int cur,int cnt);
        public void testEnd();
        public void showResult(float perc,int count,int cor_count,int wrong_count);
    }
    interface Presenter extends MvpPresenter<View>{
        public void loaddata(int test_id);
        public void onAnswerSelected(String answer);
        public void nextQ();
        public void showResult();


    }
}
