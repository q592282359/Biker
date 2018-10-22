package com.leew.biker.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * author:Leew
 * date:2018/10/22  10:18
 * vesion:1.0
 * description:
 */
public class PropertyAnimation {
    private float mDensity;

    private int mHiddenViewMeasuredHeight; //点击箭头的时候，需要隐藏的控件最终到达一个高度，
    // 这个就是我们要控件到达的目标值。

    public PropertyAnimation(Context context){
        //点击箭头的时候，需要隐藏的控件最终到达一个高度，这个就是我们的目标值，只需要通过布局中的dp转换成像素就行了。
        mDensity = context.getResources().getDisplayMetrics().density;
        mHiddenViewMeasuredHeight = (int) (mDensity * 220 + 0.5);
    }

    public void animateOpen(View v) {
        v.setVisibility(View.VISIBLE);
        //createDropAnimator()自定义的一个动画效果函数
        ValueAnimator animator = createDropAnimator(v, 0,
                mHiddenViewMeasuredHeight);
        animator.start();
    }

    /**
     * 给控制动画的箭头设置动画.
     * 给箭头设置向上的动画
     * @param view  控件
     */
    public void animationIvOpen(View view) {
        //旋转动画，参数说明：new RotateAnimation(旋转的开始角度,旋转的结束角度,X轴的伸缩模式:可以取值为ABSOLUTE、
        // RELATIVE_TO_SELF、RELATIVE_TO_PARENT,X坐标的伸缩值,Y轴的伸缩模式:可以取值为ABSOLUTE、RELATIVE_TO_SELF、
        // RELATIVE_TO_PARENT,Y坐标的伸缩值);
        RotateAnimation animation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        //动画执行完后是否停留在执行完的状态
        animation.setFillAfter(true);
        //持续时间
        animation.setDuration(100);
        //为箭头图片绑定动画
        view.startAnimation(animation);
    }

    /**
     * 给控制动画的箭头设置动画.
     * 给箭头设置向下的动画
     * @param view
     */
    public void animationIvClose(View view) {
        RotateAnimation animation = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(100);
        view.startAnimation(animation);
    }

    /**
     * 设置隐藏动画
     *
     * @param view //动画作用的控件
     */
    public void animateClose(final View view) {
        //获得控件的高度
        int origHeight = view.getHeight();
        //createDropAnimator()自定义的一个动画效果函数
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        //如果你不想实现Animator.AnimatorListener中的所有接口，你可以通过继承AnimatorListenerAdapter。
        //AnimatorListenerAdapter类为所有的方法提供了一个空实现，所以你可以根据需要实现你需要的，覆盖AnimatorListenerAdapter原来的方法
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {  //动画结束时调用
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    /**
     * 自定义的动画效果
     *
     * @param v   //动画作用的控件
     * @param start //动画的开始值
     * @param end  //动画的结束值
     * @return
     */
    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        //这里我们利用ValueAnimator.ofInt创建了一个值从start到end的动画
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        //为ValueAnimator注册AnimatorUpdateListener监听器，在该监听器中可以
        // 监听ValueAnimator计算出来的值的改变，并将这些值应用到指定对象
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                //获取动画当前值
                int value = (int) arg0.getAnimatedValue();
                //得到控件的属性集合
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                //设置控件的高属性
                layoutParams.height = value;
                //把属性绑定到需要动画的控件上
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

}
