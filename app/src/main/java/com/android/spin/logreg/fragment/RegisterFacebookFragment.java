package com.android.spin.logreg.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.base.BaseFragment;
import com.android.spin.R;

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
