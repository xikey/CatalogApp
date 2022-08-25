package com.zikey.android.razancatalogapp.ui.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.zikey.android.razancatalogapp.model.Advertise;
import com.zikey.android.razancatalogapp.ui.home.AdvertiseMainFragment;

import java.util.List;

public class MainAdvertisePagerAdapter extends FragmentStateAdapter {

    private int count = 0;
    private int height = 250;
    private List<Advertise> advertises = null;

    public void setCount(int count) {
        this.count = count;
    }

    public void setAdvertises(List<Advertise> advertises) {
        this.advertises = advertises;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public MainAdvertisePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return AdvertiseMainFragment.newInstance(position + 1, height, advertises.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return count;
    }
}
