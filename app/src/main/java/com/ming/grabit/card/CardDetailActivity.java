package com.ming.grabit.card;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.base.base.BaseUIActivity;
import com.ming.grabit.R;
import com.ming.grabit.shop.entity.ShopHistroyItemEntity;

@Route(path = "/app/CarDetail")
public class CardDetailActivity extends BaseUIActivity<CarDetailDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_from_bottom,0);
        super.onCreate(savedInstanceState);

    }

    public static void star(Context mContext, int id){
        Intent intent = new Intent(mContext,CardDetailActivity.class);
        intent.putExtra("id",id);
        mContext.startActivity(intent);
        ((Activity)mContext).overridePendingTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom);
    }

    public static void star(Context mContext, ShopHistroyItemEntity entity){
        Intent intent = new Intent(mContext,CardDetailActivity.class);
        intent.putExtra("entity",entity);
        mContext.startActivity(intent);
        ((Activity)mContext).overridePendingTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom);
    }

    @Override
    public int getLayoutStyle() {
        return TITLEBARSTYLE_NOT;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initHeaderView() {
        setHeaderTitleAndColor(getString(R.string.text_change_pwd_title),getResources().getColor(R.color.app_font_color_main_title));
        setHeaderTitleViewBackgroundColor(R.color.color_white);
        setHeaderTitleLeftDrawable(R.mipmap.icon_gray_back);
        setHeaderTitleRightDrawable(R.mipmap.ic_delete_gray);
        setHeaderRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate delegate = getDelegate();
            }
        });


    }

    @Override
    protected Class<CarDetailDelegate> getDelegateClass() {
        return CarDetailDelegate.class;
    }

    @Override
    public void finish() {
//        overridePendingTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom);
        super.finish();
        overridePendingTransition(0,R.anim.slide_out_to_bottom);
    }
}
