package com.leew.biker.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * author:Leew
 * date:2018/10/19  14:48
 * vesion:1.0
 * description:
 */
public class MyFooterBehavior extends CoordinatorLayout.Behavior<View> {

    public MyFooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY = -dependency.getY();
        child.setTranslationY(translationY);
        return true;
    }
}
