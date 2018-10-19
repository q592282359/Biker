package com.leew.biker.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * author:Leew
 * date:2018/10/17  14:21
 * vesion:1.0
 * description:
 */
public class User {

    private Application application;
    private SharedPreferences sharedPre;
    private SharedPreferences.Editor editor;

    public User(Application application) {
        this.application = application;
        sharedPre = application.getSharedPreferences("User", Context.MODE_PRIVATE);
        editor = sharedPre.edit();
    }

    public boolean IsLogin() {
        return sharedPre.getBoolean("islogin", false);
    }

    public void setIsLogin(boolean isLogin) {
        editor.putBoolean("islogin", isLogin);
        editor.apply();

    }

    public String getUsername() {
        return sharedPre.getString("username", "");
    }

    public String getPassword() {
        return sharedPre.getString("password", "");
    }

}
