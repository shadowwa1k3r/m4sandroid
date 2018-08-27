package com.osg.loki.m4s.View;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osg.loki.m4s.Contracts.MapMageContract;
import com.osg.loki.m4s.Dagger.App;
import com.osg.loki.m4s.R;
import com.osg.loki.m4s.Tools.CustomPolygon;
import com.osg.loki.m4s.Tools.State;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapView extends Fragment implements MapMageContract.View,MapEventsReceiver {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private MapController mapController;

    @BindView(R.id.weather)
    org.osmdroid.views.MapView mapView;



    public MapView() {
        // Required empty public constructor
    }

    public static MapView newInstance(String param1, String param2) {
        MapView fragment = new MapView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
      //  Log.d("osmd", "singleTapConfirmedHelper: "+p.getAltitude()+" "+p.getLongitude());
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        Log.d("osmd", "longpressHelper: "+p.getAltitude()+" "+p.getLongitude());
        return true;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View Map = inflater.inflate(R.layout.weathermap,container,false);


        ButterKnife.bind(this,Map);
        App.getComponent().inject(this);

        mapView.setTileSource(TileSourceFactory.MAPNIK);

        mapView.setMaxZoomLevel(11);
        mapView.setMinZoomLevel(6);
        mapView.setBuiltInZoomControls(true);
        mapController = (MapController)mapView.getController();
        mapController.setZoom(6);
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(getContext(),this);
        mapView.getOverlays().add(0,mapEventsOverlay);



        Context ctx = getContext();
        GeoPoint gPt = new GeoPoint(41.2914,69.227);
        mapController.setCenter(gPt);

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2);
        } else {
            Log.e("osm", "onCreateView: "+"permission granted" );
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.e("osm", "onLocationChanged: "+location.getLongitude() );
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            LocationManager locationManager = (LocationManager)getActivity().getSystemService(ctx.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //mapController.animateTo(new GeoPoint(location.getLongitude(),location.getAltitude()));
            Marker.ENABLE_TEXT_LABELS_WHEN_NO_IMAGE =true;
            Marker areaname = new Marker(mapView);
            areaname.setPosition(new GeoPoint(location.getLongitude(),location.getAltitude()));
            areaname.setTextLabelBackgroundColor(Color.YELLOW);
            areaname.setTextLabelFontSize(20);
            areaname.setTextLabelForegroundColor(Color.BLACK);
            areaname.setTitle(String.valueOf("Me"));
            areaname.setIcon(null);
            mapView.getOverlays().add(areaname);
            areaname.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, org.osmdroid.views.MapView mapView) {
                    marker.getTitle();
                    return false;
                }
            });
            Log.e("osm", "onCreateView: "+location.getLongitude());
        }


        MyLocationNewOverlay locationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx),mapView);

        locationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(locationNewOverlay);
      // mapView.setUseDataConnection(false);
       return Map;
    }

    //public Marker setMarker()

    public CustomPolygon setPolygon(String src, String name,String fillColor,int strokeColor){
        ArrayList<GeoPoint> coordinates;
        coordinates = parseKmlCoordinates(src);
        CustomPolygon polygon = new CustomPolygon();
        polygon.setFillColor(Color.parseColor(fillColor));
        polygon.setStrokeWidth(5f);
        polygon.setStrokeColor(strokeColor);
        polygon.setPoints(coordinates);
        polygon.setInfoWindow(new BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble,mapView));
        polygon.setTitle(name);


        return polygon;
    }

    public void addPolygon(CustomPolygon polygon,int id){
        mapView.getOverlayManager().add(id,polygon);
    }

    public void editPolygon(CustomPolygon polygon,int id){
        mapView.getOverlayManager().set(id,polygon);
    }







    protected static ArrayList<GeoPoint> parseKmlCoordinates(String input){
        LinkedList<GeoPoint> tmpCoords = new LinkedList<GeoPoint>();
        int i = 0;
        int tupleStart = 0;
        int length = input.length();
        boolean startReadingTuple = false;
        while (i<length){
            char c = input.charAt(i);
            if (c==' '|| c=='\n' || c=='\t'){
                if (startReadingTuple){ //just ending coords portion:
                    String tuple = input.substring(tupleStart, i);
                    GeoPoint p = parseKmlCoord(tuple);
                    if (p != null)
                        tmpCoords.add(p);
                    startReadingTuple = false;
                }
            } else { //data
                if (!startReadingTuple){ //just ending space portion
                    startReadingTuple = true;
                    tupleStart = i;
                }
                if (i == length-1){ //at the end => handle last tuple:
                    String tuple = input.substring(tupleStart, i+1);
                    GeoPoint p = parseKmlCoord(tuple);
                    if (p != null)
                        tmpCoords.add(p);
                }
            }
            i++;
        }
        ArrayList<GeoPoint> coordinates = new ArrayList<GeoPoint>(tmpCoords.size());
        coordinates.addAll(tmpCoords);

        return coordinates;
    }

    protected static GeoPoint parseKmlCoord(String input){
        int end1 = input.indexOf(',');
        int end2 = input.indexOf(',', end1+1);
        try {
            if (end2 == -1){
                double lon = Double.parseDouble(input.substring(0, end1));
                double lat = Double.parseDouble(input.substring(end1+1, input.length()));
                return new GeoPoint(lat, lon);
            } else {
                double lon = Double.parseDouble(input.substring(0, end1));
                double lat = Double.parseDouble(input.substring(end1+1, end2));
                double alt = Double.parseDouble(input.substring(end2+1, input.length()));
                return new GeoPoint(lat, lon, alt);
            }
        } catch (NumberFormatException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    @Override
    public void setPolygons(ArrayList<State> states) {
        for (int i = 0; i <states.size() ; i++) {
            addPolygon(setPolygon(states.get(i).getSrc(),states.get(i).getName(),states.get(i).getFillColor(),states.get(i).getStrokeColor()),i);
        }


    }

    @Override
    public void setMarkers() {

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

}
