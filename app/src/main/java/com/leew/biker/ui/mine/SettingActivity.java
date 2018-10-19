package com.leew.biker.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leew.biker.R;
import com.leew.biker.base.BaseActivity;
import com.leew.biker.base.PageManager;
import com.leew.biker.global.MyApplication;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * author:Leew
 * date:2018/10/18  14:44
 * vesion:1.0
 * description:
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.problems)
    TextView mProblems;
    @BindView(R.id.help)
    TextView mHelp;
    @BindView(R.id.version)
    TextView mVersion;
    @BindView(R.id.exit_login)
    TextView mExitLogin;

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mTitle.setText("设置");
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(v -> finish());
        mExitLogin.setOnClickListener(v -> {
            BmobUser.logOut();
            MyApplication.getInstance().mUser.setIsLogin(false);
            finish();
            EventBus.getDefault().post("exitlogin");
        });
    }


}
