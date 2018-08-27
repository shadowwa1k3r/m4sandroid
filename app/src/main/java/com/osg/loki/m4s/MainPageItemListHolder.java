package com.osg.loki.m4s;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.osg.loki.m4s.View.MainPageHolderView;

/**
 * Created by ergas on 1/9/2018.
 */

public class MainPageItemListHolder extends RecyclerView.ViewHolder implements MainPageHolderView {

     TextView title;
     ImageView icon;

    public MainPageItemListHolder(View itemView){
        super(itemView);
        title=(TextView)itemView.findViewById(R.id.header);
        icon=(ImageView)itemView.findViewById(R.id.itemimg);

    }


    @Override
    public void setText(String text){
        title.setText(text);
    }
    @Override
    public void setIcon(int src){

        switch (src){
            case 0:icon.setImageResource(R.drawable.ic_flame); break;
            case 1:icon.setImageResource(R.drawable.ic_hospital);break;
            case 2:icon.setImageResource(R.drawable.ic_gps);break;
            case 3:icon.setImageResource(R.drawable.ic_list);break;
            case 4:icon.setImageResource(R.drawable.ic_books);break;
            case 5:icon.setImageResource(R.drawable.ic_information);break;
            default:icon.setImageResource(R.drawable.ic_question_mark_button);break;
        }


    }

}
