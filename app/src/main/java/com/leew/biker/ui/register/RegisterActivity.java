package com.leew.biker.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.widget.ImageView;
import android.widget.TextView;

import com.leew.biker.R;
import com.leew.biker.base.BaseActivity;
import com.leew.biker.bean.UserInfo;
import com.leew.biker.ui.login.LoginActivity;
import com.leew.biker.util.LogUtils;
import com.leew.biker.util.StringUtils;
import com.leew.biker.util.ToastUtils;
import com.leew.biker.view.TimeButton;
import com.xw.repo.XEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * author:Leew
 * date:2018/10/17  16:04
 * vesion:1.0
 * description:
 */
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.et_username)
    XEditText mEtUsername;
    @BindView(R.id.et_passWord)
    XEditText mEtPassWord;
    @BindView(R.id.et_verify_code)
    XEditText mEtVerifyCode;
    @BindView(R.id.checkcode)
    TimeButton mCheckcode;
    @BindView(R.id.register)
    TextView mRegister;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mTitle.setText("手机号注册");
        mRegister.setEnabled(false);
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(v -> finish());
        mRegister.setOnClickListener(v -> {
            //注册
            BmobUser bmobUser = new BmobUser();
            bmobUser.setUsername(mEtUsername.getText().toString());
            bmobUser.setPassword(mEtPassWord.getText().toString());
            bmobUser.signUp(new SaveListener<UserInfo>() {
                @Override
                public void done(UserInfo userInfo, BmobException e) {
                    if (e == null) {
                        userInfo.setName(mEtUsername.getText().toString());
                        userInfo.setJifen(100);
                        userInfo.setFensi(0);
                        userInfo.setGuanzhu(0);
                        userInfo.save();
                        ToastUtils.showToast("注册成功!");
                        finish();
                        LoginActivity.LoginEvent event = new LoginActivity.LoginEvent(mEtUsername.getText().toString(), mEtPassWord.getText().toString());
                        EventBus.getDefault().post(event);
                    } else {
                        LogUtils.e(e.toString());
                    }
                }
            });
        });
        mEtUsername.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                if (!mEtUsername.getText().toString().isEmpty() && !mEtPassWord.getText().toString().isEmpty() && !mEtVerifyCode.getText().toString().isEmpty()) {
                    mRegister.setBackground(getResources().getDrawable(R.drawable.login_back1));
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setBackground(getResources().getDrawable(R.drawable.login_back));
                    mRegister.setEnabled(false);
                }
            }
        });
        mEtPassWord.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                if (!mEtVerifyCode.getText().toString().isEmpty() && !mEtPassWord.getText().toString().isEmpty() && !mEtUsername.getText().toString().isEmpty()) {
                    mRegister.setBackground(getResources().getDrawable(R.drawable.login_back1));
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setBackground(getResources().getDrawable(R.drawable.login_back));
                    mRegister.setEnabled(false);
                }
            }
        });
        mEtVerifyCode.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                if (!mEtVerifyCode.getText().toString().isEmpty() && !mEtPassWord.getText().toString().isEmpty() && !mEtUsername.getText().toString().isEmpty()) {
                    mRegister.setBackground(getResources().getDrawable(R.drawable.login_back1));
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setBackground(getResources().getDrawable(R.drawable.login_back));
                    mRegister.setEnabled(false);
                }
            }
        });
        mCheckcode.setOnClickListener(v -> {
            boolean phoneNumber = StringUtils.isPhoneNumber(mEtUsername.getText().toString());
            mCheckcode.performOnClick(phoneNumber);
        });
        mCheckcode.setOnSendCheckCodeListener(() -> {
            boolean phoneNumber = StringUtils.isPhoneNumber(mEtUsername.getText().toString());
            mCheckcode.performOnClick(phoneNumber);
        });
        mCheckcode.setOnFinishListener(() -> mCheckcode.setText("重新发送"));
        mCheckcode.setOnSendCheckCodeListener(() -> {
        });
    }
}
