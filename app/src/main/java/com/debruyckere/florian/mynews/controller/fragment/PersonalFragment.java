package com.debruyckere.florian.mynews.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debruyckere.florian.mynews.R;
import com.debruyckere.florian.mynews.model.NewsDownload;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {


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
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void launchDownload() {
        // key: 318107b72537430c89101c53511a08d0
        //"https://api.nytimes.com/svc/search/v2/articlesearch.json"
        /*new NewsDownload(this,"https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=318107b72537430c89101c53511a08d0")
        .execute();*/
    }
}
