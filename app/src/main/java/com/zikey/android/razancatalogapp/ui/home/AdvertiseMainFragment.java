package com.zikey.android.razancatalogapp.ui.home;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.zikey.android.razancatalogapp.R;
import com.zikey.android.razancatalogapp.core.ImageViewWrapper;
import com.zikey.android.razancatalogapp.model.Advertise;

import java.util.List;


/**
 * Created by Torabi on 9/6/2016.
 */

public class AdvertiseMainFragment extends Fragment {

    private static final String ARG_PAGE_NUMBER = "page_number";
    private static final String ARG_PAGE_HEIGHT = "ARG_PAGE_HEIGHT";

    List<Advertise> advertiseList;

    public void setAdvertiseList(List<Advertise> advertiseList) {
        this.advertiseList = advertiseList;
    }

    public AdvertiseMainFragment() {
    }

    public static AdvertiseMainFragment newInstance(int page, int height, List<Advertise> advertises) {
        AdvertiseMainFragment fragment = new AdvertiseMainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        args.putInt(ARG_PAGE_HEIGHT, height);
        fragment.setArguments(args);
        fragment.setAdvertiseList(advertises);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_advertise_slider, container, false);

        final ImageView imgAdv = (ImageView) rootView.findViewById(R.id.imgAdv);

        int page = getArguments().getInt(ARG_PAGE_NUMBER, -1);
        int height = getArguments().getInt(ARG_PAGE_HEIGHT, 300);


        final int placeHolder = R.drawable.img_yadegar_loader;

        try {
//                    String url = SessionManagement.getInstance(getActivity()).getAdvertise_1Url();
            String url = advertiseList.get(page - 1).getImageUrl();
            new ImageViewWrapper(getActivity()).FromUrl(url).into(imgAdv).defaultImage(placeHolder).load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//                imgAdv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//
//                            UriParser.run(getActivity(), SessionManagement.getInstance(getActivity()).getAdvertise_1Uri());
//                        } catch (Exception ex) {
//                        ex.printStackTrace();
//                        }
//                    }
//                });


        final ViewGroup.LayoutParams params = imgAdv.getLayoutParams();
        params.height = height;
        imgAdv.setLayoutParams(params);

        return rootView;
    }


    public void onClick(View view) {
//        ProductActivity.openActivity(getActivity(), new ProductSummary());
    }


}
