package com.debruyckere.florian.mynews.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.debruyckere.florian.mynews.controller.fragment.BaseFragment;
import com.debruyckere.florian.mynews.controller.fragment.PersonalFragment;
import com.debruyckere.florian.mynews.controller.fragment.PopularFragment;
import com.debruyckere.florian.mynews.controller.fragment.TopStoryFragment;

/**
 * Created by Debruyckère Florian on 10/05/2018.
 */
public class PageAdapter extends FragmentPagerAdapter {

    private int[] colors;

    public PageAdapter(FragmentManager mgr, int[] colors){
        super(mgr);
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment bF;
        switch (position){
            case 0: bF = TopStoryFragment.newInstance(position, this.colors[position]);
            break;
            case 1: bF = PopularFragment.newInstance(position,this.colors[position]);
                break;
            case 2: bF = PersonalFragment.newInstance(position,this.colors[position]);
                break;
            default: bF = new BaseFragment();
                break;
        }

        return bF;
    }
}
