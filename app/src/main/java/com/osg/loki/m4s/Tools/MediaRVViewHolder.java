package com.osg.loki.m4s.Tools;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.osg.loki.m4s.R;

/**
 * Created by ergas on 7/3/2018.
 */

public class MediaRVViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView image;
    public MediaRVViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        image = (ImageView) itemView.findViewById(R.id.media_image);
    }

    @Override
    public void onClick(View view) {
        Log.i("vh", "onClick: "+getAdapterPosition());
    }
}
