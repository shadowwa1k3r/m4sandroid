package com.osg.loki.m4s.View;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.osg.loki.m4s.Contracts.SubItemPageContract;
import com.osg.loki.m4s.Dagger.App;
import com.osg.loki.m4s.Presenter.SubItemPagePresenter;
import com.osg.loki.m4s.R;
import com.osg.loki.m4s.SubItemPageAdapter;
import com.osg.loki.m4s.Tools.RecyclerItemClickListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubItemPageView extends Fragment implements SubItemPageContract.View {

    private static final String ARG_PARAM1 = "param1";

    @Inject
    SubItemPagePresenter mPresenter;

    private String[] id;


    private int current;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.sub_item_list)
    RecyclerView list;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.TitleText)
    TextView mTextView;
    private String cat;

    public SubItemPageView() {
        // Required empty public constructor
    }

    public static SubItemPageView newInstance(int current) {
        SubItemPageView fragment = new SubItemPageView();
        Bundle args = new Bundle();

        args.putInt(ARG_PARAM1, current);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            current = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View SubItemPage=inflater.inflate(R.layout.sub_item_page,container,false);

        ButterKnife.bind(this,SubItemPage);

        if (current==4){
            mTextView.setText(R.string.enciklopedia);
            cat = "Энциклопедия";
        } else if (current==0){
            mTextView.setText(R.string.chs);
            cat = "ЧС: Как действовать?";
        } else if (current==6) {
            mTextView.setText(R.string.lb);
            cat = "Личное безопасность";
        }else if (current==2){
            mTextView.setText(R.string.perviy_pomosh);
            cat = "Первая помощь";
        }
        mLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(mLayoutManager);
        list.setHasFixedSize(true);
        App.getComponent().inject(this);
        mPresenter.attachView(this);
        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_LANG = "lang";
        final SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        System.out.println(prefs.getString(PREF_LANG,"-1"));
        mPresenter.loadItemList(current,prefs.getString(PREF_LANG,"ru"),cat);
        mPresenter.viewIsReady();



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).replace(R.id.contentContainer,new SearchingView()).addToBackStack(null).commit();
                   }
        });


        list.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (current==5){
                    switch (position){
                        case 0:break;
                        case 1:getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.contentContainer,new SettingsView()).addToBackStack(null).commit();break;
                        case 2:break;
                        case 3:break;
                        case 4:break;
                        case 5:break;
                        case 6:break;
                    }
                }
                else if (current==3){
                    getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.contentContainer,TestPageView.newInstance(position+1)).addToBackStack(null).commit();
                }
                else {


                    id = mPresenter.onItemSelected(position);
                    getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.contentContainer, SubItemFullInfo.newInstance(Integer.valueOf(id[0]), id[1])).addToBackStack(null).commit();

                    //MainActivity.ImageViewAnimatedChange(getContext(), (ImageView)inflater.inflate(R.layout.material_main,null,false).findViewById(R.id.Logo), BitmapFactory.decodeResource(getResources(),R.drawable.ic_cross));
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        return SubItemPage;
    }

    @Override
    public void setAdapter(SubItemPageAdapter adapter){
        list.setAdapter(adapter);
    }
    @Override
    public void close(){

    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }
    @Override
    public void setTitle(String title){

    }


}
