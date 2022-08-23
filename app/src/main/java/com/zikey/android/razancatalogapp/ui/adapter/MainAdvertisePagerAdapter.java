package com.zikey.android.razancatalogapp.ui.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.zikey.android.razancatalogapp.model.Advertise;
import com.zikey.android.razancatalogapp.ui.home.AdvertiseMainFragment;

import java.util.List;

public class MainAdvertisePagerAdapter extends FragmentPagerAdapter {
    int height = 300;
    int count = 0;
    List<Advertise> advertises;

    public MainAdvertisePagerAdapter(FragmentManager fm, int height, int count, List<Advertise> advertises) {
        super(fm);
        this.height = height;
        this.count = count;
        this.advertises = advertises;
    }

    @Override
    public Fragment getItem(int position) {
        return AdvertiseMainFragment.newInstance(position + 1, height, advertises);
    }

    @Override
    public int getCount() {
        return count;
    }

}
