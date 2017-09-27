package com.example.hanaj.kery.pagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hanaj.kery.fragment.DriveFragment;
import com.example.hanaj.kery.fragment.GpsFragment;
import com.example.hanaj.kery.fragment.WeightFragment;

/**
 * Created by tnghk on 2017-08-24.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private static int PAGE_NUMBER = 3;
    public PagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return DriveFragment.newInstance();
            case 1:
                return WeightFragment.newInstance();
            case 2:
                return GpsFragment.newInstance();
            default:
                return null;
        }

    }
    @Override
    public int getCount(){
        return PAGE_NUMBER;
    }
    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Drive";
            case 1:
                return "Weight";
            case 2:
                return "GPS";
            default:
                return null;
        }
    }


}