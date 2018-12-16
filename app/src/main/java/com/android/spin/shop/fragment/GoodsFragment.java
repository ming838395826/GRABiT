package com.android.spin.shop.fragment;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.base.MvpFragment;
import com.android.base.mvp.view.IView;
import com.android.base.util.ToastUtil;
import com.android.base.util.image.GlideUtil;
import com.android.base.view.layout.PtrMaterialFrameLayout;
import com.android.base.view.listview.CommonListViewWrapper;
import com.android.spin.R;
import com.android.spin.common.CommonWebActivity;
import com.android.spin.common.selector.view.CircleImageView;
import com.android.spin.common.util.Constant;
import com.android.spin.event.AddCardEvent;
import com.android.spin.event.UpdateCardEvent;
import com.android.spin.home.HomeActivity;
import com.android.spin.home.adapter.HomeBannerAdapter;
import com.android.spin.home.entity.CommingProUpdateEvent;
import com.android.spin.home.entity.ProUpdateEvent;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.shop.presenter.ShopPresenter;
import com.android.spin.util.DialogUtil;
import com.android.spin.util.ErrorToastUtli;
import com.android.spin.view.gallery.IndicatorContainer;
import com.android.spin.view.gallery.SliderBanner;
import com.taobao.uikit.feature.view.TTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：发现好物
 */
public class GoodsFragment extends MvpFragment<IView, ShopPresenter> implements View.OnClickListener,CommonListViewWrapper.OnPtrHandlerRefreshListener {

    @Bind(R.id.ttv_product_name)
    TTextView ttvProductName;
    @Bind(R.id.img_shop_avatar)
    CircleImageView imgShopAvatar;
    @Bind(R.id.ttv_shop_name)
    TTextView ttvShopName;
    @Bind(R.id.ttv_product_content)
    TTextView ttvProductContent;
    @Bind(R.id.ttv_product_count)
    TextView ttvProductCount;
    @Bind(R.id.ttv_btn_right)
    TextView ttvBtnRight;
    @Bind(R.id.ttv_date_tag)
    TTextView ttvDateTag;

    private boolean isFirst = true;

    private final static String PAGE_STATUS = "status";

    private final static int TYPE_REQUEST_COMMING = 0x00;
    private final static int TYPE_REQUEST_CURRENT = 0x01;
    private final static int TYPE_POST_USER_COUPONS = 0x02;
    private final static int TYPE_POST_SET_TIP = 0x03;
    private final static int TYPE_GET_GOODS_DETAIL = 0x04;


