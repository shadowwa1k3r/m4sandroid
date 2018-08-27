package com.osg.loki.m4s.View;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.osg.loki.m4s.Contracts.SearchContract;
import com.osg.loki.m4s.Dagger.App;
import com.osg.loki.m4s.Presenter.SearchPresenter;
import com.osg.loki.m4s.R;
import com.osg.loki.m4s.SearchAdapter;
import com.osg.loki.m4s.Tools.RecyclerItemClickListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchingView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchingView extends Fragment implements SearchContract.View{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.search_result)
    RecyclerView resultList;
    @BindView(R.id.search_text)
    EditText searchText;


    @Inject
    SearchPresenter mPresenter;
    public SearchingView() {
        // Required empty public constructor
    }
    public static SearchingView newInstance(String param1, String param2) {
        SearchingView fragment = new SearchingView();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View sv = inflater.inflate(R.layout.search_lay,container,false);
        ButterKnife.bind(this,sv);

        mLayoutManager=new LinearLayoutManager(getContext());
        resultList.setLayoutManager(mLayoutManager);
        App.getComponent().inject(this);
        mPresenter.attachView(this);
        //mPresenter.onSearchTextChanged("");
        mPresenter.viewIsReady();
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ( (i == EditorInfo.IME_ACTION_DONE) ){
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(sv.getWindowToken(), 0);


                    return true;
                }
                else{
                    return false;
                }

            }
        });
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPresenter.onSearchTextChanged(charSequence.toString());
                Log.d("search", "onTextChanged: "+charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        resultList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), resultList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView txt = (TextView)view.findViewById(R.id.header2);
                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).replace(R.id.contentContainer,SubItemFullInfo.newInstance(Integer.valueOf(txt.getText().toString()),"")).addToBackStack(null).commit();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


        return sv;
    }

    @Override
    public void setAdapter(SearchAdapter adapter) {
        adapter.notifyDataSetChanged();
        resultList.setAdapter(adapter);

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.close();

    }
}
