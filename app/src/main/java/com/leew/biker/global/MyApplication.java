package com.leew.biker.global;

import android.app.Application;
import android.widget.Toast;

import cn.bmob.v3.Bmob;

/**
 * author:Leew
 * date:2018/10/1711:42
 * vesion:1.0
 * description:
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public Toast toast = null;
    public User mUser;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mUser = new User(this);

        Bmob.initialize(this,"27eaf32985a61374b44a3230fb2c2f3a");
    }

    public static MyApplication getInstance(){
        return instance;
    }
}
