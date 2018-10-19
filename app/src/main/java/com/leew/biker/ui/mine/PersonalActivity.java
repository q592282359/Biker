package com.leew.biker.ui.mine;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leew.biker.R;
import com.leew.biker.base.BaseActivity;
import com.leew.biker.bean.UserInfo;
import com.leew.biker.util.BitmapUtils;
import com.leew.biker.util.LogUtils;
import com.leew.biker.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author:Leew
 * date:2018/10/17  18:04
 * vesion:1.0
 * description:
 */
public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.head)
    CircleImageView mHead;
    @BindView(R.id.head_rl)
    RelativeLayout mHeadRl;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.name_rl)
    RelativeLayout mNameRl;
    @BindView(R.id.place)
    TextView mPlace;
    @BindView(R.id.place_rl)
    RelativeLayout mPlaceRl;
    @BindView(R.id.sex)
    TextView mSex;
    @BindView(R.id.sex_rl)
    RelativeLayout mSexRl;
    @BindView(R.id.age)
    TextView mAge;
    @BindView(R.id.age_rl)
    RelativeLayout mAgeRl;
    @BindView(R.id.high)
    TextView mHigh;
    @BindView(R.id.high_rl)
    RelativeLayout mHighRl;
    @BindView(R.id.weight)
    TextView mWeight;
    @BindView(R.id.weight_rl)
    RelativeLayout mWeightRl;
    @BindView(R.id.parent_ll)
    LinearLayout mParentLl;
    private PopupWindow ImgPopupWindow;
    private PopupWindow TextPopupWindow;
    private int width,height;
    private int[] heads = {R.mipmap.head1, R.mipmap.head2, R.mipmap.head3, R.mipmap.head4, R.mipmap.head5, R.mipmap.head6, R.mipmap.head7, R.mipmap.head8, R.mipmap.head9, R.mipmap.head10, R.mipmap.head11};

    @Override
    public int getContentViewId() {
        return R.layout.activity_personal;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        initdata();
        mTitle.setText("修改个人资料");
    }

    @SuppressLint("DefaultLocale")
    private void initdata() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels;
        height = outMetrics.heightPixels;



        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
        mName.setText(userInfo.getName());
        Glide.with(activity).load(userInfo.getHead().getFileUrl()).into(mHead);
        mPlace.setText(userInfo.getPlace());
        mSex.setText(userInfo.getSex());
        mAge.setText(String.format("%d", userInfo.getAge()));
        mHigh.setText(String.format("%d cm", userInfo.getHigh()));
        mWeight.setText(String.format("%d kg", userInfo.getWeight()));
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(this);
        mHeadRl.setOnClickListener(this);
        mNameRl.setOnClickListener(this);
        mPlaceRl.setOnClickListener(this);
        mAgeRl.setOnClickListener(this);
        mSexRl.setOnClickListener(this);
        mHighRl.setOnClickListener(this);
        mWeightRl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_rl:
                showimgpop();
                break;
            case R.id.name_rl:
                showtextpop(1);
                break;
            case R.id.place_rl:
                showtextpop(2);
                break;
            case R.id.sex_rl:
                showtextpop(3);
                break;
            case R.id.age_rl:
                showtextpop(4);
                break;
            case R.id.high_rl:
                showtextpop(5);
                break;
            case R.id.weight_rl:
                showtextpop(6);
                break;
            case R.id.back:
                finish();
                EventBus.getDefault().post("refreshmine");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post("refreshmine");
    }

    private void showtextpop(int i) {
        View textview = LayoutInflater.from(activity).inflate(R.layout.popupwindow_text, null, false);
        TextView title = textview.findViewById(R.id.pop_text_title);
        EditText edit = textview.findViewById(R.id.pop_text_edit);
        TextView ok = textview.findViewById(R.id.pop_text_ok);
        TextView no = textview.findViewById(R.id.pop_text_no);

        switch (i) {
            case 1:
                title.setText("修改昵称");
                edit.setText(mName.getText().toString());
                ok.setOnClickListener(v -> {
                    if (!edit.getText().toString().isEmpty()) {
                        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
                        userInfo.setName(edit.getText().toString());
                        userInfo.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    if (TextPopupWindow.isShowing()) {
                                        TextPopupWindow.dismiss();
                                    }
                                    ToastUtils.showToast("修改成功!");
                                    mName.setText(edit.getText().toString());
                                }
                            }
                        });
                    }
                });
                break;
            case 2:
                title.setText("修改地址");
                edit.setText(mPlace.getText().toString());
                ok.setOnClickListener(v -> {
                    if (!edit.getText().toString().isEmpty()) {
                        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
                        userInfo.setPlace(edit.getText().toString());
                        userInfo.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    if (TextPopupWindow.isShowing()) {
                                        TextPopupWindow.dismiss();
                                    }
                                    ToastUtils.showToast("修改成功!");
                                    mPlace.setText(edit.getText().toString());
                                }
                            }
                        });
                    }
                });
                break;
            case 3:
                title.setText("修改性别");
                edit.setText(mSex.getText().toString());
                ok.setOnClickListener(v -> {
                    if (!edit.getText().toString().isEmpty()) {
                        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
                        userInfo.setSex(edit.getText().toString());
                        userInfo.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    if (TextPopupWindow.isShowing()) {
                                        TextPopupWindow.dismiss();
                                    }
                                    ToastUtils.showToast("修改成功!");
                                    mSex.setText(edit.getText().toString());
                                }
                            }
                        });
                    }
                });
                break;
            case 4:
                title.setText("修改年龄");
                edit.setText(mAge.getText().toString());
                ok.setOnClickListener(v -> {
                    if (!edit.getText().toString().isEmpty()) {
                        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
                        userInfo.setAge(Integer.parseInt(edit.getText().toString()));
                        userInfo.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    if (TextPopupWindow.isShowing()) {
                                        TextPopupWindow.dismiss();
                                    }
                                    ToastUtils.showToast("修改成功!");
                                    mAge.setText(edit.getText().toString());
                                }
                            }
                        });
                    }
                });
                break;
            case 5:
                title.setText("修改身高");
                edit.setText(mHigh.getText().toString().split(" ")[0]);
                ok.setOnClickListener(v -> {
                    if (!edit.getText().toString().isEmpty()) {
                        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
                        userInfo.setHigh(Integer.parseInt(edit.getText().toString()));
                        userInfo.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    if (TextPopupWindow.isShowing()) {
                                        TextPopupWindow.dismiss();
                                    }
                                    ToastUtils.showToast("修改成功!");
                                    mHigh.setText(String.format("%s cm", edit.getText().toString()));
                                }
                            }
                        });
                    }
                });
                break;
            case 6:
                title.setText("修改体重");
                edit.setText(mWeight.getText().toString().split(" ")[0]);
                ok.setOnClickListener(v -> {
                    if (!edit.getText().toString().isEmpty()) {
                        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
                        userInfo.setWeight(Integer.parseInt(edit.getText().toString()));
                        userInfo.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    if (TextPopupWindow.isShowing()) {
                                        TextPopupWindow.dismiss();
                                    }
                                    ToastUtils.showToast("修改成功!");
                                    mWeight.setText(String.format("%s kg", edit.getText().toString()));
                                }
                            }
                        });
                    }
                });
                break;
        }

        no.setOnClickListener(v -> {
            if (TextPopupWindow.isShowing()) {
                TextPopupWindow.dismiss();
            }
        });
        TextPopupWindow = new PopupWindow(textview, width/5*4, height/6, true);
        TextPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        TextPopupWindow.setOutsideTouchable(true);
        TextPopupWindow.setTouchable(true);
        TextPopupWindow.showAtLocation(mParentLl, Gravity.CENTER, 0, 0);
    }

    private void showimgpop() {
        View imgview = LayoutInflater.from(activity).inflate(R.layout.popupwindow_img, null, false);
        ImgPopupWindow = new PopupWindow(imgview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ImgPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ImgPopupWindow.setOutsideTouchable(true);
        ImgPopupWindow.setTouchable(true);
        ImgPopupWindow.showAtLocation(mParentLl, Gravity.CENTER, 0, 0);
    }

    public void imgclick(View v) throws IOException {
        if (ImgPopupWindow.isShowing()) {
            ImgPopupWindow.dismiss();
        }
        BmobFile bmobFile = new BmobFile(BitmapUtils.saveBitmapFile(BitmapUtils.getimgeBitmap(v)));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
                    userInfo.setHead(bmobFile);
                    userInfo.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                ToastUtils.showToast("上传图片成功!");
                                Glide.with(activity).load(bmobFile.getFileUrl()).into(mHead);
                            } else {
                                ToastUtils.showToast("上传图片失败!");
                                LogUtils.e(e.toString());
                            }
                        }
                    });
                } else {
                    ToastUtils.showToast("上传图片失败!");
                    LogUtils.e(e.toString());
                }
            }
        });
    }


}
