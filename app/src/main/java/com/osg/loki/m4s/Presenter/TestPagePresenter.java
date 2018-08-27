package com.osg.loki.m4s.Presenter;

import com.osg.loki.m4s.Contracts.TestPageContract;
import com.osg.loki.m4s.DataBase.Result;
import com.osg.loki.m4s.DataBase.Test;
import com.osg.loki.m4s.Model.MainPageModel;
import com.osg.loki.m4s.PresenterBase;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by ergas on 1/25/2018.
 */

public class TestPagePresenter extends PresenterBase<TestPageContract.View> implements TestPageContract.Presenter {

    private ArrayList<Test> mTests = new ArrayList<>();
    private MainPageModel baza = new MainPageModel(Realm.getDefaultInstance());
    private ArrayList<Integer> answers = new ArrayList<>();
    private ArrayList<Integer> right_answers = new ArrayList<>();
    private Result mResult ;
    private int cur_q=0,cur_t=1,cor_count=0,wrong_count=0;

    @Override
    public void loaddata(int test_id) {
       // mTests=baza.getTest(test_id);

        cur_t=test_id;
    }
    @Override
    public void showResult() {
        check(cor_count,wrong_count);
        getView().showResult(mResult.getPercentage(),mResult.getCount(),mResult.getCor_count(),mResult.getWrong_count());
    }
    private void check(int correct, int wrong){
        mResult = new Result(mTests.size(),correct,wrong);
    }
    @Override
    public void onAnswerSelected(String answer) {

        for (int i = 0; i <answer.length() ; i++) {
            answers.add(Integer.valueOf(answer.charAt(i)));
        }
        String rights = String.valueOf(mTests.get(cur_q-1).getA());
        for (int i = 0; i < rights.length(); i++) {
            right_answers.add(Integer.valueOf(rights.charAt(i)));
        }
        for (int i = 0; i <right_answers.size() ; i++) {
            for (int j = 0; j <answers.size() ; j++) {
                if (right_answers.get(i).equals(answers.get(j))){
                    cor_count++;
                }
                else {
                    wrong_count++;
                }
            }
        }
    }
    @Override
    public void nextQ() {
        if (cur_q!=mTests.size()) {

            getView().setQuestion(mTests.get(cur_q), cur_q+1, mTests.size());
            cur_q++;
        }
        if(cur_q==mTests.size())getView().testEnd();
    }
    @Override
    public void viewIsReady() {
        getView().setData(mTests.get(cur_q),mTests.size());
    }
}
