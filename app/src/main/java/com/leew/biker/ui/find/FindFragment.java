package com.leew.biker.ui.find;

import android.os.Bundle;

import com.leew.biker.R;
import com.leew.biker.base.BaseFragment;

/**
 * author:Leew
 * date:2018/10/17  14:52
 * vesion:1.0
 * description:
 */
public class FindFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }
}
