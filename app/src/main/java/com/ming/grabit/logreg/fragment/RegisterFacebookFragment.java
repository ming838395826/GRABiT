package com.ming.grabit.logreg.fragment;

import android.os.Bundle;

import com.android.base.base.BaseFragment;
import com.ming.grabit.R;

public class RegisterFacebookFragment extends BaseFragment {

    public RegisterFacebookFragment() {
        // Required empty public constructor
    }

    public static RegisterFacebookFragment newInstance() {
        RegisterFacebookFragment fragment = new RegisterFacebookFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_facebook;
    }

    @Override
    public void initView() {

    }
}
