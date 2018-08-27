package com.osg.loki.m4s;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.osg.loki.m4s.View.SubItemPageHolderView;

/**
 * Created by ergas on 1/9/2018.
 */

public class SubItemPageListHolder extends RecyclerView.ViewHolder implements SubItemPageHolderView {

     TextView title;
     ImageView icon;

    public SubItemPageListHolder(View itemView){
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


     /* try {
          icon.setImageResource(R.drawable.ic_fireman);
         // icon.setImageURI(Uri.parse("ic_flame"));
          //icon.setImageResource(src);

      }
      catch (Exception e){
          e.printStackTrace();
      }*/



     /*   switch (src){
            case 0:icon.setImageResource(R.drawable.ic_flasks); break;
            case 1:icon.setImageResource(R.drawable.ic_oil);break;
            case 2:icon.setImageResource(R.drawable.ic_snake);break;
            case 3:icon.setImageResource(R.drawable.ic_light_bulb);break;
            case 4:icon.setImageResource(R.drawable.ic_beehive);break;
            case 5:icon.setImageResource(R.drawable.ic_fire);break;
            case 6:icon.setImageResource(R.drawable.ic_campfire);break;
            case 7:icon.setImageResource(R.drawable.ic_storm);break;
            case 8:icon.setImageResource(R.drawable.ic_overturned_car);break;
        }*/


    }

}
