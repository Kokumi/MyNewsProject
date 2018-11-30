package com.debruyckere.florian.mynews.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.debruyckere.florian.mynews.R;
import com.debruyckere.florian.mynews.model.News;
import com.debruyckere.florian.mynews.model.NewsAdapter;
import com.debruyckere.florian.mynews.model.NewsDownload;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements NewsDownload.Listeners {

    public static final String KEY_Position = "position";
    public static final String KEY_Color ="color";

    @BindView(R.id.fragment_recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_progress) ProgressBar mProgressBar;


    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_base, container, false);

        mRecyclerView = result.findViewById(R.id.fragment_recyclerview);
        mProgressBar = result.findViewById(R.id.fragment_progress);

        configureRecyclerView();
        launchDownload();

        return result;
    }

    /**
     * create the recycler view to see news
     */
    public void configureRecyclerView(){
        Log.i("BASE FRAGMENT","configure recyclerView");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(null);
    }

    public abstract void launchDownload();

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {

    }

    /**
     * show the news downloaded
     * @param news downloaded news
     */
    @Override
    public void onPostExecute(ArrayList<News> news) {
        mRecyclerView.setAdapter(new NewsAdapter(news));
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
