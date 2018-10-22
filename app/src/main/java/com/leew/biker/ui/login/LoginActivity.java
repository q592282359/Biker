package com.leew.biker.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leew.biker.MainActivity;

import com.leew.biker.R;
import com.leew.biker.base.BaseActivity;
import com.leew.biker.base.PageManager;
import com.leew.biker.bean.UserInfo;
import com.leew.biker.ui.register.RegisterActivity;
import com.leew.biker.util.LogUtils;
import com.leew.biker.view.XEditText;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.RequiresApi;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * author:Leew
 * date:2018/10/17  15:31
 * vesion:1.0
 * description:
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_username)
    XEditText mEtUsername;
    @BindView(R.id.et_passWord)
    XEditText mEtPassWord;
    @BindView(R.id.login)
    TextView mLogin;
    @BindView(R.id.forget_password)
    TextView mForgetPassword;
    @BindView(R.id.register)
    TextView mRegister;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mLogin.setEnabled(false);
        mTitle.setText("登录");
    }

    @Override
    protected void setListener() {
        mEtUsername.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                if (!mEtUsername.getText().toString().isEmpty() && !mEtPassWord.getText().toString().isEmpty()) {
                    mLogin.setBackground(getResources().getDrawable(R.drawable.login_back1));
                    mLogin.setEnabled(true);
                } else {
                    mLogin.setBackground(getResources().getDrawable(R.drawable.login_back));
                    mLogin.setEnabled(false);
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
                if (!mEtPassWord.getText().toString().isEmpty() && !mEtUsername.getText().toString().isEmpty()) {
                    mLogin.setBackground(getResources().getDrawable(R.drawable.login_back1));
                    mLogin.setEnabled(true);

                } else {
                    mLogin.setBackground(getResources().getDrawable(R.drawable.login_back));
                    mLogin.setEnabled(false);
                }
            }
        });
        mLogin.setOnClickListener(v -> BmobUser.loginByAccount(mEtUsername.getText().toString(), mEtPassWord.getText().toString(), new LogInListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if (e == null) {
                    SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("islogin", true);
                    editor.putString("username", mEtUsername.getText().toString());
                    editor.putString("password", mEtPassWord.getText().toString());
                    editor.apply();
                    PageManager.getPageManager().finishAllActivity();
                    startActivity(new Intent(activity, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    LogUtils.e(e.toString());
                }
            }
        }));
        mBack.setOnClickListener(v -> finish());
        mRegister.setOnClickListener(v -> RegisterActivity.start(activity));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmsg(LoginEvent event) {
        mEtUsername.setText(event.getUsername());
        mEtPassWord.setText(event.getPassword());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public static class LoginEvent {
        private String username;
        private String password;

        public LoginEvent(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
