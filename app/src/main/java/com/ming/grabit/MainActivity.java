package com.ming.grabit;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.base.base.BaseUIActivity;
import com.android.base.event.TokenFailEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = "/app/main")
public class MainActivity extends BaseUIActivity<MainDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

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
    protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenFail(TokenFailEvent message) {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
