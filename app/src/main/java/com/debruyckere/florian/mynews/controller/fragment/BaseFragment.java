package com.debruyckere.florian.mynews.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.debruyckere.florian.mynews.R;
import com.debruyckere.florian.mynews.model.News;
import com.debruyckere.florian.mynews.model.NewsAdapter;
import com.debruyckere.florian.mynews.model.NewsDownload;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements NewsDownload.Listeners {

    public static final String KEY_Position = "position";
    public static final String KEY_Color ="color";

    @BindView(R.id.fragment_recyclerview) RecyclerView mRecyclerView;
    private NewsDownload mNewsDownload;

    //protected RecyclerView mRecyclerView;


    public BaseFragment() {
        // Required empty public constructor
    }

    /*public static BaseFragment newInstance(int position, int color){
        BaseFragment bFrag = new BaseFragment();
        Bundle args=new Bundle();

        args.putInt(KEY_Position,position);
        args.putInt(KEY_Color,color);
        bFrag.setArguments(args);

        return bFrag;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_base, container, false);

        mRecyclerView = result.findViewById(R.id.fragment_recyclerview);

        configureRecyclerView();
        launchDownload();

        return result;
    }

    public void configureRecyclerView(){
        Log.i("BASE FRAGMENT","configure recyclerView");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public abstract void launchDownload();

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute(ArrayList<News> news) {
        mRecyclerView.setAdapter(new NewsAdapter(news));
    }
}
