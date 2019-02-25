package com.ming.grabit.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.base.base.BaseUIActivity;
import com.ming.grabit.R;
import com.ming.grabit.shop.delegate.ShopDetailDelegate;
import com.ming.grabit.shop.entity.ShopHistroyItemEntity;

@Route(path = "/app/ShopDetail")
public class ShopDetailActivity extends BaseUIActivity<ShopDetailDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_from_bottom,0);
        super.onCreate(savedInstanceState);

    }

    public static void star(Context mContext, int id){
        Intent intent = new Intent(mContext,ShopDetailActivity.class);
        intent.putExtra("id",id);
        mContext.startActivity(intent);
        ((Activity)mContext).overridePendingTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom);
    }

    public static void star(Context mContext, ShopHistroyItemEntity entity){
        Intent intent = new Intent(mContext,ShopDetailActivity.class);
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

    }

    @Override
    protected Class<ShopDetailDelegate> getDelegateClass() {
        return ShopDetailDelegate.class;
    }

    @Override
    public void finish() {
//        overridePendingTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom);
        super.finish();
        overridePendingTransition(0,R.anim.slide_out_to_bottom);
    }
}
