package com.osg.loki.m4s.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.osg.loki.m4s.Contracts.FullInfoPageContract;
import com.osg.loki.m4s.Dagger.App;
import com.osg.loki.m4s.Presenter.SubItemFullInfoPresenter;
import com.osg.loki.m4s.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubItemFullInfo extends Fragment implements FullInfoPageContract.View{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int id;
    private String head;

    @Inject
    SubItemFullInfoPresenter mPresenter;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.content)
    WebView content;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.hidder)
    ExpandableLayout hide;

    @BindView(R.id.images)
    ImageView img;

    public SubItemFullInfo() {
        // Required empty public constructor
    }


    public static SubItemFullInfo newInstance(int id,String head) {
        SubItemFullInfo fragment = new SubItemFullInfo();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putString(ARG_PARAM2,head);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            id= getArguments().getInt(ARG_PARAM1);
            head=getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View FullInfo=inflater.inflate(R.layout.subitemfullinfo,container,false);
        ButterKnife.bind(this,FullInfo);

        App.getComponent().inject(this);

        mPresenter.attachView(this);
        mPresenter.loadInfo(id);
        mPresenter.viewIsReady();




        content.getSettings().setJavaScriptEnabled(true);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return FullInfo;
    }

    @Override
    public void setContent(String content,String img) {
        this.title.setText(head);
        if (!img.equals("")) hide.expand();
        this.img.setImageResource(R.drawable.chemcrash);



        this.content.loadDataWithBaseURL(null,content,"text/html","utf-8",null);
    }
}