    @Bind(R.id.vp_home_banner_pager)
    ViewPager vpHomeBannerPager;
    @Bind(R.id.dc_home_banner_indicator)
    IndicatorContainer mIndicatorContainer;
    @Bind(R.id.sb_home_banner)
    SliderBanner mSliderBanner;
    @Bind(R.id.ttv_date_hour)
    TTextView ttvDateHour;
    @Bind(R.id.ttv_date_min)
    TTextView ttvDateMin;
    @Bind(R.id.ttv_date_s)
    TTextView ttvDateS;
    @Bind(R.id.ll_ran)
    LinearLayout llRan;
    @Bind(R.id.ttv_goods_count)
    TTextView ttvGoodsCount;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.ttv_address)
    TTextView ttvAddress;
    @Bind(R.id.ttv_tel)
    TTextView ttvTel;
    @Bind(R.id.ll_layout_time)
    LinearLayout llLayoutTime;
    @Bind(R.id.ttv_product_tag_left)
    TextView ttvProductTagLeft;
    @Bind(R.id.ttv_product_tag)
    TextView ttvProductTag;
    @Bind(R.id.ttv_date_day)
    TTextView ttvDateDay;
    @Bind(R.id.ttv_date_day_unit)
    TTextView ttvDateDayUnit;
    @Bind(R.id.ptr_material_style_ptr_frame)
    PtrMaterialFrameLayout ptrMaterialStylePtrFrame;

    private HomeBannerAdapter mBannerAdapter = new HomeBannerAdapter();

    private ShopProductItemEntity mShopProductItemEntity = new ShopProductItemEntity();

    private CountDownTimer timer;

    private boolean isRefresh = false;

    private void starTimer(long star, long end) {
        if (getType() != 0) {
            end = star;
        }

        long current = System.currentTimeMillis() / 1000;
        if (end <= current) {
//            ttvDate.setText(getString(R.string.text_home_end));
            ttvDateDay.setVisibility(View.GONE);
            ttvDateDayUnit.setVisibility(View.GONE);
            ttvDateHour.setText("00");
            ttvDateMin.setText("00");
            ttvDateS.setText("00");
            ttvBtnRight.setEnabled(false);
            ranOut();
            return;
        }else {
            ttvBtnRight.setEnabled(true);
        }
        if(timer != null){
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

//                long day = millisUntilFinished / (3600 * 24);
//                long hour = millisUntilFinished % (3600 * 24);
//                long min = millisUntilFinished % 3600 / 60;
//                long s = millisUntilFinished % 3600 % 60;
                if (ttvDateHour != null) {
                    if (day > 0) {
                        ttvDateDay.setVisibility(View.VISIBLE);
                        ttvDateDayUnit.setVisibility(View.VISIBLE);
                        ttvDateDay.setText(day + "");
                    } else {
                        ttvDateDay.setVisibility(View.GONE);
                        ttvDateDayUnit.setVisibility(View.GONE);
                    }
                    ttvDateHour.setText(hour + "");
                    ttvDateMin.setText(min + "");
                    ttvDateS.setText(s + "");
                }
            }

            @Override
            public void onFinish() {
                if(ttvDateDay == null){
                    return;
                }
                ttvDateDay.setVisibility(View.GONE);
                ttvDateDayUnit.setVisibility(View.GONE);
                ttvDateHour.setText("00");
                ttvDateMin.setText("00");
                ttvDateS.setText("00");
                ranOut();
//                if (ttvDate != null) {
//                    ttvDate.setText(getString(R.string.text_home_end));
////                }

            }
        };

        timer.start();
    }

    private void hideDownTime() {
        if (llLayoutTime != null) {
            llLayoutTime.setVisibility(View.GONE);
        }
    }

    @Override
    public ShopPresenter initPresenter() {
        return new ShopPresenter();
    }

    public GoodsFragment() {
        // Required empty public constructor
    }

    public static GoodsFragment newInstance(ShopProductItemEntity entity, int type,int size) {
        GoodsFragment mGoodsFragment = new GoodsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(PAGE_STATUS, entity);
        mBundle.putInt("type", type);
        mBundle.putInt("size", size);
        mGoodsFragment.setArguments(mBundle);
        return mGoodsFragment;
    }

    private int getListSize(){
        return getArguments().getInt("size");
    }

    @Override
    public void onFail(String code, int type) {
        if(TYPE_POST_USER_COUPONS == type && ttvBtnRight != null){
            ttvBtnRight.setEnabled(true);
        }
        if (Constant.FAIL_GET_AGAIN_CODE.equals(code)) {
            if (getType() == 0) {
                ToastUtil.shortShow(getString(R.string.text_getter_receied));
            } else {
                ToastUtil.shortShow(getString(R.string.text_set_receied));
            }

        } else {
            ErrorToastUtli.showErrorToast(code);
            //刷新商品
            Map<String,Object> params = new HashMap<>();
            params.put("id",mShopProductItemEntity.getId());
            getPresenter().getShopItemDetailNew(params,TYPE_GET_GOODS_DETAIL);
            isRefresh = true;
        }

        if("1006".equals(code)){
            //库存不足
            DialogUtil.getNoGoodSDialog(getActivity(), true, null).show();
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        updateView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShopProductItemEntity = getPageStatus();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
        if(ptrMaterialStylePtrFrame != null){
            ptrMaterialStylePtrFrame.refreshComplete();
        }
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case TYPE_REQUEST_COMMING:
                //即将开抢
                break;
            case TYPE_REQUEST_CURRENT:
                //普通

                break;
            case TYPE_POST_USER_COUPONS:
                //获取优惠券成功
                try {
                    isRefresh = false;
                    mShopProductItemEntity.setCurrent_stock(mShopProductItemEntity.getCurrent_stock() + 1);
                    String count = mShopProductItemEntity.getStock() - mShopProductItemEntity.getCurrent_stock() + "";
                    if (getType() == 1) {
                        ttvProductTagLeft.setText(getResources().getString(R.string.text_unit_comming_left));
                        ttvProductTag.setText(getResources().getString(R.string.text_unit_comming_right));
                    } else {
                        ttvProductTagLeft.setText(getResources().getString(R.string.text_unit_left));
                        ttvProductTag.setText(getResources().getString(R.string.text_unit_right));
                    }
                    ttvProductCount.setText(count);

                    EventBus.getDefault().post(new UpdateCardEvent(mShopProductItemEntity.getId()));
                    mShopProductItemEntity.setUser_coupon(new ShopProductItemEntity.UserCouponBean());
                    ttvBtnRight.setText(getString(R.string.text_home_got_it));
                    ttvBtnRight.setBackgroundColor(Color.parseColor("#66191917"));
                    ttvBtnRight.setEnabled(false);
                } catch (Exception e) {
                }

                DialogUtil.getGetterDialog(getActivity(), true, new DialogUtil.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, int position) {
                        ((HomeActivity) getActivity()).getViewDelegate().showCardfragment();
                        EventBus.getDefault().post(new AddCardEvent(0));
                    }
                }).show();
                break;
            case TYPE_POST_SET_TIP:
                //设置提醒成功
                try {
                    mShopProductItemEntity.setUser_item_notice(new ShopProductItemEntity.userItemNoticeBean());
                    ttvBtnRight.setText(getString(R.string.text_home_grabit_reminder_set));
                    ttvBtnRight.setBackgroundColor(Color.parseColor("#66191917"));
                    ttvBtnRight.setEnabled(false);
                } catch (Exception e) {
                }
                DialogUtil.getRemindSetSuccessDialog(getActivity(), true, new DialogUtil.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, int position) {
                        EventBus.getDefault().post(new UpdateCardEvent(mShopProductItemEntity.getId()));
                    }
                }).show();
                break;
            case TYPE_GET_GOODS_DETAIL:
                //刷新商品
                try{
                    isRefresh = false;
                    if(ptrMaterialStylePtrFrame != null){
                        ptrMaterialStylePtrFrame.refreshComplete();
                        updateEntity((ShopProductItemEntity) data);
                    }
                }catch (Exception e){}
                break;
        }
    }

    /**
     * 数据更新
     * @param entity
     */
    private void updateEntity(ShopProductItemEntity entity){

        if(entity == null){
            return;
        }
        mShopProductItemEntity.setUser_item_notice(entity.getUser_item_notice());
        mShopProductItemEntity.setCurrent_stock(entity.getCurrent_stock());
        mShopProductItemEntity.setUser_coupon(entity.getUser_coupon());
        mShopProductItemEntity.setBusiness(entity.getBusiness());
        mShopProductItemEntity.setBusiness_id(entity.getBusiness_id());
        mShopProductItemEntity.setContact_phone(entity.getContact_phone());
        mShopProductItemEntity.setDescription(entity.getDescription());
        mShopProductItemEntity.setEnd_time(entity.getEnd_time());
        mShopProductItemEntity.setExchange_deadline(entity.getExchange_deadline());
        mShopProductItemEntity.setFront_cover(entity.getFront_cover());
        mShopProductItemEntity.setImages(entity.getImages());
        mShopProductItemEntity.setLocation(entity.getLocation());
        mShopProductItemEntity.setName(entity.getName());
        mShopProductItemEntity.setStart_time(entity.getStart_time());
        mShopProductItemEntity.setStatus(entity.getStatus());
        mShopProductItemEntity.setStock(entity.getStock());

        Bundle mBundle = getArguments();
        if (mBundle != null) {
            mBundle.putSerializable(PAGE_STATUS, mShopProductItemEntity);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods;
    }

    private ShopProductItemEntity getPageStatus() {
        ShopProductItemEntity entity = (ShopProductItemEntity) getArguments().getSerializable(PAGE_STATUS);
        return entity;
    }

    private int getType() {
        return getArguments().getInt("type");
    }

    @Override
    public void initView() {

        initBaseView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 根据环境配置页面
     */
    private void initBaseView() {
        // 兼容 ViewPager，ptr 和 ViewPager 的滑动冲突
        ptrMaterialStylePtrFrame.disableWhenHorizontalMove(true);
        ptrMaterialStylePtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                try{
                    //刷新商品
                    Map<String,Object> params = new HashMap<>();
                    params.put("id",mShopProductItemEntity.getId());
                    getPresenter().getShopItemDetailNew(params,TYPE_GET_GOODS_DETAIL);
                    isRefresh = true;
                }catch (Exception e){}
            }
        });
        if (getType() == 0) {
            //狂抢好物
            ttvDateTag.setText(getString(R.string.text_home_countdown));
            ttvBtnRight.setText(getString(R.string.text_home_grab_it));
        } else {
            //即将开抢
            ttvDateTag.setText(getString(R.string.text_home_time_limit));
            ttvBtnRight.setText(getString(R.string.text_home_reminder));
        }
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    /**
     * 填充数据
     *
     * @param mShopProductItemEntity
     */
    public void setData(ShopProductItemEntity mShopProductItemEntity) {
        this.mShopProductItemEntity = mShopProductItemEntity;
//        Log.e("yqy",this.mShopProductItemEntity + "");
//        updateEntity(mShopProductItemEntity);

        Bundle mBundle = getArguments();
        if (mBundle != null) {
            mBundle.putSerializable(PAGE_STATUS, mShopProductItemEntity);
        }
        updateView();

    }

    /**
     * 填充数据
     * 刷新数据
     */
    private void updateView(){
        mShopProductItemEntity = getPageStatus();
        try {
            ttvProductName.setText(mShopProductItemEntity.getName());
//        ttvDate.setText(mShopProductItemEntity.getEnd_time() + "");
            ttvShopName.setText(mShopProductItemEntity.getBusiness().getName());
            ttvProductContent.setText(mShopProductItemEntity.getDescription());
            ttvGoodsCount.setText(mShopProductItemEntity.getCurrent_stock() + "");
            String count = mShopProductItemEntity.getStock() - mShopProductItemEntity.getCurrent_stock() + "";
            if (getType() == 1) {
                ttvProductTagLeft.setText(getResources().getString(R.string.text_unit_comming_left));
                ttvProductTag.setText(getResources().getString(R.string.text_unit_comming_right));
            } else {
                ttvProductTagLeft.setText(getResources().getString(R.string.text_unit_left));
                ttvProductTag.setText(getResources().getString(R.string.text_unit_right));
            }
            ttvProductCount.setText(count);
            String image = mShopProductItemEntity.getImages();
            GlideUtil.defaultLoad(getActivity(), mShopProductItemEntity.getBusiness().getAvatar(), imgShopAvatar);
//        GlideUtil.defaultLoad(getActivity(),images.split(",")[0],timgProductAvatar);
            String[] images = image.split(",");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < images.length; i++) {
                list.add(images[i]);
            }
            mBannerAdapter.setData(list);
            mSliderBanner.setAdapter(mBannerAdapter);
            mIndicatorContainer.setNum(list.size());
            mSliderBanner.beginPlay();
            llLayoutTime.setVisibility(View.VISIBLE);
            starTimer(mShopProductItemEntity.getStart_time(), mShopProductItemEntity.getEnd_time());
            if (getType() == 0) {
                if (mShopProductItemEntity.getCurrent_stock() == mShopProductItemEntity.getStock()) {
                    ttvBtnRight.setText(getString(R.string.text_home_ran_out));
                    ttvBtnRight.setBackgroundColor(Color.parseColor("#66191917"));
                    ttvBtnRight.setEnabled(false);
                } else {
                    if (mShopProductItemEntity.getUser_coupon() != null) {
                        //已抢到
                        ttvBtnRight.setText(getString(R.string.text_home_got_it));
                        ttvBtnRight.setBackgroundColor(Color.parseColor("#66191917"));
                        ttvBtnRight.setEnabled(false);
                    } else {
                        //没抢
                        ttvBtnRight.setText(getString(R.string.text_home_grab_it));
                        ttvBtnRight.setBackgroundColor(Color.parseColor("#191917"));
                        ttvBtnRight.setEnabled(true);
                    }
                }
            } else {
                if (mShopProductItemEntity.getUser_item_notice() != null) {
                    //已抢到
                    ttvBtnRight.setText(getString(R.string.text_home_grabit_reminder_set));
                    ttvBtnRight.setBackgroundColor(Color.parseColor("#66191917"));
                    ttvBtnRight.setEnabled(false);
                } else {
                    //没抢
                    ttvBtnRight.setText(getString(R.string.text_home_reminder));
                    ttvBtnRight.setBackgroundColor(Color.parseColor("#191917"));
                    ttvBtnRight.setEnabled(true);
                }
            }

            ttvAddress.setText(mShopProductItemEntity.getLocation());
            ttvTel.setText(mShopProductItemEntity.getContact_phone());
        }catch (Exception e){}
    }

    @OnClick({R.id.ttv_btn_right, R.id.ll_shop_info})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ttv_btn_right:
                //抢
                if (0 == getType()) {
                    //抢
                    if (mShopProductItemEntity == null) {
                        ToastUtil.shortShow("ERROR");
                    }

                    if (mShopProductItemEntity.getCurrent_stock() == mShopProductItemEntity.getStock()) {
                        ttvBtnRight.setText(getString(R.string.text_home_ran_out));
                        ttvBtnRight.setBackgroundColor(Color.parseColor("#66191917"));
                        DialogUtil.getNoGoodSDialog(getActivity(), true, null).show();
                        return;
                    }

                    Map<String, Object> params = new HashMap<>();
                    params.put("item_id", mShopProductItemEntity.getId());
                    getPresenter().postUserCoupons(params, TYPE_POST_USER_COUPONS);
                    if(ttvBtnRight != null){
                        ttvBtnRight.setEnabled(false);
                    }
                } else {
                    //提醒
                    Map<String, Object> params = new HashMap<>();
                    params.put("item_id", mShopProductItemEntity.getId());
                    showLoadDialog();
                    getPresenter().postShopNotice(params, TYPE_POST_SET_TIP);
                    if(ttvBtnRight != null){
                        ttvBtnRight.setEnabled(false);
                    }
                }
                break;
            case R.id.ll_shop_info:
                //商户信息
                if (mShopProductItemEntity == null) {
                    ToastUtil.shortShow("ERROR");
                }
                if (mShopProductItemEntity.getBusiness().getUrl() != null && !TextUtils.isEmpty(mShopProductItemEntity.getBusiness().getUrl())) {
                    CommonWebActivity.star(getContext(), mShopProductItemEntity.getBusiness().getUrl());
                }
                break;
        }
    }

    /**
     * 抢购过期
     */
    private void ranOut() {
        llRan.setVisibility(View.GONE);
        llBottom.setVisibility(View.VISIBLE);
        hideDownTime();
        if(getType() == 1){
            EventBus.getDefault().post(new ProUpdateEvent());
            return;
        }
        if(!isRefresh && getListSize() > 1){
            EventBus.getDefault().post(new ProUpdateEvent());
        }
    }

    @Override
    public void onRefreshBegin() {

    }

    @Override
    public void onRefreshError() {

    }
}
