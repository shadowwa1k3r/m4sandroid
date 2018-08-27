package com.osg.loki.m4s.Tools;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osg.loki.m4s.R;

import java.util.List;

/**
 * Created by ergas on 7/3/2018.
 */

public class MediaRVAdapter extends RecyclerView.Adapter<MediaRVViewHolder> {
    private List<String> itemList;
    private Context mContext;

    public MediaRVAdapter(List<String> itemList, Context context) {
        this.itemList = itemList;
        mContext = context;
    }

    @Override
    public MediaRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(mContext).inflate(R.layout.media_item,null);
        MediaRVViewHolder mrvvh = new MediaRVViewHolder(layoutView);
        return mrvvh;
    }

    @Override
    public void onBindViewHolder(MediaRVViewHolder holder, int position) {
        holder.image.setImageURI(Uri.parse(itemList.get(position))) ;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
