package com.osg.loki.m4s.View;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;
import com.osg.loki.m4s.Contracts.MainPageContract;
import com.osg.loki.m4s.Dagger.App;
import com.osg.loki.m4s.MainPageItemAdapter;
import com.osg.loki.m4s.Model.MainPageModel;
import com.osg.loki.m4s.Model.Wikimodel;
import com.osg.loki.m4s.Presenter.MainPagePresenter;
import com.osg.loki.m4s.R;
import com.osg.loki.m4s.Tools.Urls;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainPageContract.View{

    private String token;
    private int current;
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MainPagePresenter mPresenter;
    @BindView(R.id.bottomNav)
    BottomBar bottomNav;
    @BindView(R.id.settings)
    ImageButton settings;
    @BindView(R.id.contentContainer)
    FrameLayout container;
    @BindView(R.id.Logo)
    ImageView logo;
    private MainPageModel baza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.material_main);
        Bundle extras = getIntent().getExtras();
        token=extras.getString("token");
        Toast.makeText(this,"logged in as:"+extras.getString("token"),Toast.LENGTH_LONG).show();
        int PERMISSON_CODE = 2;

        String[] permission = new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        ActivityCompat.requestPermissions(this,permission,PERMISSON_CODE);

        ButterKnife.bind(this);

        new Instabug.Builder(getApplication(), "835620b5a1bdefd7a2ff0c889c570b52")
                .setInvocationEvents(InstabugInvocationEvent.FLOATING_BUTTON, InstabugInvocationEvent.SCREENSHOT)
                .build();



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.168.1.102:8000/")
                .baseUrl("https://app.fvv.uz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Urls service = retrofit.create(Urls.class); final String PREFS_NAME = "MyPrefsFile";
        final String PREF_TOKEN = "token";
        final String DOESNT_EXIST = "-1";
        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        final Call<List<Wikimodel>> sync = service.syncwiki(prefs.getString(PREF_TOKEN,DOESNT_EXIST),1);
        sync.enqueue(new Callback<List<Wikimodel>>() {
            @Override
            public void onResponse(Call<List<Wikimodel>> call, Response<List<Wikimodel>> response) {
                Log.d("sync", "onResponse: "+response.code());
                if (response.code()==200) {
                    Log.d("sync", "onResponse: "+response.body().size());
                    baza = new MainPageModel(Realm.getDefaultInstance());
                    baza.setHelpList(response.body());
                    Toast.makeText(getApplicationContext(),"Обновление базы данных справочника успешно выполнено",Toast.LENGTH_LONG).show();
                    Log.e("checkupdateddb", "onClick: " + baza.getHelpList().get(0).getContent() + " size=" + baza.getHelpList().size());
                }
                else if (response.code()==304){
                    Toast.makeText(getApplicationContext(),"Обновление не требуется",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Ошибка сервера при обновлении базы данных справочника",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Wikimodel>> call, Throwable t) {
                Log.d("sync", "onResponse: "+t.getMessage());
                Toast.makeText(getApplicationContext(),"Ошибка сети при обновлении базы данных справочника",Toast.LENGTH_LONG).show();
            }
        });



        //home.setTypeface(Typeface.DEFAULT_BOLD);

        //mLayoutManager = new LinearLayoutManager(this);
      //  list.setLayoutManager(mLayoutManager);
       // list.setHasFixedSize(true);

        App.getComponent().inject(this);

        mPresenter.attachView(this);
        mPresenter.loadItemList();
        mPresenter.viewIsReady();



        bottomNav.setDefaultTab(R.id.item3);

      bottomNav.setOnTabSelectListener(new OnTabSelectListener() {
          @Override
          public void onTabSelected(int tabId) {

              switch (tabId){
                  case R.id.item1:ChangeFragment("item1");break;
                  case R.id.item2:ChangeFragment("item2");break;
                  case R.id.item4:ChangeFragment("item4");break;
                  case R.id.item5:ChangeFragment("item5");break;
                  case R.id.item3:ChangeFragment("item3");break;
              }

                 }


      });
      settings.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.contentContainer, SubItemPageView.newInstance(5)).addToBackStack(null).commit();
          }
      });



    }

    public static void ImageViewAnimatedChange(Context c, final ImageView v, final int new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.slide_in_left);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, R.anim.slide_in_right);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {
                v.setImageResource(new_image);
            }
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                /*v.setImageResource(new_image);

                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVddddddddddddddddddddppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp41
                v.startAnimation(anim_in);*/
            }
        });
        v.startAnimation(anim_out);
    }

    private void ChangeFragment(String value){
        Fragment fragment = null;
        switch (value) {
            case "item1":  try {
                fragment = SubItemPageView.newInstance(0);
                ImageViewAnimatedChange(getApplication(),logo, R.drawable.ic_phone_call);
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
                break;
            case "item2": fragment = new LicenseRequest();break;
            case "item3": fragment= AlertView.newInstance(token);/*ImageViewAnimatedChange(getApplication(),logo, R.drawable.logo);*/break;
//            case "item4": fragment= SubItemPageView.newInstance(4);break;
            case "item4": fragment = new LicenseRequest();break;
            case "item5": fragment= new NewSettings();break;
        }

        if(fragment!=null) {int a = getSupportFragmentManager().getBackStackEntryCount();
            for(int i = 0;i<a;i++)
            {getSupportFragmentManager().popBackStack();
                Toast.makeText(this,getSupportFragmentManager().getBackStackEntryCount()+"s",Toast.LENGTH_LONG).show();}
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.contentContainer, fragment)
                    .commit();
        }
    }




    @Override
    public void setAdapter(MainPageItemAdapter mAdapter){
        //list.setAdapter(mAdapter);

    }

    @Override
    public void close(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}

