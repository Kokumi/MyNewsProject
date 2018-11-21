package com.debruyckere.florian.mynews.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    @BindView(R.id.notification_climate)CheckBox mClimateBox;
    @BindView(R.id.notification_enable)Switch mEnableSwitch;
    private ArrayList<CheckBox> CheckboxList = new ArrayList<>();
    private Boolean valid = false;

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
        mClimateBox = findViewById(R.id.notification_climate);
        mEnableSwitch = findViewById(R.id.notification_enable);
                                            //index
        CheckboxList.add(mArtsBox);         //0
        CheckboxList.add(mBusinessBox);     //1
        CheckboxList.add(mPoliticsBox);     //2
        CheckboxList.add(mTravelsBox);      //3
        CheckboxList.add(mSportsBox);       //4
        CheckboxList.add(mClimateBox);      //5

        configureListener();
        SharedLoader();
    }

    /*------------
     Toolbar
      ------------*/

    /**
     * create Toolbar
     */
    public void configureToolbar(){
        Toolbar mToolbar = findViewById(R.id.notification_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * create toolbar's option
     * @param menu toolbar's menu
     * @return toolbar's options
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * add reaction to toolbar's option
     * @param item toolbar's option
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

    /**
     * configure reaction to checkbox
     */
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

        mClimateBox.setOnClickListener(new View.OnClickListener() {
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
                // save to shared parameter the entry
                SharedPreferences.Editor editor = getSharedPreferences("PARAMETER",MODE_PRIVATE).edit();
                editor.putString("paramSearch",mSearchTerm.getText().toString());
                editor.apply();
            }
        });

        mEnableSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save to shared parameter the status of the switch
                SharedPreferences.Editor editor = getSharedPreferences("PARAMETER",MODE_PRIVATE).edit();
                editor.putBoolean("paramEnable",mEnableSwitch.isChecked());
                editor.apply();
            }
        });
    }

    /*-----------------
      Shared Preferences
     ------------------*/

    /**
     * Load parameter save in SharedPreference
     */
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
            e.printStackTrace();
            Toast.makeText(this,"ERROR: Can't load parameter",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Save to SharedPreference checkBox parameter
     * @param pIndex index of the checkbox
     */
    public void SharedSaver(int pIndex){
        //save in sharedPreferences the change
        try {
            SharedPreferences.Editor editor = getSharedPreferences("PARAMETER",MODE_PRIVATE).edit();
            editor.putBoolean("paramValue"+pIndex,CheckboxList.get(pIndex).isChecked());
            editor.putString("paramName"+pIndex,(String)CheckboxList.get(pIndex).getText());
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"ERROR: Can't save parameter",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        if(!mArtsBox.isChecked() & !mBusinessBox.isChecked() & !mClimateBox.isChecked() &
                !mPoliticsBox.isChecked() & !mSportsBox.isChecked() & !mTravelsBox.isChecked()){

            Toast.makeText(this,"You must choose one category",Toast.LENGTH_LONG).show();
        }else {
            super.onBackPressed();
        }
    }
}