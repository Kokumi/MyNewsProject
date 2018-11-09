package com.debruyckere.florian.mynews.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.debruyckere.florian.mynews.R;
import com.debruyckere.florian.mynews.controller.fragment.BaseFragment;
import com.debruyckere.florian.mynews.controller.fragment.SearchResultFragment;
import com.debruyckere.florian.mynews.model.PickersDialogs;

import butterknife.BindView;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.search_search_term)EditText mSearchTerm;
    @BindView(R.id.search_arts)CheckBox mArtsBox;
    @BindView(R.id.search_business)CheckBox mBusinessBox;
    @BindView(R.id.search_politics)CheckBox mPoliticsBox;
    @BindView(R.id.search_travel)CheckBox mTravelsBox;
    @BindView(R.id.search_sports)CheckBox mSportsBox;
    @BindView(R.id.search_climate)CheckBox mClimateBox;
    @BindView(R.id.search_button)Button mSearchButton;
    @BindView(R.id.btn_begin_date)Button mBeginDate;
    @BindView(R.id.btn_end_date)Button mEndDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar mToolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mSearchTerm = findViewById(R.id.search_search_term);
        mArtsBox = findViewById(R.id.search_arts);
        mBusinessBox = findViewById(R.id.search_business);
        mPoliticsBox = findViewById(R.id.search_politics);
        mTravelsBox = findViewById(R.id.search_travel);
        mSportsBox = findViewById(R.id.search_sports);
        mClimateBox = findViewById(R.id.search_climate);
        mSearchButton = findViewById(R.id.search_button);
        mBeginDate = findViewById(R.id.btn_begin_date);
        mEndDate = findViewById(R.id.btn_end_date);

        configureListener();


    }

    /**
     * create toolbar's options
     * @param menu toolbar's menu
     * @return the toolbar's options
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * add reaction to the toolbar's option
     * @param item option selected
     * @return good execution rapport
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.toolbar_search :
                Toast.makeText(this,"You are already there",Toast.LENGTH_LONG).show();
                return true;

            case R.id.toolbar_about:
                intent=new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;

            case R.id.toolbar_notification:
                intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                return true;

            case 16908332:      //button home
                this.finish();
                return true;

            default:
                Log.d("OPTION SELECT","Default item selected");
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * create reaction to the search button
     */
    public void configureListener(){

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSearch();
            }
        });
    }

    /**
     * pick up search parameter and send to SearchResultFragment
     */
    public void launchSearch(){
        Intent intent = new Intent(this, SearchResultFragment.class);
        Bundle bundle = new Bundle();


        if(!mSearchTerm.getText().toString().equals("")){
            Log.i("Search","there mSearchTerm Text");
            bundle.putString("SEARCHTERM",mSearchTerm.getText().toString());
        }
        if(mArtsBox.isChecked()){
            bundle.putBoolean("SEARCHART",mArtsBox.isChecked());
        }
        if(mBusinessBox.isChecked()){
            bundle.putBoolean("SEARCHBUSINESS",mBusinessBox.isChecked());
        }
        if(mPoliticsBox.isChecked()){
            bundle.putBoolean("SEARCHPOLITICS",mPoliticsBox.isChecked());
        }
        if(mTravelsBox.isChecked()){
            bundle.putBoolean("SEARCHTRAVEL",mTravelsBox.isChecked());
        }
        if(mSportsBox.isChecked()){
            bundle.putBoolean("SEARCHSPORT",mSportsBox.isChecked());
        }
        if(mClimateBox.isChecked()){
            bundle.putBoolean("SEARCHCLIMATE",mClimateBox.isChecked());
        }
        if(!mBeginDate.getText().toString().equals("Begin date")){
            bundle.putString("SEARCHBEGINDATE",DateUrlFormatter( mBeginDate.getText().toString()));
        }
        if(!mEndDate.getText().toString().equals("end Date")){
            bundle.putString("SEARCHENDDATE",DateUrlFormatter( mEndDate.getText().toString()));
        }

        intent.putExtra("SEARCHBUNDKE",bundle);

        BaseFragment fragment = new SearchResultFragment();
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.search_layout,fragment);
        trans.commit();
    }

    /**
     * set the date to the clicked button
     * @param view view of the clicked button
     */
    public void setDate(View view){
        Button clickedButton = view.findViewById(view.getId());

        PickersDialogs pickersDialogs = new PickersDialogs();
        pickersDialogs.setButton(clickedButton);
        pickersDialogs.show(this.getFragmentManager(),"date");
    }

    /**
     * change the date to the correct format
     * @param pDate date to correct
     * @return the formatted date
     */
    public String DateUrlFormatter(String pDate){
        String toReturn;
        toReturn = pDate.substring(0,4)+ pDate.substring(5,7) + pDate.substring(8);            //get Year
        
        return toReturn;
    }
}
