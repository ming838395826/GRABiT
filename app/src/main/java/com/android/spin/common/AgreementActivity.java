package com.android.spin.common;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.base.base.BaseUIActivity;
import com.android.spin.R;
import com.android.spin.common.delegate.AgreementDelegate;
import com.android.spin.common.util.Constant;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：协议
 */
public class AgreementActivity extends BaseUIActivity<AgreementDelegate> {

    public static final String BASE_URL_PARAMS = "url";

    private String getUrl(){
        return getIntent().getStringExtra(AgreementActivity.BASE_URL_PARAMS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void star(Context context,String url){
        Intent intent = new Intent(context,AgreementActivity.class);
        intent.putExtra(BASE_URL_PARAMS,url);
        context.startActivity(intent);
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
        if(Constant.URL_ABOUT_GRABIT.equals(getUrl())){
            setHeaderTitle(getString(R.string.text_mine_about));
        }else{
            setHeaderTitle(getString(R.string.text_mine_service_agreement));
        }
    }

    @Override
    protected Class<AgreementDelegate> getDelegateClass() {
        return AgreementDelegate.class;
    }
}
