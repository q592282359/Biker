package com.leew.biker.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;

import com.leew.biker.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import androidx.annotation.Nullable;

/**
 * author:Leew
 * date:2018/10/19  15:10
 * vesion:1.0
 * description:
 */
public class MySrollLayout extends LinearLayout {
    private boolean isOpen = false;
    private int instance;
    private int starty;
    private int height;

    public MySrollLayout(Context context) {
        super(context);
        init(context);

    }

    public MySrollLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public MySrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        height = windowManager.getDefaultDisplay().getHeight();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                starty = (int) event.getY();
                if(starty<1973&&!isOpen){
                    return false;
                }
                if(starty<1100&&isOpen){
                    return false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                instance = starty - (int) event.getY();
                scrollTo(0, instance);
                break;

            case MotionEvent.ACTION_UP:
                if(!isOpen){
                if (instance > 0 && instance < 263) {
                    scrollTo(0, 0);
                } else if (instance > 200) {
                    isOpen = true;
                    scrollTo(0, height/2-263);
                    resize();
                    EventBus.getDefault().post("refreshmapview");

                } else {
                    scrollTo(0, 0);
                }
                }else{

                   if(instance<0&&instance>-263) {
                       scrollTo(0,0);
                   }else if(instance<-263){
                       scrollTo(0,-(height/2-263));
                       isOpen = false;
                       EventBus.getDefault().post("refreshmapview");
                   }
                }
                starty = 0;
                break;
        }
        return true;
    }

    private void resize() {


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return true;
//    }
}
