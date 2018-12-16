package com.android.base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.R;
import com.android.base.mvp.persenrter.MvpPresenter;
import com.android.base.mvp.view.IView;
import com.android.base.view.listview.CommonListViewWrapper;
import com.android.base.view.load.RotateLoading;
import com.taobao.uikit.feature.view.TTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者：yangqiyun
 * 时间：2017/6/11
 * 1120389276@qq.com
 * 描述：与baseactivity同属性
 */

public abstract class BaseFragment extends Fragment{
    protected View rootView;
    RotateLoading pbLoading;
    TTextView ttvEmpty;
    TTextView ttvNetworkError;

    private FrameLayout emptyLayout;
    private FrameLayout container;

    private boolean isLoading = false;

    public void setBackgroudColor(int color){
        if(container != null){
            container.setBackgroundColor(color);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.delegate_base_ui, container, false);
        container = (FrameLayout) rootView.findViewById(R.id.delegate_container);
        emptyLayout = (FrameLayout) rootView.findViewById(R.id.fl_empty_layout);
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ttvNetworkError == null || ttvNetworkError.getVisibility() != View.VISIBLE || isLoading){
                    return;
                }
                isLoading = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoading = false;
                    }
                },2000);
                onRefreshAgain();
            }
        });
        pbLoading = (RotateLoading) rootView.findViewById(R.id.pb_loading);
        ttvEmpty = (TTextView) rootView.findViewById(R.id.ttv_empty);
        ttvNetworkError = (TTextView) rootView.findViewById(R.id.ttv_network_error);
        int rootLayoutId = getLayoutId();
//        rootView = inflater.inflate(rootLayoutId, container, false);
        container.addView(inflater.inflate(rootLayoutId, null),
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
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
     * 显示数据为空
     */
    public void showEmptyView(String title,int resid){
        showEmptyView();

        ttvEmpty.setText(title);
        ttvEmpty.setCompoundDrawablesWithIntrinsicBounds(
                null,getActivity().getResources().getDrawable(resid),null,null);
    }

    /**
     * 显示加载中
     */
    public void showLoadingView(){
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

    public abstract int getLayoutId();

    public abstract void initView();

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    protected String getText(TextView view){
        if(view != null){
            return view.getText().toString().trim();
        }
        return "";
    }

    protected int getTextLength(TextView view){
        if(view != null){
            return view.getText().toString().trim().length();
        }
        return 0;
    }

    protected void setEnable(View view,boolean bool){
        if(view != null){
            view.setEnabled(bool);
        }
    }

    protected void showLoadDialog(){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).showLoadDialog();
        }
    }

    protected void dismissLoadDialog(){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).dismissLoadDialog();
        }
    }

    protected void finishActivity(){
        Activity act = getActivity();
        if(act != null){
            act.finish();
        }
    }


    protected List<Object> getTestDate(){
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            list.add(i);
        }
        return list;
    }

    //收起键盘
    protected void hideSoftKeyboard(View view) {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //弹出键盘
    protected void showSoftKeyboard(View view) {
        if (getActivity().getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                ((InputMethodManager)(getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))).showSoftInput(view,
                        InputMethodManager.SHOW_FORCED);
        }
    }

    protected void registerEvent(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    protected void unregisterEvent(){
        EventBus.getDefault().unregister(this);
    }

    protected void setText(TextView view,String str){
        if(view == null){
            return;
        }
        view.setText(str);
    }

    protected void setVisibility(View view,int visibility){
        if(view == null){
            return;
        }
        view.setVisibility(visibility);
    }

    protected void setCommonListViewWrapperRefresh(CommonListViewWrapper ListViewWrapper,boolean refresh){
        if(ListViewWrapper != null){
            ListViewWrapper.setIsRefresh(refresh);
        }
    }

    public void onRefreshAgain(){

    }
}
