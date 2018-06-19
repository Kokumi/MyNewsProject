package com.debruyckere.florian.mynews.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.debruyckere.florian.mynews.R;

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar mToolbar = findViewById(R.id.search_toolbar);
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
                Toast.makeText(this,"You are already there",Toast.LENGTH_LONG).show();
                return true;

            case R.id.toolbar_about:
                Intent intentAbout=new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                return true;

            case R.id.toolbar_notification:
                Intent intentNotification = new Intent(this, NotificationActivity.class);
                startActivity(intentNotification);
                return true;

            case 16908332:      //button home
                this.finish();
                return true;

            default:
                Log.d("OPTION SELECT","Default item selected");
                return super.onOptionsItemSelected(item);
        }
    }
}
