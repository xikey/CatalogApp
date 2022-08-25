package com.zikey.android.razancatalogapp.ui.custom_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.zikey.android.razancatalogapp.R;
import com.zikey.android.razancatalogapp.core.Convertor;


/**
 * Created by Torabi on 9/8/2016.
 */

public class Indicator extends LinearLayout {

    private ViewPager2 pager;
    private int pageNumber = 0;
    private int selectedPage = 0;

    public int getSelectedPage() {
        return selectedPage;
    }

    public Indicator(Context context) {
        super(context);
        init();
    }

    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Indicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        if (isInEditMode())
            return;

        setOrientation(LinearLayout.HORIZONTAL);

        if (Build.VERSION.SDK_INT > 17)
            setLayoutDirection(LAYOUT_DIRECTION_LTR);
    }

    public void setViewPager(ViewPager2 pager) {
        if (pager == null)
            return;

        this.pager = pager;
        this.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selectedPage = position;
                for (int i = 0; i < getChildCount(); i++) {
                    getChildAt(i).setAlpha(0.5f);
                }

                getChildAt(position).setAlpha(1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        notifyDatabasChange();
    }

    public void notifyDatabasChange() {
        if (pager == null || pager.getAdapter() == null) {
            selectedPage = 0;
            pageNumber = 0;
            return;
        }

        selectedPage = pager.getCurrentItem();
        pageNumber = pager.getAdapter().getItemCount();
        initView();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        removeAllViews();

        for (int i = 0; i < pageNumber; i++) {
            View child = new View(getContext());
            LayoutParams params = new LayoutParams(Convertor.toPixcel(12, getContext()), Convertor.toPixcel(12, getContext()));

            if (i != 0) {
                int left = Convertor.toPixcel(5, getContext());
                if (Build.VERSION.SDK_INT < 17)
                    params.setMargins(left, 0, 0, 0);
                else
                    params.setMarginStart(left);
            }

            child.setLayoutParams(params);
            child.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_gray, null));

            if (selectedPage == i)
                child.setAlpha(1);
            else
                child.setAlpha(0.5f);
            addView(child);
        }
    }

}
