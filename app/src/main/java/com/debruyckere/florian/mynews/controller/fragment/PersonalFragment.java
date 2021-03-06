package com.debruyckere.florian.mynews.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {

    @BindView(R.id.fragment_Warning_Parameter)TextView mWarningText;

    public PersonalFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance(int position, int color){
        PersonalFragment tSF = new PersonalFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_Position,position);
        args.putInt(KEY_Color,color);
        tSF.setArguments(args);

        return tSF;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);

        mWarningText = view.findViewById(R.id.fragment_Warning_Parameter);
        mWarningText.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void launchDownload() {
        // key: 318107b72537430c89101c53511a08d0
        //"https://api.nytimes.com/svc/search/v2/articlesearch.json"
        /*new NewsDownload(this,"https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=318107b72537430c89101c53511a08d0")
        //https://api.nytimes.com/svc/topstories/v2/home.json?api-key=1ae7b601c1c7409796be77cce450f631*/

        new NewsDownload(this,"https://api.nytimes.com/svc/topstories/v2/home.json?api-key=1ae7b601c1c7409796be77cce450f631",
                getContext(),true)
        .execute();
    }

    @Override
    public void onPostExecute(ArrayList<News> news) {
        super.onPostExecute(news);
        mWarningText.setVisibility(View.GONE);
    }
}
