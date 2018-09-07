package com.debruyckere.florian.mynews.controller.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.debruyckere.florian.mynews.model.NewsDownload;

import java.util.ArrayList;


public class SearchResultFragment extends BaseFragment {

    private String mSearchTerm;
    private ArrayList<Boolean> mBoolList = new ArrayList<>();


    public SearchResultFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = this.getArguments();
        if(args != null) {
            bundleLoader(args);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * load search parameters from the bundle send by SearchActivity
     * @param pArgs bundle of parameters
     */
    public void bundleLoader(Bundle pArgs){
        mSearchTerm = pArgs.getString("SEARCHTERM","");
        Boolean mArt = pArgs.getBoolean("SEARCHART",false);
        Boolean mBusiness = pArgs.getBoolean("SEARCHBUSINESS",false);
        Boolean mPolitics = pArgs.getBoolean("SEARCHPOLITICS",false);
        Boolean mTravel = pArgs.getBoolean("SEARCHTRAVEL",false);
        Boolean mSport = pArgs.getBoolean("SEARCHSPORT",false);
        Boolean mClimate = pArgs.getBoolean("SEARCHCLIMATE",false);

        mBoolList.add(mArt);
        mBoolList.add(mBusiness);
        mBoolList.add(mPolitics);
        mBoolList.add(mTravel);
        mBoolList.add(mSport);
        mBoolList.add(mClimate);
    }

    /**
     * download news from NewYorkTimes API
     */
    @Override
    public void launchDownload() {
        new NewsDownload(this, "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=1ae7b601c1c7409796be77cce450f631", mSearchTerm, mBoolList)
                .execute();
    }
}
