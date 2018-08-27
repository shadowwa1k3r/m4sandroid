package com.osg.loki.m4s.Tools;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

/**
 * Created by ergas on 3/16/2018.
 */

public class PlaceInfoWindow extends MarkerInfoWindow {

    public PlaceInfoWindow(int layoutResId, MapView mapView){
        super(layoutResId,mapView);
    }

    @Override
    public void onOpen(final Object item){

    }


}
