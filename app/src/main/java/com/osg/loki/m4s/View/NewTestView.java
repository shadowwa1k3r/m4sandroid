package com.osg.loki.m4s.View;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.osg.loki.m4s.Contracts.NewTestContract;
import com.osg.loki.m4s.Dagger.App;
import com.osg.loki.m4s.DataBase.NewTest;
import com.osg.loki.m4s.Presenter.NewTestPresenter;
import com.osg.loki.m4s.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTestView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTestView extends Fragment implements NewTestContract.View,View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Inject
    NewTestPresenter mNewTestPresenter;
    @BindView(R.id.result_percentage)
    TextView percentage;
    @BindView(R.id.correct_count)
    TextView correct;
    @BindView(R.id.question)
    TextView question;
    @BindView(R.id.answer1)
    Button answer1;
    @BindView(R.id.answer2)
    Button answer2;
    @BindView(R.id.answer3)
    Button answer3;
    @BindView(R.id.answer4)
    Button answer4;
    @BindView(R.id.test_start)
    Button startTest;
    @BindView(R.id.test_intro)
    RelativeLayout intro;
    @BindView(R.id.test_page)
    RelativeLayout testPage;
    @BindView(R.id.result)
    RelativeLayout result;

    private String mParam1;
    private String mParam2;


    public NewTestView() {
        // Required empty public constructor
    }


    public static NewTestView newInstance(String param1, String param2) {
        NewTestView fragment = new NewTestView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newTestPage = inflater.inflate(R.layout.fragment_new_test_view, container, false);
        ButterKnife.bind(this,newTestPage);
        App.getComponent().inject(this);
        mNewTestPresenter.attachView(this);



        mNewTestPresenter.viewIsReady();
        startTest.setOnClickListener(this);
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
        return newTestPage;
    }

    @Override
    public void setData(NewTest test, int count) {
        question.setText(test.getQuestion());
        answer1.setText(test.getAnswer1());
        answer2.setText(test.getAnswer2());
        answer3.setText(test.getAnswer3());
        answer4.setText(test.getAnswer4());
    }

    @Override
    public void setQuestion(NewTest test, int count, int current) {
        question.setText(test.getQuestion());
        answer1.setText(test.getAnswer1());
        answer2.setText(test.getAnswer2());
        answer3.setText(test.getAnswer3());
        answer4.setText(test.getAnswer4());

    }

    @Override
    public void testEnd() {
        mNewTestPresenter.prepareResult();

    }

    @Override
    public void showResult(float percentage, int count, int correct_count, int wrong_count) {
        testPage.setVisibility(View.INVISIBLE);
        result.setVisibility(View.VISIBLE);
        this.percentage.setText(getString(R.string.result_perc) +Math.round(percentage)+"%");
        this.correct.setText(getString(R.string.result_cor)+correct_count);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.test_start:
                final String PREFS_NAME = "MyPrefsFile";
                final String PREF_LANG = "lang";
                final SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                mNewTestPresenter.loadData(prefs.getString(PREF_LANG,"ru"));intro.setVisibility(View.INVISIBLE);testPage.setVisibility(View.VISIBLE);break;
            case R.id.answer1:mNewTestPresenter.nextQ(answer1.getText().toString());break;
            case R.id.answer2:mNewTestPresenter.nextQ(answer2.getText().toString());break;
            case R.id.answer3:mNewTestPresenter.nextQ(answer3.getText().toString());break;
            case R.id.answer4:mNewTestPresenter.nextQ(answer4.getText().toString());break;

        }
    }
}
