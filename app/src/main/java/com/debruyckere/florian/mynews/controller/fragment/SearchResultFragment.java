package com.debruyckere.florian.mynews.controller.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debruyckere.florian.mynews.R;
import com.debruyckere.florian.mynews.model.News;
import com.debruyckere.florian.mynews.model.NewsDownload;

import java.util.ArrayList;
import butterknife.BindView;


public class SearchResultFragment extends BaseFragment {

    private String mSearchTerm;
    private String mBeginDate;
    private String mEndDate;
    @BindView(R.id.fragment_Warning_Parameter)TextView mWarningText;
    private ArrayList<Boolean> mBoolList = new ArrayList<>();


    public SearchResultFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = this.getArguments();
        if(args != null)
            bundleLoader(args);

        View view = super.onCreateView(inflater,container,savedInstanceState);
        mWarningText = view.findViewById(R.id.fragment_Warning_Parameter);

        return view;
    }

    /**
     * load search parameters from the bundle send by SearchActivity
     * @param pArgs bundle of parameters
     */
    public void bundleLoader(Bundle pArgs){

        mSearchTerm = pArgs.getString("SEARCHTERM","");
        mBeginDate = pArgs.getString("SEARCHBEGINDATE","");
        mEndDate = pArgs.getString("SEARCHENDDATE","");
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
     * download news from NewYorkTimes API with received params
     */
    @Override
    public void launchDownload() {
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=318107b72537430c89101c53511a08d0";

        if(mSearchTerm.length() != 0) url+=("&q="+mSearchTerm);
        if(mBeginDate.length()!=0) url+="&begin_date="+mBeginDate;
        if(mEndDate.length()!=0) url+="&end_date="+mEndDate;
        url+="&sort=newest";

        Log.d("LaunchDownload","url: "+url);

        new NewsDownload(this,url,"",mBoolList)
        .execute();
    }

    @Override
    public void onPostExecute(ArrayList<News> news) {
        if(news.size()==0){
            String s = "nothing found";
            mWarningText.setText(s);
            mWarningText.setVisibility(View.VISIBLE);
        }

        super.onPostExecute(news);
    }


}
