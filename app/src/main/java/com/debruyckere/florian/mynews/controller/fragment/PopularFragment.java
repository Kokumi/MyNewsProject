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
public class PopularFragment extends BaseFragment {


    public PopularFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance(int position, int color){
        PopularFragment tSF = new PopularFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_Position,position);
        args.putInt(KEY_Color,color);
        tSF.setArguments(args);

        return tSF;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = super.onCreateView(inflater,container,savedInstanceState);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void launchDownload() {
        //key: 318107b72537430c89101c53511a08d0 || 318107b72537430c89101c53511a08d0 || 318107b72537430c89101c53511a08d0
        new NewsDownload(this,"https://api.nytimes.com/svc/mostpopular/v2/mostshared/all-sections/7.json?api-key=318107b72537430c89101c53511a08d0",
                getContext(),false)
                .execute();
    }
}
