package com.android.spin.shop.delegate;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.android.base.base.BaseUIActivity;
import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.view.IView;
import com.android.base.util.AppLanguageManager;
import com.android.base.util.DateUtil;
import com.android.spin.R;
import com.android.spin.shop.entity.ShopHistroyItemEntity;
import com.android.spin.shop.fragment.CardViewPagerFragment;
import com.android.spin.shop.fragment.HistoryShopListFragment;
import com.android.spin.shop.presenter.ShopPresenter;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/9/24
 * 1120389276@qq.com
 * 描述：
 */

public class HistoryFundNewDelegate extends MvpDelegate<IView, ShopPresenter> implements View.OnClickListener{
    @Bind(R.id.ttv_week)
    TTextView ttvWeek;
    @Bind(R.id.ttv_month)
    TTextView ttvMonth;
    @Bind(R.id.ttv_day)
    TTextView ttvDay;
    @Bind(R.id.timg_back)
    TImageView timgBack;

    private String[] months = {"Jan","Feb","Mar","Apr","May",
            "Jun","Jul","Aug","Sep","Oct","Nov","Dec"};


    @Override
    public void onFail(String code, int type) {

    }

    @Override
    public void onComplete(int type) {

    }

    @Override
    public void onSuccess(Object data, int type) {

    }

    @NonNull
    @Override
    protected ShopPresenter createPresenter() {
        return new ShopPresenter();
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_history_fund;
    }

    @Override
    public void initWidget() {
        HistoryShopListFragment historyShopListFragment=new HistoryShopListFragment();
        CardViewPagerFragment fragment = CardViewPagerFragment.getInstance();
        fragment.setonPageSelectedListener(new CardViewPagerFragment.onPageSelectedListener() {
            @Override
            public void onPageSelected(ShopHistroyItemEntity entity) {
                try{
                    if(entity != null){
                        String time = DateUtil.format("MM-dd",entity.getStart_time() * 1000);
                        String dates[] = time.split("-");
                        if(AppLanguageManager.isLanguageEN()){
                            ttvMonth.setText(months[Integer.parseInt(dates[0])]);
                            ttvWeek.setText(DateUtil.getEnWeekDay(entity.getStart_time() * 1000));
                        }else{
                            ttvMonth.setText(dates[0] + "月");
                            ttvWeek.setText(DateUtil.getWeekDay(entity.getStart_time() * 1000));
                        }
                        ttvDay.setText(dates[1]);
                    }
                }catch (Exception e){}

            }
        });

        FragmentTransaction transaction = ((BaseUIActivity) getActivity()).getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.frameLayout, historyShopListFragment);
        transaction.commit();
    }
    @OnClick(R.id.timg_back)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.timg_back:
                finish();
                break;
        }
    }
}
