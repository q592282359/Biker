package com.leew.biker.util;

import android.content.Context;

/**
 * author:Leew
 * date:2018/10/19  17:42
 * vesion:1.0
 * description:
 */
public class DisplayUtils {



    public static int px2dp(Context context, float pxValue) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
