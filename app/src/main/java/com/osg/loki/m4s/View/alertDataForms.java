package com.osg.loki.m4s.View;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kbeanie.multipicker.api.FilePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenFile;
import com.osg.loki.m4s.Model.ReportAnswer;
import com.osg.loki.m4s.Model.alertDataModel;
import com.osg.loki.m4s.R;
import com.osg.loki.m4s.Tools.MediaRVAdapter;
import com.osg.loki.m4s.Tools.ProgressRequestBody;
import com.osg.loki.m4s.Tools.Urls;
import com.osg.loki.m4s.Tools.addressModel;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link alertDataForms#newInstance} factory method to
 * create an instance of this fragment.
 */
public class alertDataForms extends Fragment implements MapEventsReceiver {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String token;


    @BindView(R.id.lay)
    LinearLayout lay;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.me)
    Button me;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.pick)
    Button pick;
    @BindView(R.id.FIO)
    EditText fio;
    @BindView(R.id.TYPE)
    EditText type;
    @BindView(R.id.accept)
    Button accept;
    @BindView(R.id.ABOUT)
    EditText about;
    @BindView(R.id.weather)
    org.osmdroid.views.MapView mapView;
    @BindView(R.id.country)
    TextView country;
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.prog)
    ProgressBar prog;
    @BindView(R.id.progback)
    RelativeLayout progback;
    @BindView(R.id.sclay)
    ScrollView sclay;
    @BindView(R.id.to_me)
    ImageButton to_me;
    @BindView(R.id.addmedia)
    Button addmedia;
    @BindView(R.id.media)
    RecyclerView mediaRV;
    private GridLayoutManager mGridLayoutManager;
    private MediaRVAdapter mAdapter;
    private List<String> fileList = new ArrayList<>();
    private FilePicker mFilePicker;
    private FilePickerCallback mFilePickerCallback;
    private File file;
    private RequestBody reqFile;
    private RequestBody name,context;
    private List<MultipartBody.Part> parts=new ArrayList<MultipartBody.Part>();
    private ProgressRequestBody body;
    static String[] IMAGE_EXTENSIONS = {
            "jpg",
            "jpeg",
            "bmp",
            "png",
            "gif",
            "tiff",
            "webp",
            "ico"
    };

    static String[] VIDEO_EXTENSIONS = {
            "avi",
            "asf",
            "mov",
            "flv",
            "swf",
            "mpg",
            "mpeg",
            "mp4",
            "wmv",
    };
    private static Set<String> SET_IMAGE_EXTENSIONS = new HashSet<String>(Arrays.asList(IMAGE_EXTENSIONS));
    private static Set<String> SET_VIDEO_EXTENSIONS = new HashSet<String>(Arrays.asList(VIDEO_EXTENSIONS));



    GpsMyLocationProvider mGpsMyLocationProvider;
    MapController mapController;
    MyLocationNewOverlay mMyLocationNewOverlay;
    Retrofit mRetrofit,mRetrofit2;
    Urls service,service2;
    MapEventsOverlay mapEventsOverlay;
    private alertDataModel data = new alertDataModel();
    private int me_cnt=0;
    private Marker pos;

    public alertDataForms() {
        // Required empty public constructor
    }


    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {


        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        Log.e("onclick", "longPressHelper: beforeClear" );
        mapView.getOverlays().clear();
        Log.e("onclick", "longPressHelper: afterClear" );
        mapView.invalidate();
        Log.e("onclick", "longPressHelper: afterInvalidate" );
        mapView.getOverlays().add(0,mapEventsOverlay);
        Log.e("onclick", "longPressHelper: afteraddEvent" );
        pos = new Marker(mapView);
        pos.setTitle("asfdasfasfasf");
        pos.setPosition(p);
        data.setAltitude(p.getLatitude());
        data.setLongitude(p.getLongitude());

        Call<addressModel> getlocaddr = service.getAddress("jsonv2",p.getLatitude(),p.getLongitude(),"ru");
        getlocaddr.enqueue(new Callback<addressModel>() {
            @Override
            public void onResponse(Call<addressModel> call, final Response<addressModel> response) {
                if (response.isSuccessful()){
                    country.setText(response.body().getDisplayName());
                    state.setText("");
                    pos.setInfoWindow(new BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble,mapView));
                    pos.setTitle("haha");
                    mapView.invalidate();
                    Log.d("reverse", "onResponse: "+response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<addressModel> call, Throwable t) {

            }
        });

        mapView.getOverlays().add(pos);

        mapView.invalidate();

        return false;
    }

    public static alertDataForms newInstance(String param1) {
        alertDataForms fragment = new alertDataForms();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token= getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View adfl =inflater.inflate(R.layout.alert_data_forms_lay, container, false);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://nominatim.openstreetmap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRetrofit2 = new Retrofit.Builder()
                //.baseUrl("http://192.168.1.100:8000/")
                .baseUrl("http://app.fvv.uz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        service = mRetrofit.create(Urls.class);
        service2 = mRetrofit2.create(Urls.class);


        ButterKnife.bind(this,adfl);

        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        //mapView.setBuiltInZoomControls(true);
        mapController=(MapController)mapView.getController();
        mapController.setZoom(11);
        mapController.setCenter(new GeoPoint(41.3037,69.2660));
        mGpsMyLocationProvider = new GpsMyLocationProvider(getContext());
        mMyLocationNewOverlay = new MyLocationNewOverlay(mGpsMyLocationProvider,mapView);
        mMyLocationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(mMyLocationNewOverlay);
        mapView.setMultiTouchControls(true);
        me.performClick();

        mGridLayoutManager = new GridLayoutManager(getContext(),2);
        mediaRV.setLayoutManager(mGridLayoutManager);
        mediaRV.setHasFixedSize(true);







        addmedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("addclick", "onClick: ");
                mFilePicker=new FilePicker(alertDataForms.this);

                mFilePickerCallback=new FilePickerCallback() {
                    @Override
                    public void onFilesChosen(List<ChosenFile> list) {
                        for (int i = 0; i <list.size() ; i++) {

                            file = new File(list.get(i).getOriginalPath());


                            Log.e("FilePick", "onImagesChosen: " + list.get(i).getOriginalPath());
                            Log.e("FilePick", "onImagesChosen: " + list.get(i).getDisplayName());
                            Log.e("FilePick", "onImagesChosenext: " + list.get(i).getOriginalPath().substring(list.get(i).getOriginalPath().length() - (list.get(i).getOriginalPath().length() - list.get(i).getOriginalPath().lastIndexOf(".") - 1)));
                            Log.e("FilePick", "onImagesChosen: " + file.getName());

                            String filepathh=getPath(getContext(), Uri.parse(list.get(i).getOriginalPath()));
                            filepathh=list.get(i).getOriginalPath();
                            Log.e("FilePick", "onImagesChosen: "+filepathh );
                            file=new File(filepathh);

                            if (SET_IMAGE_EXTENSIONS.contains((filepathh.substring(filepathh.length() - (filepathh.length() - filepathh.lastIndexOf(".") - 1))).toLowerCase()) || SET_VIDEO_EXTENSIONS.contains((filepathh.substring(filepathh.length() - (filepathh.length() - filepathh.lastIndexOf(".") - 1))).toLowerCase())) {


                                reqFile = RequestBody.create(MediaType.parse("file/*"), file);


                                try {
                                    body=new ProgressRequestBody(new FileInputStream(list.get(i).getOriginalPath()), new ProgressRequestBody.UploadCallbacks() {
                                        @Override
                                        public void onProgressUpdate(int percentage) {
                                            //progress.setProgress(percentage);
                                            Log.i("upload", "onProgressUpdate: "+percentage);
                                        }
                                    }, MediaType.parse("file/*"));
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                parts.add(MultipartBody.Part.createFormData("file", file.getName(), body));

                                fileList.add(list.get(i).getOriginalPath());
                                Log.i("list_changed", "onFilesChosen: "+fileList.size());


                                //include.setText(list.get(i).getOriginalPath());
                                Log.e("FilePick", "onFilesChosen: hahaha" );


                                name = RequestBody.create(MediaType.parse("text/plain"), "text");
                                mAdapter = new MediaRVAdapter(fileList,getContext());
                                mAdapter.notifyDataSetChanged();

                                mediaRV.setAdapter(mAdapter);
                                mediaRV.smoothScrollToPosition(fileList.size() - 1);
                            }
                        }
                    }

                    @Override
                    public void onError(String s) {
                        Log.e("er", "onError: "+s );
                    }
                };
                mFilePicker.setFilePickerCallback(mFilePickerCallback);
                mFilePicker.allowMultiple();


                mFilePicker.pickFile();
            }
        });



        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Log.e("onclick", "longPressHelper: test single" );
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                Log.e("onclick", "longPressHelper: test long" );
                Log.e("onclick", "longPressHelper: beforeClear" );
                mapView.getOverlays().clear();
                mapView.getOverlays().add(mMyLocationNewOverlay);
                Log.e("onclick", "longPressHelper: afterClear" );
                mapView.invalidate();
                Log.e("onclick", "longPressHelper: afterInvalidate" );
                mapView.getOverlays().add(0,mapEventsOverlay);
                Log.e("onclick", "longPressHelper: afteraddEvent" );
                Marker pos = new Marker(mapView);
                pos.setPosition(p);
                mapView.getOverlays().add(pos);
                //data.setLocation(p.getLongitude(),p.getLatitude());
                data.setLongitude(p.getLongitude());
                data.setAltitude(p.getLatitude());
                Call<addressModel> getlocaddr = service.getAddress("jsonv2",p.getLatitude(),p.getLongitude(),"ru");
                getlocaddr.enqueue(new Callback<addressModel>() {
                    @Override
                    public void onResponse(Call<addressModel> call, Response<addressModel> response) {
                        if (response.isSuccessful()){
                            country.setText(response.body().getDisplayName());
                            state.setText("");
                            Log.d("reverse", "onResponse: "+response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<addressModel> call, Throwable t) {
                        Log.d("check", "onFailure:fail "+t.getMessage());

                    }
                });
                mapView.invalidate();

                return false;
            }
        };

        mapEventsOverlay = new MapEventsOverlay(getContext(),mReceive);
        mapView.getOverlays().add(0,mapEventsOverlay);


        to_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mapView.getController().animateTo(new GeoPoint(mGpsMyLocationProvider.getLastKnownLocation()));
                }
                catch (Exception e){
                    Toast.makeText(getContext(),"Not available",Toast.LENGTH_SHORT).show();
                }
            }
        });


        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                me_cnt=1;
                progback.setClickable(true);
                //prog.setVisibility(View.VISIBLE);
                //progback.setVisibility(View.VISIBLE);
                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }
                else {
                    Log.e("osm", "onCreateView: "+"permission granted" );
                    LocationListener locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.e("osm", "onLocationChanged: "+location.getLongitude() );
                            if (me_cnt==1) {
                                me_cnt++;
                                GeoPoint geoPoint = new GeoPoint(location.getLongitude(), location.getAltitude());
                                // mapController.setCenter(geoPoint);
                            }

                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {
                            Intent gpsOptionsIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(gpsOptionsIntent);
                        }
                    };
                    LocationManager locationManager = (LocationManager)getActivity().getSystemService(getContext().LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    Call<addressModel> getlocaddr = service.getAddress("jsonv2", location.getLatitude(), location.getLongitude(), "ru");
                    getlocaddr.enqueue(new Callback<addressModel>() {
                        @Override
                        public void onResponse(Call<addressModel> call, Response<addressModel> response) {
                            if (response.isSuccessful()) {
                                country.setText(response.body().getDisplayName());
                                state.setText("");
                                Log.d("reverse", "onResponse: " + response.body().toString());
                                prog.setVisibility(View.GONE);
                                progback.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<addressModel> call, Throwable t) {
                            Log.d("mefail", "onFailure: faiiil");
                            prog.setVisibility(View.GONE);
                        }
                    });

                    //data.setLocation(location.getLongitude(),location.getAltitude());
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getAltitude()!=0) {
                    data.setName(fio.getText().toString());
                    data.setType(type.getText().toString());
                    data.setAbout(about.getText().toString());

                    Call<ReportAnswer> postReport = service2.postReport("Token " + token, data, parts);
                    Log.i("data", "onClick: " + data.getName() + data.getType() + data.getAltitude() + data.getLongitude() + data.getAbout());

                    postReport.enqueue(new Callback<ReportAnswer>() {
                        @Override
                        public void onResponse(Call<ReportAnswer> call, Response<ReportAnswer> response) {
                            Log.i("data", "onResponse: " + response.code());
                            if (response.code() == 200) {
                                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle("Отрпавка информации")
                                        .setMessage("Ваш запрос был принят.").setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                getActivity().getSupportFragmentManager().popBackStack();
                                            }
                                        }).show();
                                alertDialog.show();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle("Отправка информации")
                                        .setMessage("Ошибка соеденение с сервером, попробуйте еще раз ").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                getActivity().getSupportFragmentManager().popBackStack();
                                            }
                                        }).show();
                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ReportAnswer> call, Throwable t) {
                            Log.e("data", "onFailure: " + t.getMessage() + " " + token);
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle("Отправка информации")
                                    .setMessage("Ошибка соеденение с сервером, попробуйте еще раз ").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            getActivity().getSupportFragmentManager().popBackStack();
                                        }
                                    }).show();
                            alertDialog.show();
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(),"Выберите на карте место происществии",Toast.LENGTH_LONG).show();
                }
            }
        });
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET},2);
                } else {
                    Log.e("osm", "onCreateView: "+"permission granted" );
                    LocationListener locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.e("osm", "onLocationChanged: "+location.getLongitude() );
                           // to_me.performClick();
                           /* if (me_cnt==1) {
                                me_cnt++;
                                GeoPoint geoPoint = new GeoPoint(location.getLongitude(), location.getAltitude());
                                // mapController.setCenter(geoPoint);
                                Call<addressModel> getlocaddr = service.getAddress("jsonv2", location.getLatitude(), location.getLongitude(), "ru");
                                getlocaddr.enqueue(new Callback<addressModel>() {
                                    @Override
                                    public void onResponse(Call<addressModel> call, Response<addressModel> response) {
                                        if (response.isSuccessful()) {
                                            country.setText(response.body().getDisplayName());
                                            state.setText("");
                                            Log.d("reverse", "onResponse: " + response.body().toString());
                                            prog.setVisibility(View.GONE);
                                            progback.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<addressModel> call, Throwable t) {
                                        Log.d("mefail", "onFailure: faiiil");
                                        prog.setVisibility(View.GONE);
                                    }
                                });

                            }*/

                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {
                            to_me.performClick();
                        }

                        @Override
                        public void onProviderDisabled(String s) {
                            Intent gpsOptionsIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(gpsOptionsIntent);
                        }
                    };
                    LocationManager locationManager = (LocationManager)getActivity().getSystemService(getContext().LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    try {
                        mapView.getController().animateTo(new GeoPoint(location));
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),"Not available",Toast.LENGTH_SHORT).show();
                    }
                    //data.setLocation(location.getLongitude(),location.getAltitude());
                }

               // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,new MapView()).addToBackStack(null).commit();
                mapView.setVisibility(View.VISIBLE);
                InputMethodManager imm=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (view.getWindowToken() != null) {

                imm.hideSoftInputFromWindow(view.getWindowToken(),0);}
                //mapView.setBuiltInZoomControls(true);
                addmedia.setVisibility(View.INVISIBLE);
                mediaRV.setVisibility(View.INVISIBLE);
                accept.setVisibility(View.VISIBLE);
                lay.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
                send.setVisibility(View.INVISIBLE);
                sclay.setVisibility(View.INVISIBLE);
                to_me.setVisibility(View.VISIBLE);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //me.performClick();
                mapView.invalidate();
                mapView.setVisibility(View.INVISIBLE);
                mapView.setBuiltInZoomControls(false);
                accept.setVisibility(View.INVISIBLE);

                to_me.setVisibility(View.INVISIBLE);
                addmedia.setVisibility(View.VISIBLE);
                mediaRV.setVisibility(View.VISIBLE);
                sclay.setVisibility(View.VISIBLE);
                lay.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                send.setVisibility(View.VISIBLE);
            }
        });
        return adfl;
    }
    public static String getPath(final Context context, final Uri uri) {


        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == Picker.PICK_FILE){
            if(resultCode== Activity.RESULT_OK){
                if(mFilePicker==null){
                    mFilePicker=new FilePicker(alertDataForms.this);
                    mFilePicker.setFilePickerCallback(mFilePickerCallback);
                }
                mFilePicker.submit(data);


            }
           /* for (int i = 0; i <medialist.size() ; i++) {

            }
            */



        }
    }






}
