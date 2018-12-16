package com.android.spin.common.selector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.android.spin.R;

import java.util.ArrayList;

/**
 * 图片选择器
 * Created by nereo on 16/3/17.
 */
public class MultiImageSelector {

    public static final String EXTRA_RESULT = MultiImageSelectorDelegate.EXTRA_RESULT;

    private boolean mShowCamera = true;
    private int mMaxCount = 9;
    private int mMode = MultiImageSelectorDelegate.MODE_MULTI;
    private ArrayList<String> mOriginData;
    private static MultiImageSelector sSelector;

    private boolean isNeedCrop = true;

    @Deprecated
    private MultiImageSelector(Context context){

    }

    private MultiImageSelector(){}

    @Deprecated
    public static MultiImageSelector create(Context context){
        if(sSelector == null){
            sSelector = new MultiImageSelector(context);
        }
        return sSelector;
    }

    public static MultiImageSelector create(){
        if(sSelector == null){
            sSelector = new MultiImageSelector();
        }
        return sSelector;
    }

    public MultiImageSelector showCamera(boolean show){
        mShowCamera = show;
        return sSelector;
    }

    public MultiImageSelector count(int count){
        mMaxCount = count;
        return sSelector;
    }

    public MultiImageSelector isNeedCrop(boolean isNeedCrop){
        this.isNeedCrop = isNeedCrop;
        return sSelector;
    }

    public MultiImageSelector single(){
        mMode = MultiImageSelectorDelegate.MODE_SINGLE;
        return sSelector;
    }

    public MultiImageSelector multi(){
        mMode = MultiImageSelectorDelegate.MODE_MULTI;
        return sSelector;
    }

    public MultiImageSelector origin(ArrayList<String> images){
        mOriginData = images;
        return sSelector;
    }

    public void start(Activity activity, int requestCode){
        final Context context = activity;
        if(hasPermission(context)) {
//            activity.startActivityForResult(createIntent(context), requestCode);
            toPhoto(activity,requestCode);
        }else{
            Toast.makeText(context, R.string.mis_error_no_permission, Toast.LENGTH_SHORT).show();
        }
    }

    public void start(Fragment fragment, int requestCode){
        final Context context = fragment.getContext();
        if(hasPermission(context)) {
            fragment.startActivityForResult(createIntent(context), requestCode);
        }else{
            Toast.makeText(context, R.string.mis_error_no_permission, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPermission(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            // Permission was added in API Level 16
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private Intent createIntent(Context context){
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorDelegate.EXTRA_SHOW_CAMERA, mShowCamera);
        intent.putExtra(MultiImageSelectorDelegate.EXTRA_SELECT_COUNT, mMaxCount);
        intent.putExtra("isNeedCrop",isNeedCrop);
        if(mOriginData != null){
            intent.putStringArrayListExtra(MultiImageSelectorDelegate.EXTRA_DEFAULT_SELECTED_LIST, mOriginData);
        }
        intent.putExtra(MultiImageSelectorDelegate.EXTRA_SELECT_MODE, mMode);
        return intent;
    }

    private void toPhoto(Context context,int requestCode){
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("isNeedCrop",isNeedCrop);
        mBundle.putInt(MultiImageSelectorDelegate.EXTRA_SELECT_COUNT, mMaxCount);
        mBundle.putBoolean(MultiImageSelectorDelegate.EXTRA_SHOW_CAMERA, mShowCamera);
        if(mOriginData != null){
            mBundle.putStringArrayList(MultiImageSelectorDelegate.EXTRA_DEFAULT_SELECTED_LIST, mOriginData);
        }
        mBundle.putInt(MultiImageSelectorDelegate.EXTRA_SELECT_MODE, mMode);
        MultiImageSelectorActivity.star(context,mBundle,requestCode);
//        PanelManager.getInstance().switchPanelForResult(context, PanelFormManager.ID_MULTI_IMAGE_SELECTOR,mBundle,requestCode);
    }
}
