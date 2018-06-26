package com.debruyckere.florian.mynews.controller.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.debruyckere.florian.mynews.R;

import java.util.ArrayList;

import butterknife.BindView;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.notification_search_term)EditText mSearchTerm;
    @BindView(R.id.notification_arts)CheckBox mArtsBox;
    @BindView(R.id.notification_business)CheckBox mBusinessBox;
    @BindView(R.id.notification_politics)CheckBox mPoliticsBox;
    @BindView(R.id.notification_travel)CheckBox mTravelsBox;
    @BindView(R.id.notification_sports)CheckBox mSportsBox;
    @BindView(R.id.notification_entrepreneurs)CheckBox mEntrepreneursBox;
    @BindView(R.id.notification_enable)Switch mEnableSwitch;
    private ArrayList<CheckBox> CheckboxList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        configureToolbar();

        mSearchTerm = findViewById(R.id.notification_search_term);
        mArtsBox = findViewById(R.id.notification_arts);
        mBusinessBox = findViewById(R.id.notification_business);
        mPoliticsBox = findViewById(R.id.notification_politics);
        mTravelsBox = findViewById(R.id.notification_travel);
        mSportsBox = findViewById(R.id.notification_sports);
        mEntrepreneursBox = findViewById(R.id.notification_entrepreneurs);
        mEnableSwitch = findViewById(R.id.notification_enable);
                                            //index
        CheckboxList.add(mArtsBox);         //0
        CheckboxList.add(mBusinessBox);     //1
        CheckboxList.add(mPoliticsBox);     //2
        CheckboxList.add(mTravelsBox);      //3
        CheckboxList.add(mSportsBox);       //4
        CheckboxList.add(mEntrepreneursBox);//5

        configureListener();
        SharedLoader();


    }

    /*------------
     Toolbar
      ------------*/

    public void configureToolbar(){
        Toolbar mToolbar = findViewById(R.id.notification_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_search :
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);
                return true;

            case R.id.toolbar_about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                return true;

            case R.id.toolbar_notification:
                Toast.makeText(this,"You are already there",Toast.LENGTH_LONG).show();
                return true;

            case 16908332:      //button home
                this.finish();
                return true;

            default:
                Log.d("OPTION SELECT","Default item selected");
                return super.onOptionsItemSelected(item);
        }
}

    /*---------------
      Configuration
      ---------------*/

    public void configureListener(){
        mArtsBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedSaver(0);
            }
        });

        mBusinessBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedSaver(1);
            }
        });

        mPoliticsBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedSaver(2);
            }
        });

        mTravelsBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedSaver(3);
            }
        });

        mSportsBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedSaver(4);
            }
        });

        mEntrepreneursBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedSaver(5);
            }
        });


        mSearchTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences.Editor editor = getSharedPreferences("PARAMETER",MODE_PRIVATE).edit();
                editor.putString("paramSearch",mSearchTerm.getText().toString());
                editor.apply();
            }
        });

        mEnableSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("PARAMETER",MODE_PRIVATE).edit();
                editor.putBoolean("paramEnable",mEnableSwitch.isChecked());
                editor.apply();
            }
        });
    }

    /*-----------------
      Shared Preferences
     ------------------*/

    public void SharedLoader(){
        SharedPreferences mPrefs = getSharedPreferences("PARAMETER", MODE_PRIVATE);
        int indexParam = 0;
        Boolean update;

        try {
            mSearchTerm.setText(mPrefs.getString("paramSearch",null));
            mEnableSwitch.setChecked(mPrefs.getBoolean("paramEnable",false));

            while (indexParam <= 5) {
                update = mPrefs.getBoolean("paramValue" + indexParam, false);

                if (update) {
                    String paramName = mPrefs.getString("paramName" + indexParam, null);
                    int indexCheckbox = 0;

                    while (indexCheckbox <= 5) {
                        if (paramName.contentEquals(CheckboxList.get(indexCheckbox).getText())) {
                            CheckboxList.get(indexCheckbox).setChecked(true);
                            indexCheckbox = 6;
                        }

                        indexCheckbox++;
                    }

                }

                indexParam++;
            }
        }catch (Exception e){
            Log.e("SHARED LOADER","INDEX: "+indexParam);
            e.printStackTrace();
            Toast.makeText(this,"ERROR: Can't load parameter",Toast.LENGTH_LONG).show();
        }
    }

    public void SharedSaver(int pIndex){
        //save in sharedPreferences the change
        try {
            SharedPreferences.Editor editor = getSharedPreferences("PARAMETER",MODE_PRIVATE).edit();
            editor.putBoolean("paramValue"+pIndex,CheckboxList.get(pIndex).isChecked());
            editor.putString("paramName"+pIndex,(String)CheckboxList.get(pIndex).getText());
            editor.apply();
            Log.i("SHARED SAVER","SAVE for"+CheckboxList.get(pIndex).getText());
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"ERROR: Can't save parameter",Toast.LENGTH_LONG).show();
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

    public void NotificationConfiguration(){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"MyNewsChannel")
                .setContentTitle("MyNews new news")
                .setContentText("A new news has arrived")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
}