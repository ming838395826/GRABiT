package com.android.spin.common.delegate;

import android.graphics.Bitmap;
import android.webkit.WebView;

import com.android.base.base.delegate.AppDelegate;
import com.android.base.view.layout.BaseWebView;
import com.android.spin.R;
import com.android.spin.common.AgreementActivity;
import com.android.spin.common.util.Constant;

import butterknife.Bind;

/**
 * 作者：yangqiyun
 * 时间：2017/7/26
 * 1120389276@qq.com
 * 描述：用户协议
 */

public class AgreementDelegate extends AppDelegate {

    @Bind(R.id.bwv_agreement)
    BaseWebView bwvAgreement;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_agreenment;
    }

    private String getUrl(){
        return getActivity().getIntent().getStringExtra(AgreementActivity.BASE_URL_PARAMS);
    }

    @Override
    public void initWidget() {

        initWebView(bwvAgreement,getUrl());
    }

    private void initWebView(BaseWebView webView, String originalUrl) {
        if(originalUrl == null){
            return;
        }
        webView.setWebViewClientListener(new BaseWebView.WebViewClientListener() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                showLoadingDialog(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                hideLoadingDialog();
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // webview 加载完成，设置 false 标志位
                    view.setTag(false);
                } else {
                    // webview 正在加载，设置 true 标志位
                    view.setTag(true);
                }
            }
        });

        if (originalUrl.startsWith("http") || originalUrl.startsWith("https")) {
            // url是http或https链接
            webView.loadUrl(originalUrl);
        }
    }
}
