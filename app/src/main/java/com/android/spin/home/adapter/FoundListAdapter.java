package com.android.spin.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.base.view.listview.adapter.BaseListAdapter;
import com.android.spin.R;
import com.android.spin.card.viewholder.CardListItemViewHolder;
import com.android.spin.common.selector.view.CircleImageView;
import com.android.spin.home.holder.FoundListItemViewHolder;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.taobao.uikit.feature.view.TRecyclerView;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：
 */

public class FoundListAdapter extends BaseListAdapter<ShopProductItemEntity> {

    private String status;

    public FoundListAdapter(Context activity) {
        super(activity);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_found, parent, false);
        FoundListItemViewHolder vh = new FoundListItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FoundListItemViewHolder newHolder = (FoundListItemViewHolder) holder;
        newHolder.initData(getDataList().get(position));
    }
}
