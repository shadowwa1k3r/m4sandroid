package com.osg.loki.m4s.Tools;

import android.view.MotionEvent;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

/**
 * Created by ergas on 1/16/2018.
 */

public class CustomPolygon extends Polygon {

    CustomPolygon mPolygon;



    @Override
    public boolean onSingleTapUp(MotionEvent event, MapView mapView){
        if (mInfoWindow == null)
            //no support for tap:
            return false;
        boolean tapped = contains(event);




        if (tapped){
            InfoWindow.closeAllInfoWindowsOn(mapView);
            Projection pj = mapView.getProjection();
            GeoPoint position = (GeoPoint)pj.fromPixels((int)event.getX(), (int)event.getY());
            mInfoWindow.open(this, position, 0, 0);
        }


        return tapped;
    }

}
