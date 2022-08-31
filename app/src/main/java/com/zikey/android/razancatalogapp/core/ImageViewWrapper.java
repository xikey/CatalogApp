package com.zikey.android.razancatalogapp.core;

import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;


/**
 * Created by Zikey on 11/07/2017.
 */

public class ImageViewWrapper {

    private ImageView imgView;
    private String url;
    private File file;
    private Context context;
    private int placeHolder;

    public ImageViewWrapper defaultImage(int placeHolder) {
        this.placeHolder = placeHolder;
        return this;
    }

    public ImageViewWrapper(Context context) {
        this.context = context;
    }

    public ImageViewWrapper into(ImageView view) {
        this.imgView = view;
        return this;
    }


    public ImageViewWrapper FromUrl(String url) {
        this.url = url;
        return this;
    }

    public ImageViewWrapper FromFile(File file) {
        this.file = file;
        return this;
    }

    public void load() {

        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(imgView);

    }

    public void loadCenterInside() {

        Glide.with(context)
                .load(url)
                .centerInside()
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(imgView);

    }

    public void loadFromDrowable(int drowable) {

        Glide.with(context).load(drowable).centerCrop().into(imgView);

    }

    public void loadCircleTransform() {

        Glide.with(context)
                .load(url)
                .fitCenter()
                .circleCrop() // Applies CircleCrop to all default types and throws an exception if asked to transform an unknown type.
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(imgView);

    }




    public void loadCenterCrop() {

        Glide.with(context)
                .load(url)
                .centerCrop() // scale to fill the ImageView and crop any extra
                .error(placeHolder)
                .into(imgView);

    }


}
