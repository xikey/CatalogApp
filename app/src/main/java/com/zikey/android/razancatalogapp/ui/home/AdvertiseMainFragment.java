package com.zikey.android.razancatalogapp.ui.home;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.zikey.android.razancatalogapp.R;
import com.zikey.android.razancatalogapp.core.ImageViewWrapper;


public class AdvertiseMainFragment extends Fragment {

    private static final String ARG_PAGE_NUMBER = "page_number";
    private static final String ARG_PAGE_HEIGHT = "ARG_PAGE_HEIGHT";
    private static final String ARG_IMAGE_URL = "ARG_IMAGE_URL";


    public static AdvertiseMainFragment newInstance(int page, int height, String imageUrl) {
        AdvertiseMainFragment fragment = new AdvertiseMainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        args.putInt(ARG_PAGE_HEIGHT, height);
        args.putString(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_advertise_slider, container, false);

        final ImageView imgAdv = (ImageView) rootView.findViewById(R.id.imgAdv);

        int page = getArguments().getInt(ARG_PAGE_NUMBER, -1);
        int height = getArguments().getInt(ARG_PAGE_HEIGHT, 300);
        String imageUrl = getArguments().getString(ARG_IMAGE_URL, "");


        final int placeHolder = R.drawable.img_yadegar_loader;

        try {

            new ImageViewWrapper(getActivity()).FromUrl(imageUrl).into(imgAdv).defaultImage(placeHolder).load();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        final ViewGroup.LayoutParams params = imgAdv.getLayoutParams();
        params.height = height;
        imgAdv.setLayoutParams(params);

        return rootView;
    }




}
