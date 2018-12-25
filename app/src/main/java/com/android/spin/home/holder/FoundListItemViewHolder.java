package com.android.spin.home.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.base.util.DateUtil;
import com.android.base.util.image.GlideUtil;
import com.android.spin.R;
import com.android.spin.card.entity.CardItemEntity;
import com.android.spin.common.selector.view.CircleImageView;
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

public class FoundListItemViewHolder extends RecyclerView.ViewHolder {

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

    public FoundListItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void initData(ShopProductItemEntity entity) {

    }
}
