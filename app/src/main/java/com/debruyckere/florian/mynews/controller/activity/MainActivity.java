package com.debruyckere.florian.mynews.controller.activity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.debruyckere.florian.mynews.R;
import com.debruyckere.florian.mynews.model.AlarmReceiver;
import com.debruyckere.florian.mynews.model.PageAdapter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_toolbar) Toolbar mToolbar;
    @BindView(R.id.main_tabs)TabLayout mTab;
    @BindView(R.id.main_navigation) NavigationView mNavigationView;
    @BindView(R.id.main_layout)DrawerLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        configureViewPager();
        createNotificationChannel();
        configureAlarmManager();
        configureDrawerLayout();
        configureNavigationView();

    }

    /**
     * create the tab
     */
    private void configureViewPager(){
        ViewPager pager = findViewById(R.id.main_viewpager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(),getResources().getIntArray(R.array.colorPagesViewPager)){
        });

        mTab.setupWithViewPager(pager);
        mTab.setTabMode(TabLayout.MODE_FIXED);

    }

    /**
     * create options of the toolbar
     * @param menu toolbar's menu
     * @return toolbar's option
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * add reaction to the toolbar
     * @param item option selected
     * @return good execution rapport
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_search :
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);
                return true;

            case R.id.toolbar_about:
                Intent intentAbout=new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                return true;

            case R.id.toolbar_notification:
                Intent intentNotification = new Intent(this,NotificationActivity.class);
                startActivity(intentNotification);
                return true;

            default:
                Log.d("OPTION SELECT","Default item selected");
                return super.onOptionsItemSelected(item);
        }
    }

    /*------------
      Notification
      ------------*/

    public void createNotificationChannel(){
        CharSequence name = "MyNews Channel";
        String description = "Channel use for Notification of MyNews app";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("MyNewsChannel",name,importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


    /*--------------
      Alarm Manager
      --------------*/

    /**
     * create the alarm
     */
    public void configureAlarmManager(){
        AlarmManager alarm =(AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR,7);               //alarm set to active at 7 hour
        cal.set(Calendar.MINUTE,0);
        //cal.add(Calendar.MINUTE,1);
        cal.set(Calendar.SECOND,0);
        Log.i("ALARM","set for: "+ cal.getTime());

        PendingIntent pi = PendingIntent.getBroadcast(this,0,new Intent(this, AlarmReceiver.class),0);
        SharedPreferences shared = getSharedPreferences("PARAMETER",MODE_PRIVATE);

        if(shared.getBoolean("paramEnable",false)){

            alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pi);
            Log.i("ALARM","STATE: ACTIVE");

        }else{
            alarm.cancel(pi);
            Log.i("ALARM","STATE: INACTIVE");
        }

    }

    /**
     * create toolbar
     */
    private void configureDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    /*-----------------
     Navigation Drawer
     ------------------*/

    /**
     * create navigation drawer
     */
    public void configureNavigationView(){
        mNavigationView = findViewById(R.id.main_navigation);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * add reaction to navigation drawer
     * @param item tab selected
     * @return good execution rapport
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent navigationIntent;

        switch (id){
            case R.id.main_drawer_notification:navigationIntent = new Intent(this, NotificationActivity.class);
                startActivity(navigationIntent);
                return true;
            case R.id.main_drawer_search:navigationIntent = new Intent(this, SearchActivity.class);
                startActivity(navigationIntent);
                return true;
            default:
                break;

        }

        return false;
    }

    /**
     * modify the reaction of back button to close the navigation drawer if it open
     */
    @Override
    public void onBackPressed(){
        if(this.mLayout.isDrawerOpen(GravityCompat.START)){
            this.mLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
