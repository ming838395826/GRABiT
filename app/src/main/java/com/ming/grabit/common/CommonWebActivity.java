package com.ming.grabit.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.base.base.BaseUIActivity;
import com.ming.grabit.R;
import com.ming.grabit.common.delegate.CommonWebDelegate;

public class CommonWebActivity extends BaseUIActivity<CommonWebDelegate> {

    public static final String BASE_URL_PARAMS = "url";

    private String getUrl(){
        return getIntent().getStringExtra(AgreementActivity.BASE_URL_PARAMS);
    }

    public static void star(Context context, String url){
        Intent intent = new Intent(context,CommonWebActivity.class);
        intent.putExtra(BASE_URL_PARAMS,url);
        context.startActivity(intent);
    }

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

    }

    @Override
    public void initHeaderView() {

    }

    @Override
    protected Class<CommonWebDelegate> getDelegateClass() {
        return CommonWebDelegate.class;
    }
}
