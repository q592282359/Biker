package com.leew.biker;


import android.content.Intent;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.leew.biker.base.BaseActivity;
import com.leew.biker.bean.UserInfo;
import com.leew.biker.global.MyApplication;
import com.leew.biker.global.User;
import com.leew.biker.util.LogUtils;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


public class SplashActivity extends BaseActivity {

    private LinearLayout layout;

    @Override
    public int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

        layout = findViewById(R.id.splash_ll);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                User user = MyApplication.getInstance().mUser;
                if (user.IsLogin()) {
                    BmobUser.loginByAccount(user.getUsername(), user.getPassword(), new LogInListener<UserInfo>() {
                        @Override
                        public void done(UserInfo userInfo, BmobException e) {
                            if (e == null) {

                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            } else {
                                user.setIsLogin(false);
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        layout.setAnimation(alphaAnimation);
    }

    @Override
    protected void setListener() {

    }



}
