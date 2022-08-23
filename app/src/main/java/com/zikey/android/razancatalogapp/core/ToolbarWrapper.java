package com.zikey.android.razancatalogapp.core;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.zikey.android.razancatalogapp.R;


/**
 * Created by Zikey on 01/06/2017.
 */

public class ToolbarWrapper {



    Toolbar toolbar;

    private AppCompatActivity act;

    public ToolbarWrapper(AppCompatActivity act) {
        this.act = act;
    }

    public void initToolbarWithBackArrow(int toolbarID, String title, final View.OnClickListener onClickListener) {

        Toolbar toolbar = (Toolbar) act.findViewById(toolbarID);
        act.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            if (onClickListener != null)
                onClickListener.onClick(v);
            else act.finish();
        }

        );
        act.setTitle(title);
        act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        act.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_forward_white_24dp);
    }

}
