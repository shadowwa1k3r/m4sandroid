package com.osg.loki.m4s.Presenter;

import com.osg.loki.m4s.Contracts.NewTestContract;
import com.osg.loki.m4s.DataBase.NewTest;
import com.osg.loki.m4s.Model.MainPageModel;
import com.osg.loki.m4s.PresenterBase;

import java.util.ArrayList;

import io.realm.Realm;

public class NewTestPresenter extends PresenterBase<NewTestContract.View> implements NewTestContract.Presenter {
    private ArrayList<NewTest> testList = new ArrayList<>();
    private MainPageModel db = new MainPageModel(Realm.getDefaultInstance());
    private Integer correctCount=0,wrongCount=0,current=0;
    @Override
    public void viewIsReady() {

    }

    @Override
    public void loadData(String lang) {
        testList = db.getTestList(lang);
        getView().setData(testList.get(current),testList.size());
    }

    @Override
    public void onAnswerSelected(String answer) {

    }

    @Override
    public void nextQ(String prevAnswer) {
        current++;
        if (current-1 == testList.size()){
            getView().testEnd();
        }else {
            if (testList.get(current-1).getAnswer().equals(prevAnswer)) {
                correctCount++;
            } else {
                wrongCount++;
            }
            if (current == testList.size()) {
                getView().testEnd();
            }else {
                getView().setQuestion(testList.get(current), testList.size(), current);
            }
        }
    }

    @Override
    public void prepareResult() {
        float divide = new Float(correctCount)/new Float(testList.size());
        float percentage = divide*100;
        getView().showResult(percentage,testList.size(),correctCount,wrongCount);
    }
}
