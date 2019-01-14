package com.android.spin.shop.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.util.image.GlideUtil;
import com.android.spin.R;
import com.android.spin.common.selector.view.CircleImageView;
import com.android.spin.shop.entity.CardUserEntity;
import com.android.spin.shop.entity.ShopItemEntity;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ming on 2019/1/6.
 */

public class CardUserViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_shop_avatar)
    CircleImageView mImgShopAvatar;
    @Bind(R.id.ttv_name)
    TTextView mTtvName;
    @Bind(R.id.ttv_time)
    TTextView mTtvTime;

    View itemView;

    public CardUserViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void initData(CardUserEntity entity) {
        GlideUtil.defualtLoad(mImgShopAvatar.getContext(), entity.getUser().getAvatar(),R.mipmap.icon_mine_header, mImgShopAvatar);
        mTtvName.setText(entity.getUser().getName());
        mTtvTime.setText(entity.getUpdated_at().substring(5,16));

    }
}
