package com.ming.grabit.shop.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.base.util.image.GlideUtil;
import com.ming.grabit.R;
import com.ming.grabit.common.selector.view.CircleImageView;
import com.ming.grabit.shop.entity.CardUserEntity;
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
        String userName=entity.getUser().getName();
        if(entity.getUser().getName().length()>2){
            userName=entity.getUser().getName().substring(0,1)+entity.getUser().getName().substring(0,1)+"*****";
        }
        mTtvName.setText(userName);
        mTtvTime.setText(entity.getUpdated_at().substring(5,16));

    }
}
