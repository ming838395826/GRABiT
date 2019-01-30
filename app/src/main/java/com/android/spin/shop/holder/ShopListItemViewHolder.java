package com.android.spin.shop.holder;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.util.DateUtil;
import com.android.base.util.image.GlideUtil;
import com.android.base.view.listview.ListItemDecoration;
import com.android.spin.R;
import com.android.spin.card.entity.CardItemEntity;
import com.android.spin.common.selector.view.CircleImageView;
import com.android.spin.shop.adapter.GoodItemAdapter;
import com.android.spin.shop.adapter.GoodItemListAdapter;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.util.FormatUtil;
import com.android.spin.view.NoScrollListView;
import com.android.spin.view.NoScrollRecyclerView;
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
    @Bind(R.id.ttv_date_second)
    TTextView mTtvDateSecond;
    @Bind(R.id.tv_submit)
    TTextView mTvSubmit;
    @Bind(R.id.tr_good_list)
    NoScrollListView mTrGoodList;
    @Bind(R.id.ln_have_grabit)
    RelativeLayout ln_have_grabit;
    @Bind(R.id.iv_person_one)
    CircleImageView iv_person_one;
    @Bind(R.id.iv_person_two)
    CircleImageView iv_person_two;
    @Bind(R.id.iv_person_third)
    CircleImageView iv_person_third;
    @Bind(R.id.iv_person_four)
    CircleImageView iv_person_four;
    @Bind(R.id.ln_time)
    LinearLayout ln_time;
    @Bind(R.id.tv_show_all)
    TTextView tv_show_all;

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

    public void OnChildItem(AdapterView.OnItemClickListener listener){
        mTrGoodList.setOnItemClickListener(listener);
    }

    public void Recevier(View.OnClickListener listener){
        mTvSubmit.setOnClickListener(listener);
    }

    public void showPerson(View.OnClickListener listener){
        ln_have_grabit.setOnClickListener(listener);
    }

    public void showAll(View.OnClickListener listener){
        tv_show_all.setOnClickListener(listener);
    }

    public void setSubmitRanOut(){
        mTvSubmit.setText(mTvSubmit.getContext().getString(R.string.text_home_ran_out));
        mTvSubmit.setTextColor(Color.parseColor("#333333"));
        mTvSubmit.setBackgroundResource(R.color.app_color_F0F0F0);
        mTvSubmit.setEnabled(false);
    }

    public void setSubmitGotIt(){
        mTvSubmit.setText(mTvSubmit.getContext().getString(R.string.text_home_got_it));
        mTvSubmit.setTextColor(Color.parseColor("#333333"));
        mTvSubmit.setBackgroundResource(R.color.app_color_F0F0F0);
        mTvSubmit.setEnabled(false);
    }

    public void setAttend(){
        mTvSubmit.setText(mTvSubmit.getContext().getString(R.string.text_home_Concern));
        mTvSubmit.setTextColor(Color.parseColor("#333333"));
        mTvSubmit.setBackgroundResource(R.drawable.btn_border_color_191917_2px);
        mTvSubmit.setEnabled(false);
    }

    public void setHaveAttend(){
        mTvSubmit.setText(mTvSubmit.getContext().getString(R.string.text_home_have_Concern));
        mTvSubmit.setTextColor(Color.parseColor("#333333"));
        mTvSubmit.setBackgroundResource(R.color.app_color_F0F0F0);
        mTvSubmit.setEnabled(false);
    }

    public void setSubmitGrabIt(){
        mTvSubmit.setText(mTvSubmit.getContext().getString(R.string.text_home_grab_it));
        mTvSubmit.setTextColor(Color.parseColor("#FFFFFF"));
        mTvSubmit.setBackgroundResource(R.color.app_color_FF4141);
        mTvSubmit.setEnabled(true);
    }

    public void initData(ShopProductItemEntity entity) {
        GlideUtil.defualtLoad(mImgShopAvatar.getContext(), entity.getBusiness().getAvatar(),R.mipmap.ic_shop_defaut, mImgShopAvatar);
        mTvShopName.setText(entity.getBusiness().getName());
        mTvContent.setText(entity.getBusiness().getDescription());
        boolean isRanOut=true;
        for (int i=0;i<entity.getItems().size();i++){
            if(entity.getItems().get(i).getCurrent_stock() < entity.getItems().get(i).getStock()){
                isRanOut=false;
                break;
            }
        }
        if(getType()==0){
            if(isRanOut){
                setSubmitRanOut();
            }else {
                if (entity.getIsRecerve() != null&&entity.getIsRecerve()==0) {
                    setSubmitGrabIt();
                }else {
                    setSubmitGotIt();
                }
            }
        }else if(getType()==1){
            if (entity.getUser_item_notice() == null) {
                setAttend();
            }else {
                setHaveAttend();
            }
        }else if(getType()==2){
            mTvSubmit.setVisibility(View.GONE);
            ln_time.setVisibility(View.GONE);
        }

        if(!entity.isShowAll()&&entity.getItems().size()>3){
            tv_show_all.setVisibility(View.VISIBLE);
        }else {
            tv_show_all.setVisibility(View.GONE);
        }


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//        mTrGoodList.setLayoutManager(layoutManager);
//        mTrGoodList.addItemDecoration(new ListItemDecoration(
//                mTrGoodList.getContext(), LinearLayout.VERTICAL, mTrGoodList.getContext().getResources().getDrawable(R.drawable.list_divider_h10_tran)));
        GoodItemAdapter goodItemAdapter=new GoodItemAdapter(mTrGoodList.getContext());
        goodItemAdapter.setShowAll(entity.isShowAll());
        GoodItemListAdapter goodItemListAdapter=new GoodItemListAdapter(mTrGoodList.getContext());
        goodItemListAdapter.setShowAll(entity.isShowAll());
        goodItemAdapter.addDataList(entity.getItems());
        goodItemListAdapter.setData(entity.getItems());
        mTrGoodList.setAdapter(goodItemListAdapter);
        if(entity.getUserList()==null){
            iv_person_one.setVisibility(View.GONE);
            iv_person_two.setVisibility(View.GONE);
            iv_person_third.setVisibility(View.GONE);
            iv_person_four.setVisibility(View.GONE);
        }else {
            for (int i=0;i<entity.getUserList().size();i++){
                switch (i){
                    case 0:
                        iv_person_one.setVisibility(View.VISIBLE);
                        GlideUtil.defualtLoad(iv_person_one.getContext(), entity.getUserList().get(i).getUser().getAvatar(),R.mipmap.icon_get_person_header, iv_person_one);
                        break;
                    case 1:
                        iv_person_two.setVisibility(View.VISIBLE);
                        GlideUtil.defualtLoad(iv_person_two.getContext(), entity.getUserList().get(i).getUser().getAvatar(),R.mipmap.icon_get_person_header, iv_person_two);
                        break;
                    case 2:
                        iv_person_third.setVisibility(View.VISIBLE);
                        GlideUtil.defualtLoad(iv_person_third.getContext(), entity.getUserList().get(i).getUser().getAvatar(),R.mipmap.icon_get_person_header, iv_person_third);
                        break;
                    case 3:
                        iv_person_four.setVisibility(View.VISIBLE);
                        GlideUtil.defualtLoad(iv_person_four.getContext(), entity.getUserList().get(i).getUser().getAvatar(),R.mipmap.icon_get_person_header, iv_person_four);
                        break;
                }
            }
        }
        if(getType()==0){
            starTimer(entity.getStart_time(), entity.getEnd_time());
        }else if(getType()==1){
            starTimer(entity.getStart_time(), entity.getStart_time());
        }
    }

    private CountDownTimer timer;
    private void starTimer(long star, long end) {

        long current = System.currentTimeMillis() / 1000;
        if (end <= current) {
            mTtvDateHour.setText("00");
            mTtvDateMin.setText("00");
            mTtvDateDay.setText("00");
            mTtvDateSecond.setText("00");
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
//                    if (day > 0) {
//                        mTtvDateDay.setVisibility(View.VISIBLE);
//                        mTtvDateDayUnit.setVisibility(View.VISIBLE);
//                        mTtvDateDay.setText(day + "");
//                    } else {
//                        mTtvDateDay.setVisibility(View.GONE);
//                        mTtvDateDayUnit.setVisibility(View.GONE);
//                    }
                    mTtvDateDay.setText(FormatUtil.formatTime(day));
                    mTtvDateHour.setText(FormatUtil.formatTime(hour));
                    mTtvDateMin.setText(FormatUtil.formatTime(min));
                    mTtvDateSecond.setText(FormatUtil.formatTime(s));
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
                mTtvDateSecond.setText("00");
            }
        };

        timer.start();
    }
}
