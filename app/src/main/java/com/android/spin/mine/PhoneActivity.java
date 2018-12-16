package com.android.spin.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.mine.delegate.PhoneDelegate;

public class PhoneActivity extends BaseUIActivity<PhoneDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static void star(Context context,String phone){

        Intent intent = new Intent(context,PhoneActivity.class);
        intent.putExtra("phone",phone);
        context.startActivity(intent);
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
        setHeaderTitleAndColor(getString(R.string.text_mine_mobile),getResources().getColor(R.color.app_font_color_main_title));
        setHeaderTitleViewBackgroundColor(R.color.color_white);
        setHeaderTitleLeftDrawable(R.mipmap.icon_gray_back);
    }

    @Override
    protected Class<PhoneDelegate> getDelegateClass() {
        return PhoneDelegate.class;
    }
}
