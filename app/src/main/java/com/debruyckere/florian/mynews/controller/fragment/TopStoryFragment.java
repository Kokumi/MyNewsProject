package com.debruyckere.florian.mynews.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debruyckere.florian.mynews.R;
import com.debruyckere.florian.mynews.model.NewsDownload;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoryFragment extends BaseFragment {


    public TopStoryFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance(int position, int color){
        TopStoryFragment tSF = new TopStoryFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_Position,position);
        args.putInt(KEY_Color,color);
        tSF.setArguments(args);

        return tSF;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = super.onCreateView(inflater,container,savedInstanceState);


        return result;
    }

    @Override
    public void launchDownload() {
        new NewsDownload(this, "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=1ae7b601c1c7409796be77cce450f631")
                .execute();
    }
}
