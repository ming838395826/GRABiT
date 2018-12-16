package com.android.spin.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.mine.delegate.UserInfoDelegate;
import com.android.spin.util.AliRouterMannager;
@Route(path = "/app/info")
public class UserInfoActivity extends BaseUIActivity<UserInfoDelegate> {

    public static void star(Context mContext){

        mContext.startActivity(new Intent(mContext,UserInfoActivity.class));
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

        setHeaderTitleAndColor(getString(R.string.text_user_info_title),
                getResources().getColor(R.color.app_font_color_main_title));
        setHeaderTitleViewBackgroundColor(R.color.color_white);
        setHeaderTitleRightColor(R.color.app_font_color_main_title);
        setHeaderTitleRightContent(getString(R.string.text_user_info_title_edit));
        setHeaderTitleLeftDrawable(R.mipmap.icon_gray_back);

        setHeaderRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDelegate.onClickEditorSave();
            }
        });

    }

    @Override
    protected Class<UserInfoDelegate> getDelegateClass() {
        return UserInfoDelegate.class;
    }
}
