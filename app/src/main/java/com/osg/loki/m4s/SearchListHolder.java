package com.osg.loki.m4s;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.osg.loki.m4s.View.SearchHolderView;

/**
 * Created by ergas on 2/28/2018.
 */

public class SearchListHolder extends RecyclerView.ViewHolder implements SearchHolderView {
    TextView header;
    TextView footer;
    ImageView icon;

    public SearchListHolder(View itemView){
        super(itemView);
        header=(TextView)itemView.findViewById(R.id.header2);
        footer=(TextView)itemView.findViewById(R.id.footer);
        icon=(ImageView)itemView.findViewById(R.id.itemimg2);
    }

    @Override
    public void setHeader(String header) {
        this.header.setText(header);
    }

    @Override
    public void setFooter(String footer) {
        this.footer.setText(Html.fromHtml("<small>"+footer+"</small>"));
    }

    @Override
    public void setIcon(int icon) {
        try{
            this.icon.setImageResource(icon);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
