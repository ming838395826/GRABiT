package com.ming.grabit.shop.delegate;

import android.app.Dialog;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.view.IView;
import com.android.base.util.DensityUtil;
import com.android.base.util.ToastUtil;
import com.android.base.util.image.GlideUtil;
import com.ming.grabit.R;
import com.ming.grabit.common.CommonShareActivity;
import com.ming.grabit.common.CommonWebActivity;
import com.ming.grabit.common.selector.view.CircleImageView;
import com.ming.grabit.common.util.Constant;
import com.ming.grabit.event.AddCardEvent;
import com.ming.grabit.event.UpdateCardEvent;
import com.ming.grabit.home.adapter.HomeBannerAdapter;
import com.ming.grabit.home.entity.ProUpdateEvent;
import com.ming.grabit.shop.entity.ShopProductDetailEntity;
import com.ming.grabit.shop.presenter.ShopPresenter;
import com.ming.grabit.util.DialogUtil;
import com.ming.grabit.util.ErrorToastUtli;
import com.ming.grabit.util.FormatUtil;
import com.ming.grabit.util.image.BlurBuilder;
import com.ming.grabit.view.gallery.IndicatorContainer;
import com.ming.grabit.view.gallery.SliderBanner;
import com.taobao.uikit.feature.view.TEditText;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/10/14
 * 1120389276@qq.com
 * 描述：
 */

public class ShopDetailDelegate extends MvpDelegate<IView, ShopPresenter> implements View.OnClickListener {

    private final static int TYPE_POST_USER_COUPONS = 0x03;//领取卡片
    private final static int TYPE_POST_SET_TIP = 0x04;//预约提醒

