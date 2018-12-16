package com.android.spin.shop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.shop.delegate.HistoryFundDetailDelegate;
import com.android.spin.shop.entity.ShopHistroyItemEntity;
@Route(path = "/app/HistoryFundDetail")
public class HistoryFundDetailActivity extends BaseUIActivity<HistoryFundDetailDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static void star(Context mContext, ShopHistroyItemEntity entity){
        Intent intent = new Intent(mContext,HistoryFundDetailActivity.class);
        intent.putExtra("entity",entity);
        mContext.startActivity(intent);
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
    protected Class<HistoryFundDetailDelegate> getDelegateClass() {
        return HistoryFundDetailDelegate.class;
    }
}
