package com.android.spin.shop.holder;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.util.DateUtil;
import com.android.base.util.image.GlideUtil;
import com.android.base.view.listview.ListItemDecoration;
import com.android.spin.R;
import com.android.spin.card.entity.CardItemEntity;
import com.android.spin.common.selector.view.CircleImageView;
import com.android.spin.shop.adapter.GoodItemAdapter;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TRecyclerView;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：
 */

public class ShopListItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_shop_avatar)
    CircleImageView mImgShopAvatar;
    @Bind(R.id.tv_shop_name)
    TextView mTvShopName;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.ttv_date_tag)
    TTextView mTtvDateTag;
    @Bind(R.id.ttv_date_day)
    TTextView mTtvDateDay;
    @Bind(R.id.ttv_date_day_unit)
    TTextView mTtvDateDayUnit;
    @Bind(R.id.ttv_date_hour)
    TTextView mTtvDateHour;
    @Bind(R.id.ttv_date_min)
    TTextView mTtvDateMin;
    @Bind(R.id.tv_submit)
    TTextView mTvSubmit;
    @Bind(R.id.tr_good_list)
    TRecyclerView mTrGoodList;

    View itemView;
    int type=0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ShopListItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void Recevier(View.OnClickListener listener){
        mTvSubmit.setOnClickListener(listener);
    }

    public void setSubmitRanOut(){
        mTvSubmit.setText(mTvSubmit.getContext().getString(R.string.text_home_ran_out));
        mTvSubmit.setBackgroundColor(Color.parseColor("#66191917"));
        mTvSubmit.setEnabled(false);
    }

    public void setSubmitGotIt(){
        mTvSubmit.setText(mTvSubmit.getContext().getString(R.string.text_home_got_it));
        mTvSubmit.setBackgroundColor(Color.parseColor("#66191917"));
        mTvSubmit.setEnabled(false);
    }

    public void setSubmitGrabIt(){
        mTvSubmit.setText(mTvSubmit.getContext().getString(R.string.text_home_grab_it));
        mTvSubmit.setBackgroundColor(Color.parseColor("#191917"));
        mTvSubmit.setEnabled(true);
    }

    public void initData(ShopProductItemEntity entity) {
        GlideUtil.defaultLoad(mImgShopAvatar.getContext(), entity.getBusiness().getAvatar(), mImgShopAvatar);
        mTvShopName.setText(entity.getBusiness().getName());
        boolean isRanOut=true;
        for (int i=0;i<entity.getItems().size();i++){
            if(entity.getItems().get(i).getCurrent_stock() < entity.getItems().get(i).getStock()){
                isRanOut=false;
                break;
            }
        }
        if(isRanOut){
            setSubmitRanOut();
        }else {
            if (entity.getUser_coupon() != null) {
                setSubmitGotIt();
            }else {
                setSubmitGrabIt();
            }
        }

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mTrGoodList.setLayoutManager(layoutManager);
        mTrGoodList.addItemDecoration(new ListItemDecoration(
                mTrGoodList.getContext(), LinearLayout.VERTICAL, mTrGoodList.getContext().getResources().getDrawable(R.drawable.list_divider_h10_tran)));
        GoodItemAdapter goodItemAdapter=new GoodItemAdapter(mTrGoodList.getContext());
        goodItemAdapter.addDataList(entity.getItems());
        mTrGoodList.setAdapter(goodItemAdapter);
        starTimer(entity.getStart_time(), entity.getEnd_time());
    }

    private CountDownTimer timer;
    private void starTimer(long star, long end) {

        long current = System.currentTimeMillis() / 1000;
        if (end <= current) {
            mTtvDateHour.setText("00");
            mTtvDateMin.setText("00");
            mTtvDateDay.setText("00");
            mTvSubmit.setEnabled(false);
            return;
        }else {
            mTvSubmit.setEnabled(true);
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
                if (mTtvDateHour != null) {
                    if (day > 0) {
                        mTtvDateDay.setVisibility(View.VISIBLE);
                        mTtvDateDayUnit.setVisibility(View.VISIBLE);
                        mTtvDateDay.setText(day + "");
                    } else {
                        mTtvDateDay.setVisibility(View.GONE);
                        mTtvDateDayUnit.setVisibility(View.GONE);
                    }
                    mTtvDateHour.setText(hour + "");
                    mTtvDateMin.setText(min + "");
                    mTtvDateHour.setText(s + "");
                }
            }

            @Override
            public void onFinish() {
                if(mTtvDateDay == null){
                    return;
                }
                mTtvDateDay.setVisibility(View.GONE);
                mTtvDateDayUnit.setVisibility(View.GONE);
                mTtvDateHour.setText("00");
                mTtvDateMin.setText("00");
                mTtvDateHour.setText("00");
            }
        };

        timer.start();
    }
}
