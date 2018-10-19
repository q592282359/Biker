package com.leew.biker.ui.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leew.biker.R;
import com.leew.biker.base.BaseFragment;
import com.leew.biker.bean.UserInfo;
import com.leew.biker.global.MyApplication;
import com.leew.biker.ui.login.LoginActivity;
import com.leew.biker.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * author:Leew
 * date:2018/10/17 11:35
 * vesion:1.0
 * description:
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.message)
    ImageView mMessage;
    @BindView(R.id.setting)
    ImageView mSetting;
    @BindView(R.id.notification)
    ImageView mNotification;
    @BindView(R.id.user_head)
    CircleImageView mUserHead;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.guanzhu_nums)
    TextView mGuanzhuNums;
    @BindView(R.id.fensi_nums)
    TextView mFensiNums;
    @BindView(R.id.jifen_nums)
    TextView mJifenNums;
    @BindView(R.id.licheng)
    TextView mLicheng;
    @BindView(R.id.licheng_nums)
    TextView mLichengNums;
    @BindView(R.id.sport_time)
    TextView mSportTime;
    @BindView(R.id.sport_time_nums)
    TextView mSportTimeNums;
    @BindView(R.id.local_rick)
    TextView mLocalRick;
    @BindView(R.id.local_ricking)
    TextView mLocalRicking;
    @BindView(R.id.sport_history)
    TextView mSportHistory;
    @BindView(R.id.sport_history_ll)
    LinearLayout mSportHistoryLl;
    @BindView(R.id.achievement)
    TextView mAchievement;
    @BindView(R.id.achievement_ll)
    LinearLayout mAchievementLl;
    @BindView(R.id.mine_refresh)
    SmartRefreshLayout mMineRefresh;
    private boolean isLogin;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mMineRefresh.setEnableLoadmore(false);
        isLogin = MyApplication.getInstance().mUser.IsLogin();
        if (isLogin) {
            setdatas();
            mUserHead.setEnabled(true);
            mUserName.setEnabled(false);
        } else {
            mUserName.setClickable(true);
            mUserHead.setEnabled(false);
        }
    }

    private void cleardatas() {
        mUserName.setText(R.string.mine_login);
        Glide.with(context).load(R.mipmap.mine_head).into(mUserHead);
        mJifenNums.setText("0");
        mGuanzhuNums.setText("0");
        mFensiNums.setText("0");
    }

    @SuppressLint("DefaultLocale")
    private void setdatas() {
        mUserName.setText(UserInfo.getCurrentUser(UserInfo.class).getName());
        mJifenNums.setText(String.format("%d", UserInfo.getCurrentUser(UserInfo.class).getJifen()));
        mGuanzhuNums.setText(String.format("%s", UserInfo.getCurrentUser(UserInfo.class).getGuanzhu()));
        mFensiNums.setText(String.format("%d", UserInfo.getCurrentUser(UserInfo.class).getFensi()));
        Glide.with(context).load(UserInfo.getCurrentUser(UserInfo.class).getHead().getFileUrl()).into(mUserHead);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setListener() {
        mUserName.setOnClickListener(v -> LoginActivity.start(context));
        mSetting.setOnClickListener(v -> SettingActivity.start(context));
        mMineRefresh.setOnRefreshListener(refreshlayout -> {
            if (MyApplication.getInstance().mUser.IsLogin()) {
                setdatas();
            }
            mMineRefresh.finishRefresh();
        });
        mUserHead.setOnClickListener(v -> PersonalActivity.start(context));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmsg(String msg) {
        if (msg.equals("refreshmine")) {
            mMineRefresh.autoRefresh();
        } else if (msg.equals("exitlogin")) {
            cleardatas();
            mMineRefresh.autoRefresh();
            mUserName.setEnabled(true);
            mUserHead.setEnabled(false);
        }
    }

}
