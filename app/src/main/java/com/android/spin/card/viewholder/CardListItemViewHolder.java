package com.android.spin.card.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.android.base.util.DateUtil;
import com.android.base.util.image.GlideUtil;
import com.android.spin.R;
import com.android.spin.card.entity.CardItemEntity;
import com.android.spin.common.selector.view.CircleImageView;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：
 */

public class CardListItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_card_avatar)
    TImageView imgCardAvatar;
    @Bind(R.id.ttv_card_title)
    TTextView ttvCardTitle;
    @Bind(R.id.ttv_card_content)
    TTextView ttvCardContent;
    @Bind(R.id.img_shop_avatar)
    CircleImageView imgShopAvatar;
    @Bind(R.id.ttv_shop_name)
    TTextView ttvShopName;
    @Bind(R.id.ttv_card_shop_date)
    TTextView ttvCardShopDate;
    @Bind(R.id.ttv_card_exchange_addr)
    TTextView ttvCardExchangeAddr;
    @Bind(R.id.timg_status)
    TImageView timgStatus;
    @Bind(R.id.tv_to_use)
    TTextView tv_to_use;
    @Bind(R.id.ln_delete)
    LinearLayout ln_delete;
    @Bind(R.id.iv_delete)
    TImageView iv_delete;

    View itemView;

    public CardListItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void setViewStyle() {
        ttvCardTitle.setTextColor(ttvCardTitle.getResources().getColor(R.color.app_font_color_main_title));
        ttvCardContent.setTextColor(ttvCardTitle.getResources().getColor(R.color.app_font_color_main_title_sub));
        ttvShopName.setTextColor(ttvCardTitle.getResources().getColor(R.color.app_font_color_main_title_sub));
        ttvCardShopDate.setTextColor(ttvCardTitle.getResources().getColor(R.color.app_font_color_main_title_sub));
        ttvCardExchangeAddr.setTextColor(ttvCardTitle.getResources().getColor(R.color.app_font_color_main_title_sub));
    }

    public void showSattus(int resid){
        timgStatus.setImageResource(resid);
    }

    public void showDelete(){
        iv_delete.setVisibility(View.VISIBLE);
        tv_to_use.setVisibility(View.GONE);
    }
    public void showUser(){
        iv_delete.setVisibility(View.GONE);
        tv_to_use.setVisibility(View.VISIBLE);
    }


    public void setUserdCoupons(View.OnClickListener listener){
        tv_to_use.setOnClickListener(listener);
    }

    public void setDeleteCoupons(View.OnClickListener listener){
        ln_delete.setOnClickListener(listener);
        iv_delete.setOnClickListener(listener);
    }

    public void initData(CardItemEntity entity) {

        GlideUtil.defualtLoad(imgCardAvatar.getContext(), entity.getItem().getFront_cover(), R.drawable.bg_pic_defualt, imgCardAvatar);
        ttvCardTitle.setText(entity.getName());
        ttvCardContent.setText(entity.getItem().getName());
        GlideUtil.defualtLoad(imgShopAvatar.getContext(), entity.getBusiness().getAvatar(), R.mipmap.ic_shop_defaut,imgShopAvatar);
        ttvShopName.setText(entity.getBusiness().getName());
        ttvCardShopDate.setText(imgCardAvatar.getResources().getString(R.string.text_card_expiration_date) + "：" + DateUtil.format("yyyy.MM.dd", entity.getExpired_time() * 1000));
        ttvCardExchangeAddr.setText(imgCardAvatar.getResources().getString(R.string.text_card_service_address) + "：" + entity.getItem().getLocation());
    }
}
