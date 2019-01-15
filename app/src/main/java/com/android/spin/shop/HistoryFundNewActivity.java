package com.android.spin.shop;

import android.os.Bundle;
import android.view.KeyEvent;

import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.shop.delegate.HistoryFundDelegate;
import com.android.spin.shop.delegate.HistoryFundNewDelegate;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：历史好物
 */
public class HistoryFundNewActivity extends BaseUIActivity<HistoryFundNewDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutStyle() {
        return TITLEBARSTYLE_BELW;
    }

    @Override
    public void initView() {

        setSwipeBackEnable(false);
    }

    @Override
    public void initHeaderView() {
        setHeaderTitleAndColor(getString(R.string.text_history_title),getResources().getColor(R.color.app_font_color_main_title));
        setHeaderTitleViewBackgroundColor(R.color.color_white);
        setHeaderTitleLeftDrawable(R.mipmap.icon_gray_back);
    }

    @Override
    protected Class<HistoryFundNewDelegate> getDelegateClass() {
        return HistoryFundNewDelegate.class;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
