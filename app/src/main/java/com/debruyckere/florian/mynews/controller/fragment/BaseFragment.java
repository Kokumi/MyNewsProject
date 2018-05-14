package com.debruyckere.florian.mynews.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debruyckere.florian.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    public static final String KEY_Position = "position";
    public static final String KEY_Color ="color";

    protected TextView mTextView;


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

        mTextView = result.findViewById(R.id.fragment_textview);

        return result;
    }

}
