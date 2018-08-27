package com.osg.loki.m4s.Tools;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.osg.loki.m4s.DataBase.ItemFullInfo;
import com.osg.loki.m4s.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ergas on 2/24/2018.
 */

public class SearchItemView extends RelativeLayout {
    @BindView(R.id.itemimg)
    ImageView logo;
    @BindView(R.id.header)
    TextView title;

    public SearchItemView(Context context){
        super(context);
        init(context);
    }
    private void init(Context context){
        inflate(context,R.layout.subitemmodel,this);
        ButterKnife.bind(this);
    }
    public void bind(ItemFullInfo item){
        logo.setImageResource(item.getImg());
        title.setText(item.getContent());

    }
}
