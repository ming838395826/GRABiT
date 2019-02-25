package com.ming.grabit.common.selector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.base.base.BaseUIActivity;

import java.io.File;

/**
 * Multi image selector
 * Created by Nereo on 2015/4/7.
 * Updated by nereo on 2016/1/19.
 * Updated by nereo on 2016/5/18.
 */
public class MultiImageSelectorActivity extends BaseUIActivity<MultiImageSelectorDelegate> implements  MultiImageSelectorFragment.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void star(Context mContext, Bundle mBundle, int requestCode) {
        Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
        intent.putExtras(mBundle);
        ((Activity) mContext).startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutStyle() {
        return TITLEBARSTYLE_BELW;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initHeaderView() {
        setHeaderTitle("相册");
        if(viewDelegate != null){
            viewDelegate.initHeaderView();
        }
    }

    @Override
    protected Class<MultiImageSelectorDelegate> getDelegateClass() {
        return MultiImageSelectorDelegate.class;
    }

    @Override
    public void onSingleImageSelected(String path) {
        viewDelegate.onSingleImageSelected(path);
    }

    @Override
    public void onImageSelected(String path) {
        viewDelegate.onImageSelected(path);
    }

    @Override
    public void onImageUnselected(String path) {
        viewDelegate.onImageUnselected(path);
    }

    @Override
    public void onCameraShot(File imageFile) {
        viewDelegate.onCameraShot(imageFile);
    }

    @Override
    public void onClickCamera() {
        viewDelegate.onClickCamera();
    }
}
