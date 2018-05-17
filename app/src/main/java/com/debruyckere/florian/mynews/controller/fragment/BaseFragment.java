package com.debruyckere.florian.mynews.controller.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debruyckere.florian.mynews.R;
import com.debruyckere.florian.mynews.model.News;
import com.debruyckere.florian.mynews.model.NewsAdapter;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    public static final String KEY_Position = "position";
    public static final String KEY_Color ="color";

    @BindView(R.id.fragment_recyclerview) RecyclerView mRecyclerView;
    //protected RecyclerView mRecyclerView;


    public BaseFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance(int position, int color){
        BaseFragment bFrag = new BaseFragment();
        Bundle args=new Bundle();

        args.putInt(KEY_Position,position);
        args.putInt(KEY_Color,color);
        bFrag.setArguments(args);

        return bFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_base, container, false);

        mRecyclerView = result.findViewById(R.id.fragment_recyclerview);

        configureRecyclerView();

        return result;
    }

    public void configureRecyclerView(){
        Log.i("BASE FRAGMENT","configure recyclerView");
        News testNews = new News("Android P real name is Android Pancake",
                "Technologie/Android",new Date(),"nep","Place");
        ArrayList<News> testArray = new ArrayList<>();
        testArray.add(testNews);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new NewsAdapter(testArray));
    }

}
