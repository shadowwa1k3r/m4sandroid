package com.osg.loki.m4s.View;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.osg.loki.m4s.Contracts.TestPageContract;
import com.osg.loki.m4s.Dagger.App;
import com.osg.loki.m4s.DataBase.Test;
import com.osg.loki.m4s.Presenter.TestPagePresenter;
import com.osg.loki.m4s.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestPageView extends Fragment implements TestPageContract.View{

    private static final String ARG_PARAM1 = "param1";


    private int test_id;

    @Inject
    TestPagePresenter mPresenter;
    @BindView(R.id.test_info)
    LinearLayout test_info;
    @BindView(R.id.test_begin)
    LinearLayout test_begin;
    @BindView(R.id.test_results)
    RelativeLayout test_results;

    @BindView(R.id.t_title)
    TextView test_title;
    @BindView(R.id.q_count)
    TextView q_count;
    @BindView(R.id.test_description)
    TextView test_description;
    @BindView(R.id.start)
    Button start;

    @BindView(R.id.q_current)
    TextView q_current;
    @BindView(R.id.q_all)
    TextView q_all;
    @BindView(R.id.q_content)
    TextView q_content;
    @BindView(R.id.a1)
    CheckBox a1;
    @BindView(R.id.a2)
    CheckBox a2;
    @BindView(R.id.a3)
    CheckBox a3;
    @BindView(R.id.a4)
    CheckBox a4;
    @BindView(R.id.a5)
    CheckBox a5;
    @BindView(R.id.next)
    Button next;

    @BindView(R.id.result_perc)
    TextView res_perc;
    @BindView(R.id.q_count_res)
    TextView q_count_res;
    @BindView(R.id.q_correct)
    TextView q_correct;
    @BindView(R.id.q_wrong)
    TextView q_wrong;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.konec)
    Button konec;

    private List<CheckBox> answers;



    public TestPageView() {
        // Required empty public constructor
    }


    public static TestPageView newInstance(int test_id) {
        TestPageView fragment = new TestPageView();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, test_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            test_id= getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View TestPage = inflater.inflate(R.layout.test_page,container,false);
        ButterKnife.bind(this,TestPage);
        test_begin.setVisibility(View.INVISIBLE);
        test_results.setVisibility(View.INVISIBLE);
        App.getComponent().inject(this);

        answers = new ArrayList<>();
        answers.add(a1);
        answers.add(a2);
        answers.add(a3);
        answers.add(a4);
        answers.add(a5);

        mPresenter.attachView(this);

        mPresenter.loaddata(test_id);

        mPresenter.viewIsReady();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aws="";
                for (int i=0;i<answers.size();i++) {
                    if (answers.get(i).isChecked()) {
                         aws+=i;

                    }
                }
                if (aws.isEmpty()||aws.equals("")){
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater1 = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.not_selected_alert,null);
                    dialogBuilder.setView(dialogView);
                    Button ok = (Button)dialogView.findViewById(R.id.ok);
                    final AlertDialog alertDialog = dialogBuilder.create();
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
                else {
                    mPresenter.onAnswerSelected(aws);
                }
                if (next.getText()=="Следующий") {


                    mPresenter.nextQ();

                }

                else {
                    /*for (int i=0;i<answers.size();i++) {
                        if (answers.get(i).isChecked()) {
                            mPresenter.onAnswerSelected(i+1);
                        }
                    }*/
                    mPresenter.showResult();

                }
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    mPresenter.nextQ();

                    test_info.setVisibility(View.INVISIBLE);
                    test_begin.setVisibility(View.VISIBLE);


            }
        });
        konec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });



        return TestPage;
    }

    @Override
    public void setData(Test test,int cnt) {
        test_title.setText(test.getTest_name());
        q_count.setText(cnt+" вопросов");
        test_description.setText(test.getTest_description());
        next.setText("Следующий");

    }

    @Override
    public void setQuestion(Test test,int cur,int cnt) {
        q_current.setText(cur+" - вопрос");
        q_all.setText(cnt+"");
        q_content.setText(test.getQ_content());
        a1.setText(test.getA1());
        a1.setChecked(false);
        a2.setText(test.getA2());
        a2.setChecked(false);
        a3.setText(test.getA3());
        a3.setChecked(false);
        a4.setText(test.getA4());
        a4.setChecked(false);
        a5.setText(test.getA5());
        a5.setChecked(false);


    }

    @Override
    public void testEnd() {
        next.setText("Закончить");
    }

    @Override
    public void showResult(float perc, int count,int cor_count,int wrong_count) {
        test_begin.setVisibility(View.INVISIBLE);
        test_info.setVisibility(View.INVISIBLE);
        test_results.setVisibility(View.VISIBLE);
        res_perc.setText("Ваш результать: "+String.valueOf(perc)+"%");
        q_count_res.setText("Кол-во вопросов: "+String.valueOf(count));

        q_correct.setText(String.valueOf(cor_count)+" правильно");
        q_wrong.setText(String.valueOf(wrong_count)+" неправильно");
    }

}

