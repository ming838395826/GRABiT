package com.android.base.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.android.base.R;
import com.android.base.permission.EasyPermissions;
import com.android.base.permission.iface.CheckPermListener;

import java.util.List;

/**
 * 作者：yangqiyun
 * 时间：2017/6/11
 * 1120389276@qq.com
 * 描述：做系统设备及版本适配activity
 */

public class BaseAdaptateActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
    //请求权限码
    protected  int requestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adaptateStateView();
    }



    /**
     * 适配状态栏及虚拟键盘
     */
    private void adaptateStateView(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//A
//        //导航栏 @ 底部
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//B
    }


    /**
     * 权限回调接口
     */
    private CheckPermListener mListener;

    public void checkPermission(CheckPermListener listener, int requestCode,int resString, String... mPerms) {
        mListener = listener;
        this.requestCode = requestCode;
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            if (mListener != null)
                mListener.superPermission(requestCode);
        } else {
            EasyPermissions.requestPermissions(this, getString(resString),
                    requestCode, mPerms);
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EasyPermissions.SETTINGS_REQ_CODE) {
            //设置返回
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //同意了某些权限可能不是全部
    }

    @Override
    public void onPermissionsAllGranted() {
        if (mListener != null)
            mListener.superPermission(requestCode);//同意了全部权限的回调
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //提示框
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.perm_tip),
                R.string.setting, R.string.cancel, null, perms);
    }
}
