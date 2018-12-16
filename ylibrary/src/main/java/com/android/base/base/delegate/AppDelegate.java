package com.android.base.base.delegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.base.R;
import com.android.base.base.BaseActivity;
import com.android.base.base.BaseAdaptateActivity;
import com.android.base.base.BaseHeaderTitleBarView;
import com.android.base.base.BaseUIActivity;
import com.android.base.permission.iface.CheckPermListener;
import com.android.base.view.load.RotateLoading;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：YANGQIYUN
 * 时间 2017 06 21
 * 邮箱：1120389276@qq.com
 * 描述：
 */

public abstract class AppDelegate implements IDelegate {

    protected final SparseArray<View> mViews = new SparseArray<View>();

    protected View rootView;
    RotateLoading pbLoading;
    TTextView ttvEmpty;
    TTextView ttvNetworkError;

    private FrameLayout emptyLayout;
    private FrameLayout container;

    public abstract int getRootLayoutId();

    protected InputMethodManager inputMethodManager;

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.delegate_base_ui, container, false);
        container = (FrameLayout) rootView.findViewById(R.id.delegate_container);
        emptyLayout = (FrameLayout) rootView.findViewById(R.id.fl_empty_layout);
        pbLoading = (RotateLoading) rootView.findViewById(R.id.pb_loading);
        ttvEmpty = (TTextView) rootView.findViewById(R.id.ttv_empty);
        ttvNetworkError = (TTextView) rootView.findViewById(R.id.ttv_network_error);
        int rootLayoutId = getRootLayoutId();
//        rootView = inflater.inflate(rootLayoutId, container, false);
        container.addView(inflater.inflate(rootLayoutId, null),
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this, rootView);

        initWidget();

        create(savedInstanceState);
    }

    public void create(Bundle savedInstanceState) {
    }

    private void hideView(View view){
        if(view != null && view.getVisibility() == View.VISIBLE){
            view.setVisibility(View.GONE);
        }
    }

    private void showView(View view){

        if(view != null && view.getVisibility() == View.GONE){
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示网络错误
     */
    public void showNetWorkError(){
        showEmptyLayout();
        hideView(ttvEmpty);
        hideView(pbLoading);
        showView(ttvNetworkError);
    }

    /**
     * 显示数据为空
     */
    public void showEmptyView(){
        showEmptyLayout();
        hideView(pbLoading);
        hideView(ttvNetworkError);
        showView(ttvEmpty);
    }

    /**
     * 显示加载中
     */
    public void showLoading(){
        showEmptyLayout();
        hideView(ttvEmpty);
        hideView(ttvNetworkError);
        showView(pbLoading);
        if(pbLoading != null){
            pbLoading.start();
        }
    }

    public void showEmptyLayout(){
        hideView(container);
        showView(emptyLayout);
    }

    public void hideEmptyLayout(){
        hideView(emptyLayout);
        showView(container);
        if(pbLoading != null){
            pbLoading.stop();
        }
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public BaseHeaderTitleBarView getBaseHeaderTitleBarView() {
        BaseHeaderTitleBarView mHeaderTitleBar = null;
        if (getActivity() instanceof BaseUIActivity) {
            mHeaderTitleBar = ((BaseUIActivity) getActivity()).getBaseHeaderTitleBarVie();
        }
        return mHeaderTitleBar;
    }

    /**
     *
     */
    public abstract void initWidget();

    public <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
    }

    public <T extends Activity> T getActivity() {
        return (T) rootView.getContext();
    }

    public Fragment addFragment(String tag, int resId, Fragment fragment) {
        FragmentManager manager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
//            transaction.remove(fragment).commitNowAllowingStateLoss();
            transaction.setCustomAnimations(
                    R.anim.fade_in, R.anim.fade_out);
            transaction.add(resId, fragment, tag);
        }
        transaction.commitAllowingStateLoss();
        return fragment;
    }

    protected String getString(int resId) {
        return getActivity().getString(resId);
    }

    protected void sendBroadcast(Intent intent) {
        getActivity().sendBroadcast(intent);
    }

    protected void overridePendingTransition(int animIn, int animOut) {
        overridePendingTransition(animIn, animOut);
    }

    protected void setResult(int result, Intent intent) {
        getActivity().setResult(result, intent);
    }

    protected void setResult(int result) {
        getActivity().setResult(result);
    }

    protected Intent getIntent() {
        return getActivity().getIntent();
    }


    protected void setEnable(View view, boolean bool) {
        if (view != null) {
            view.setEnabled(bool);
        }
    }

    protected String getText(TextView view) {
        if (view == null) {
            return "";
        }
        return view.getText().toString().trim();
    }

    protected int getTextLength(TextView view) {
        if (view == null) {
            return 0;
        }
        return view.getText().toString().trim().length();
    }

    /**
     * 销毁activity
     */
    public void finish() {
        Activity act = getActivity();
        if (act != null) {
            act.finish();
        }
    }

    protected void showLoadDialog() {
        Activity act = getActivity();
        if (act != null && act instanceof BaseActivity) {
            BaseActivity base = (BaseActivity) act;
            base.showLoadDialog();
        }
    }

    protected void dismissLoadDialog() {
        Activity act = getActivity();
        if (act != null && act instanceof BaseActivity) {
            BaseActivity base = (BaseActivity) act;
            base.dismissLoadDialog();
        }
    }

    /**
     * 权限申请
     *
     * @param listener
     * @param requestCode
     * @param resString
     * @param mPerms
     */
    protected void checkPermission(CheckPermListener listener, int requestCode, int resString, String... mPerms) {
        try {
            BaseAdaptateActivity act = getActivity();
            act.checkPermission(listener, requestCode, resString, mPerms);
        } catch (Exception e) {
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    //收起键盘
    protected void hideSoftKeyboard(View view) {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //弹出键盘
    protected void showSoftKeyboard(View view) {
        if (getActivity().getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                ((InputMethodManager) (getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))).showSoftInput(view,
                        InputMethodManager.SHOW_FORCED);
        }
    }

    protected void setVisibility(View view,int visibility){
        if(view == null){
            return;
        }
        view.setVisibility(visibility);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
    }
}