    @Bind(R.id.timg_avatar)
    TImageView timgAvatar;
    @Bind(R.id.ttv_status)
    TTextView ttvStatus;
    @Bind(R.id.ttv_goods_title)
    TTextView ttvGoodsTitle;
    @Bind(R.id.timg_shop_avatar)
    CircleImageView timgShopAvatar;
    @Bind(R.id.ttv_shop_name)
    TTextView ttvShopName;
    @Bind(R.id.ttv_goods_content)
    TTextView ttvGoodsContent;
    @Bind(R.id.ttv_goods_count)
    TTextView ttvGoodsCount;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.timg_back)
    TImageView timgBack;
    @Bind(R.id.ttv_address)
    TEditText ttvAddress;
    @Bind(R.id.ttv_tel)
    TTextView ttvTel;
    @Bind(R.id.ttv_date_tag)
    TextView ttvDateTag;
    @Bind(R.id.ttv_date_day)
    TTextView ttvDateDay;
    @Bind(R.id.ttv_date_hour)
    TTextView ttvDateHour;
    @Bind(R.id.ttv_date_min)
    TTextView ttvDateMin;
    @Bind(R.id.ttv_date_s)
    TTextView ttvDateS;
    @Bind(R.id.ll_layout_time)
    LinearLayout llLayoutTime;
    @Bind(R.id.tv_submit)
    TTextView ttvSubmit;
    @Bind(R.id.tiv_share)
    TImageView mTivShare;
    @Bind(R.id.ttv_date_day_unit)
    TTextView mTtvDateDayUnit;
    @Bind(R.id.ttv_date_hour_unit)
    TTextView mTtvDateHourUnit;
    @Bind(R.id.ttv_date_min_unit)
    TTextView mTtvDateMinUnit;
    @Bind(R.id.vp_home_banner_pager)
    ViewPager mVpHomeBannerPager;
    @Bind(R.id.dc_home_banner_indicator)
    IndicatorContainer mDcHomeBannerIndicator;
    @Bind(R.id.sb_home_banner)
    SliderBanner mSbHomeBanner;
    @Bind(R.id.ll_shop_info)
    LinearLayout mLlShopInfo;

    private ShopProductDetailEntity mShopProductDetailEntity;
    private CountDownTimer timer;
    private boolean clickEnable = true;
    private HomeBannerAdapter mBannerAdapter = new HomeBannerAdapter();

    @Override
    public void onFail(String code, int type) {
        switch (type) {
            case TYPE_POST_USER_COUPONS:
                dismissLoadDialog();
                if (Constant.FAIL_GET_AGAIN_CODE.equals(code)) {
                    if (getType() == 0) {//
                        ToastUtil.shortShow(getString(R.string.text_getter_receied));
                    } else {
                        ToastUtil.shortShow(getString(R.string.text_set_receied));
                    }
                } else {
                    ErrorToastUtli.showErrorToast(code);
                    //刷新商品
                    if (getType() == 0) {
                        mShopProductDetailEntity.setCurrent_stock(mShopProductDetailEntity.getCurrent_stock() + 1);
                        clickEnable = false;
                        ttvSubmit.setEnabled(false);
                        ttvSubmit.setText(getString(R.string.text_home_got_it));
                    } else {
                        clickEnable = false;
                        ttvSubmit.setEnabled(false);
                        ttvSubmit.setText(getString(R.string.text_home_grabit_reminder_set));
                    }
                }

                if ("1006".equals(code)) {
                    //库存不足
                    DialogUtil.getNoGoodSDialog(getActivity(), true, null).show();
                }
                break;
            case 1:
                dismissLoadDialog();
                if (Constant.FAIL_GET_AGAIN_CODE.equals(code)) {
                    //已抢到
                    clickEnable = false;
                    ttvSubmit.setEnabled(false);
                    ttvSubmit.setText(getString(R.string.text_home_got_it));
                }
                break;
        }

    }

    @Override
    public void onComplete(int type) {

    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case 0:
                ShopProductDetailEntity entity = (ShopProductDetailEntity) data;
                initData(entity);
                break;
            case 1:

                break;
            case TYPE_POST_USER_COUPONS:
                //获取优惠券成功
                dismissLoadDialog();
                try {
                    mShopProductDetailEntity.setCurrent_stock(mShopProductDetailEntity.getCurrent_stock() + 1);
                    clickEnable = false;
                    ttvSubmit.setEnabled(false);
                    ttvSubmit.setText(getString(R.string.text_home_got_it));
                    ttvGoodsCount.setText(mShopProductDetailEntity.getCurrent_stock() + "");
//
                    EventBus.getDefault().post(new UpdateCardEvent(mShopProductDetailEntity.getId()));
//                    mShopProductItemEntity.setUser_coupon(new ShopProductItemEntity.UserCouponBean());
//                    ttvBtnRight.setText(getString(R.string.text_home_got_it));
//                    ttvBtnRight.setBackgroundColor(Color.parseColor("#66191917"));
//                    ttvBtnRight.setEnabled(false);
                } catch (Exception e) {
                }

                DialogUtil.getGetterDialog(getActivity(), true, mShopProductDetailEntity.getBusiness().getName(), mShopProductDetailEntity.getBusiness().getAvatar(), new DialogUtil.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, int position) {

                        EventBus.getDefault().post(new ProUpdateEvent());
                        EventBus.getDefault().post(new AddCardEvent(0));
                    }
                }).show();
                break;
            case TYPE_POST_SET_TIP:
                //设置提醒成功
                dismissLoadDialog();
                try {
                    ttvSubmit.setText(getString(R.string.text_home_grabit_reminder_set));
                    ttvSubmit.setBackgroundColor(Color.parseColor("#66191917"));
                    ttvSubmit.setEnabled(false);
                } catch (Exception e) {
                }
                DialogUtil.getRemindSetSuccessDialog(getActivity(), true, new DialogUtil.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, int position) {
                        EventBus.getDefault().post(new UpdateCardEvent(mShopProductDetailEntity.getId()));
                    }
                }).show();
                break;
        }
    }

    @NonNull
    @Override
    protected ShopPresenter createPresenter() {
        return new ShopPresenter();
    }

    private String getShopItemId() {
        return getActivity().getIntent().getStringExtra("id");
    }

    private String getParentId() {
        return getActivity().getIntent().getStringExtra("parentId");
    }

    private int getType() {
        return getActivity().getIntent().getIntExtra("type", 0);
    }

    private long getStartTime() {
        return getActivity().getIntent().getLongExtra("startTime", 0);
    }

    private long getEndTime() {
        return getActivity().getIntent().getLongExtra("endTime", 0);
    }

    @Override
    public void initWidget() {

//        llBottom.setVisibility(View.GONE);

//        ViewGroup.LayoutParams vpParams = timgAvatar.getLayoutParams();
//        vpParams.height = (int) (DensityUtil.getWidth() * 0.752);
//        timgAvatar.setLayoutParams(vpParams);

        ViewGroup.LayoutParams vpParams = mSbHomeBanner.getLayoutParams();
        vpParams.height = (int) (DensityUtil.getWidth() * 0.752);
        mSbHomeBanner.setLayoutParams(vpParams);

        initData();

    }

    private void initData(ShopProductDetailEntity entity) {
        try {
            this.mShopProductDetailEntity = entity;
            if (entity != null) {
                String image = entity.getImages();
                String[] images = image.split(",");
                List<String> list = new ArrayList<>();
                for (int i = 0; i < images.length; i++) {
                    list.add(images[i]);
                }
                mBannerAdapter.setData(list);
                mSbHomeBanner.setAdapter(mBannerAdapter);
                mDcHomeBannerIndicator.setNum(list.size());
                mSbHomeBanner.beginPlay();
                GlideUtil.defaultLoad(this.getActivity(), entity.getFront_cover(), timgAvatar);
                ttvGoodsTitle.setText(entity.getName());
                ttvGoodsContent.setText(entity.getDescription());
                GlideUtil.defualtLoad(this.getActivity(), entity.getBusiness().getAvatar(), R.mipmap.ic_shop_defaut, timgShopAvatar);
                ttvShopName.setText(entity.getBusiness().getName());
                ttvGoodsCount.setText(entity.getCurrent_stock() + "");
                ttvAddress.setText(entity.getLocation());
                ttvTel.setText(entity.getContact_phone());
            }
        } catch (Exception e) {
        }
    }

    private void initData() {
        switch (getType()) {
            case 0:
                llLayoutTime.setVisibility(View.VISIBLE);
                ttvDateTag.setText(getString(R.string.text_home_countdown));
                ttvSubmit.setText(getString(R.string.text_home_grab_it));
                ttvSubmit.setVisibility(View.GONE);
                break;
            case 1:
                llLayoutTime.setVisibility(View.VISIBLE);
                ttvDateTag.setText(getString(R.string.text_home_time_limit));
                ttvSubmit.setText(getString(R.string.text_home_reminder));
                ttvSubmit.setVisibility(View.GONE);
                break;
            case 2:
                llLayoutTime.setVisibility(View.GONE);
                ttvSubmit.setVisibility(View.GONE);
                break;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", getShopItemId());
        getPrensenter().getShopItemDetail(params, 0);

        Map<String, Object> params1 = new HashMap<>();
        params1.put("id", getParentId());
        getPrensenter().getShopItemReceived(params1, 1);

        starTimer(getStartTime(), getEndTime());
    }

    @OnClick({R.id.timg_back, R.id.ll_shop_info, R.id.tv_submit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timg_back:
                finish();
                break;
            case R.id.ll_shop_info:
                if (mShopProductDetailEntity == null) {
                    break;
                }
                if (mShopProductDetailEntity.getBusiness().getUrl() != null && !TextUtils.isEmpty(mShopProductDetailEntity.getBusiness().getUrl())) {
                    CommonWebActivity.star(this.getActivity(), mShopProductDetailEntity.getBusiness().getUrl());
                }
                break;
            case R.id.tv_submit:
                if (clickEnable) {
                    if (getType() == 0) {//领取
                        Map<String, Object> params = new HashMap<>();
                        params.put("card_id", getParentId());
                        showLoadDialog();
                        getPrensenter().postUserCoupons(params, TYPE_POST_USER_COUPONS);
                    } else {//预约提醒
                        Map<String, Object> params = new HashMap<>();
                        params.put("item_id", getParentId());
                        showLoadDialog();
                        getPrensenter().postShopNotice(params, TYPE_POST_SET_TIP);
                    }
                }
                break;
        }
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_shop_detail;
    }

    private void starTimer(long star, long end) {
        if (getType() != 0) {
            end = star;
        }

        long current = System.currentTimeMillis() / 1000;
        if (end <= current) {
            ttvDateDay.setText("00");
            ttvDateHour.setText("00");
            ttvDateMin.setText("00");
            ttvDateS.setText("00");
//            ttvBtnRight.setEnabled(false);
//            ranOut();
            return;
        } else {
//            ttvBtnRight.setEnabled(true);
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer((end - current) * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                millisUntilFinished = millisUntilFinished / 1000;

                long nd = 24 * 60 * 60;
                long nh = 60 * 60;
                long nm = 60;

                long day = millisUntilFinished / nd;
                // 计算差多少小时
                long hour = millisUntilFinished % nd / nh;
                // 计算差多少分钟
                long min = millisUntilFinished % nd % nh / nm;
                // 计算差多少分钟
                long s = millisUntilFinished % nd % nh % nm;

                if (ttvDateHour != null) {
                    if (day > 0) {
                        ttvDateDay.setText(FormatUtil.formatTime(day));
                        mTtvDateHourUnit.setVisibility(View.GONE);
                        mTtvDateMinUnit.setVisibility(View.GONE);
                        ttvDateHour.setVisibility(View.GONE);
                        ttvDateMin.setVisibility(View.GONE);
                        ttvDateS.setVisibility(View.GONE);
                        if(day>1){
                            mTtvDateDayUnit.setText(mTtvDateDayUnit.getContext().getString(R.string.text_home_date_day));
                        }else{
                            mTtvDateDayUnit.setText(mTtvDateDayUnit.getContext().getString(R.string.text_home_date_day_single));
                        }
                    } else {
                        ttvDateDay.setVisibility(View.GONE);
                        mTtvDateDayUnit.setVisibility(View.GONE);
                    }
                    ttvDateDay.setText(FormatUtil.formatTime(day));
                    ttvDateHour.setText(FormatUtil.formatTime(hour));
                    ttvDateMin.setText(FormatUtil.formatTime(min));
                    ttvDateS.setText(FormatUtil.formatTime(s));
                }
            }

            @Override
            public void onFinish() {
                if (ttvDateDay == null) {
                    return;
                }
                ttvDateDay.setVisibility(View.GONE);
//                ttvDateDayUnit.setVisibility(View.GONE);
                ttvDateHour.setText("00");
                ttvDateMin.setText("00");
                ttvDateS.setText("00");
//                ranOut();
//                if (ttvDate != null) {
//                    ttvDate.setText(getString(R.string.text_home_end));
////                }

            }
        };

        timer.start();
    }

    @OnClick(R.id.tiv_share)
    public void share() {
        BlurBuilder.snapShotWithoutStatusBar(getActivity());
        CommonShareActivity.star(getActivity(), mShopProductDetailEntity);
    }
}
