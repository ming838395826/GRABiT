package com.ming.grabit.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.base.base.BaseUIActivity;
import com.ming.grabit.R;
import com.ming.grabit.mine.delegate.ContactUsDelegate;

public class ContactUsActivity extends BaseUIActivity<ContactUsDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static void star(Context context){
        Intent intent = new Intent(context,ContactUsActivity.class);
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
        setHeaderTitleAndColor(getString(R.string.text_mine_contact_us),getResources().getColor(R.color.app_font_color_main_title));
        setHeaderTitleViewBackgroundColor(R.color.color_white);
        setHeaderTitleLeftDrawable(R.mipmap.icon_gray_back);
    }

    @Override
    protected Class<ContactUsDelegate> getDelegateClass() {
        return ContactUsDelegate.class;
    }
}
