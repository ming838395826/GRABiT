package com.ming.grabit.shop;

import android.os.Bundle;
import android.view.KeyEvent;

import com.android.base.base.BaseUIActivity;
import com.ming.grabit.R;
import com.ming.grabit.shop.delegate.HistoryFundDelegate;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：历史好物
 */
public class HistoryFundActivity extends BaseUIActivity<HistoryFundDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutStyle() {
        return TITLEBARSTYLE_NOT;
    }

    @Override
    public void initView() {

        setSwipeBackEnable(false);
    }

    @Override
    public void initHeaderView() {

    }

    @Override
    protected Class<HistoryFundDelegate> getDelegateClass() {
        return HistoryFundDelegate.class;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
