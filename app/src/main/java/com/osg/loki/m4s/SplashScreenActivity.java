package com.osg.loki.m4s;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.osg.loki.m4s.DataBase.ItemFullInfo;
import com.osg.loki.m4s.DataBase.MenuItem2List;
import com.osg.loki.m4s.Model.MainPageItemType;
import com.osg.loki.m4s.Tools.MySharedPreference;
import com.osg.loki.m4s.Tools.State;
import com.osg.loki.m4s.View.auth1;
import com.osg.loki.m4s.View.intro1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicLong;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SplashScreenActivity extends AppCompatActivity {

    private AtomicLong itemListPrimeryKey;
    private MySharedPreference oneTime = new MySharedPreference();
    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    int i=0;
    String data="string content \n <b>This text is bold</b> \n <strong>This text is strong</strong> \n " +
            "<i>This text is italic</i> \n <em>This text is emphasized</em> \n <h2>HTML <small>Small</small> Formatting</h2> \n " +
            "<h2>HTML <mark>Marked</mark> Formatting</h2> \n <p>My favorite color is <del>blue</del> red.</p>\n<p>My favorite <ins>color</ins> is red.</p>" +
            "\n<p>This is <sub>subscripted</sub> text.</p>\n<p>This is <sup>superscripted</sup> text.</p>\n" +
            "<h1 style=\"background-color:DodgerBlue;\">Hello World</h1>\n" +
            "<p style=\"background-color:Tomato;\">Lorem ipsum...</p> <h1 style=\"color:Tomato;\">Hello World</h1>\n" +
            "<p style=\"color:DodgerBlue;\">Lorem ipsum...</p>\n" +
            "<p style=\"color:MediumSeaGreen;\">Ut wisi enim...</p> <h1 style=\"border:2px solid Tomato;\">Hello World</h1>\n" +
            "<h1 style=\"border:2px solid DodgerBlue;\">Hello World</h1>\n" +
            "<h1 style=\"border:2px solid Violet;\">Hello World</h1> <h1 style=\"background-color:rgb(255, 99, 71);\">...</h1>\n" +
            "<h1 style=\"background-color:#ff6347;\">...</h1>\n" +
            "<h1 style=\"background-color:hsl(9, 100%, 64%);\">...</h1>\n" +
            "\n" +
            "<h1 style=\"background-color:rgba(255, 99, 71, 0.5);\">...</h1>\n" +
            "<h1 style=\"background-color:hsla(9, 100%, 64%, 0.5);\">...</h1> \n<a href=\"https://www.w3schools.com/html/\">Visit our HTML tutorial</a> ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        mProgressBar.setProgress(0);
        Random random=new Random();

        final long period=50;
        final Timer timer=new Timer();
        Intent intent = getIntent();
        try{if (!intent.getStringExtra("re").equals("1")){
        initRealm();}}
        catch (NullPointerException e){
            initRealm();
        }
        checkFirstRun();


        /*timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(i<100){
                    mProgressBar.setProgress(i);
                    i++;

                }
                else {
                    timer.cancel();
                    initRealm();


                    //createTest(1,"Sometest","sometestdescr",1,"somequestion","a1","a2","a3","a4",2);
                  //  createTest(1,"Sometest","sometestdescr",2,"somequestion2","a1","a2","a3","a4",1);

                    Intent main=new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(main);
                    finish();
                }
            }
        },0,period-random.nextInt(50));

*/

       /* saveSubItem(1,"Авария с химически опасным веществом",R.drawable.ic_flask);
        saveSubItem(2,"Бензин и другие горючие жидкости",R.drawable.ic_gasoline);
        saveSubItem(3,"В дом заползла змея",R.drawable.ic_snake);
        saveSubItem(4,"Выбило пробки",R.drawable.ic_invention);
        saveSubItem(5,"Гнездо ос в доме",R.drawable.ic_beehive);
        saveSubItem(6,"Горит лес",R.drawable.ic_forest);

        saveItemInfo(2,data,R.drawable.logo);
/*
        saveItem("Что делать",0);
        saveItem("Первая помощь",1);
        saveItem("Карта неблагоприятных явлений",2);
        saveItem("Проверь себя",3);
        saveItem("Энциклопедия",4);
        saveItem("Информация и настройки",5);*/
        //saveItem(1,"RIGHT",R.drawable.ic_chevron_right);
        String color="#663F51B5";
        final List<State> states = new ArrayList<>();
/*
        states.add(new State(getString(R.string.TashkentFull),"Tashkent",color, Color.BLUE));
        states.add(new State(getString(R.string.Karakalpakstan),"Karakalpakstan",color,Color.BLUE));
        states.add(new State(getString(R.string.Khorezm),"Khorezm",color,Color.BLUE));
        states.add(new State(getString(R.string.Bukhara),"Bukhara",color,Color.BLUE));
        states.add(new State(getString(R.string.Navoi),"Navoi",color,Color.BLUE));
        states.add(new State(getString(R.string.Samarkand),"Samarkand",color,Color.BLUE));
        states.add(new State(getString(R.string.Jizzakh),"Jizzakh",color,Color.BLUE));
        states.add(new State(getString(R.string.Kashkadarya),"Kashkadarya",color,Color.BLUE));
        states.add(new State(getString(R.string.Surkhandarya),"Surkhandarya",color,Color.BLUE));
        states.add(new State(getString(R.string.Sirdarya),"Sirdarya",color,Color.BLUE));
        states.add(new State(getString(R.string.Namangan),"Namangan",color,Color.BLUE));
        states.add(new State(getString(R.string.Ferghana),"Ferghana",color,Color.BLUE));
        states.add(new State(getString(R.string.Andijan),"Andijan",color,Color.BLUE));

        for (int i = 0; i <states.size() ; i++) {
            saveRegions(states.get(i).getSrc(),states.get(i).getName(),states.get(i).getFillColor(),states.get(i).getStrokeColor());
        }*/




       //getSupportFragmentManager().beginTransaction().replace(R.id.frame,new SubItemFullInfo()).addToBackStack(null).commit();
    }

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run

            getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.container,new auth1()).commit();
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)
            getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.container,new intro1()).commit();

        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    private void initRealm(){
        Realm.init(this);


        try {
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .assetFile("data4.realm")
                    .name("data4.realm")
                    .schemaVersion(80)
                    //.migration(new Migration())
                    //.deleteRealmIfMigrationNeeded()

                    //.deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(configuration);
        }
        catch (Exception e){
            e.printStackTrace();
        }






       /* try {
            itemListPrimeryKey = new AtomicLong(realm.where(MainPageItemType.class).max("id").longValue()+1);
        }
        catch (Exception e){
            realm.beginTransaction();
            //create temp item so as to create the table
            MainPageItemType tempItem = new MainPageItemType();
            tempItem.setId(0);
            realm.copyToRealm(tempItem);
            itemListPrimeryKey = new AtomicLong(realm.where(MainPageItemType.class).max("id").longValue()+1);
            //now remove temp item
            RealmResults<MainPageItemType> itemList = realm.where(MainPageItemType.class).equalTo("id",0).findAll();
            itemList.deleteAllFromRealm();
            realm.commitTransaction();
            e.printStackTrace();
        }*/
    }
    /*public void createTest(int test_id,String test_name,String test_descr,int q_id,String q_content,String a1,String a2,String a3, String a4,String a5,int a){
        Realm realm = Realm.getDefaultInstance();
        Test test = new Test(test_id,test_name,test_descr,q_id,q_content,a1,a2,a3,a4,a5,a);
        realm.beginTransaction();
        realm.copyToRealm(test);
        realm.commitTransaction();

    }*/
    public void saveItem(String title,int iconpath){

        Realm realm = Realm.getDefaultInstance();
        MainPageItemType item= new MainPageItemType();

        item.setTitle(title);
        item.setIconpath(iconpath);
        realm.beginTransaction();
        final MainPageItemType todb = realm.copyToRealm(item);
        realm.commitTransaction();
    }
    public void saveSubItem(int id,String title,int iconpath){

        Realm realm = Realm.getDefaultInstance();
        MenuItem2List item= new MenuItem2List(id,title,iconpath);
      /* MenuItem2List item2= new MenuItem2List();
        MenuItem4List item3= new MenuItem4List();
        MenuItem5List item4= new MenuItem5List();
        MenuItem6List item5= new MenuItem6List();*/


        realm.beginTransaction();
        realm.copyToRealm(item);

        realm.commitTransaction();
    }
    public void saveItemInfo(int id,String content,int img){
        Realm realm = Realm.getDefaultInstance();
        ItemFullInfo itemContent = new ItemFullInfo(id,content,img);
        realm.beginTransaction();
        realm.copyToRealm(itemContent);
        realm.commitTransaction();
    }
    public void saveRegions(String coordinates,String name,String fillColor,int strokeColor){
        Realm realm = Realm.getDefaultInstance();
        State state = new State(coordinates,name,fillColor,strokeColor);
        realm.beginTransaction();
        realm.copyToRealm(state);
        realm.commitTransaction();

    }

}
