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


    public SearchResultFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = this.getArguments();
        if(args != null) {
            bundleLoader(args);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static BaseFragment newInstance(int position, int color){
        SearchResultFragment tSF = new SearchResultFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_Position,position);
        args.putInt(KEY_Color,color);
        tSF.setArguments(args);

        return tSF;
    }

    public void bundleLoader(Bundle pArgs){
        mSearchTerm = pArgs.getString("SEARCHTERM","");
        Boolean mArt = pArgs.getBoolean("SEARCHART",false);
        Boolean mBusiness = pArgs.getBoolean("SEARCHBUSINESS",false);
        Boolean mPolitics = pArgs.getBoolean("SEARCHPOLITICS",false);
        Boolean mTravel = pArgs.getBoolean("SEARCHTRAVEL",false);
        Boolean mSport = pArgs.getBoolean("SEARCHSPORT",false);
        Boolean mEntrepreneur = pArgs.getBoolean("SEARCHENTREPRENEUR",false);

        mBoolList.add(mArt);
        mBoolList.add(mBusiness);
        mBoolList.add(mPolitics);
        mBoolList.add(mTravel);
        mBoolList.add(mSport);
        mBoolList.add(mEntrepreneur);
    }

    @Override
    public void launchDownload() {
        new NewsDownload(this, "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=1ae7b601c1c7409796be77cce450f631", mSearchTerm, mBoolList)
                .execute();
    }
}
