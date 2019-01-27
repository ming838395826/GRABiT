package com.android.spin.shop.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.util.image.GlideUtil;
import com.android.spin.R;
import com.android.spin.shop.entity.ShopItemEntity;
import com.android.spin.shop.entity.ShopProductItemEntity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ming on 2019/1/6.
 */

public class ShopItemViewHolder  extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_good_name)
    TextView mTvGoodName;
    @Bind(R.id.tv_count)
    TextView mTvCount;
    @Bind(R.id.iv_url)
    ImageView mIvUrl;

    View itemView;

    public ShopItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void initData(ShopItemEntity entity) {
        GlideUtil.defualtLoad(mIvUrl.getContext(), entity.getFront_cover(), R.mipmap.ic_grabit_default,  mIvUrl);
        mTvGoodName.setText(entity.getName());
        mTvCount.setText(mTvCount.getContext().getString(R.string.text_good_count,entity.getCurrent_stock()));
    }
}
