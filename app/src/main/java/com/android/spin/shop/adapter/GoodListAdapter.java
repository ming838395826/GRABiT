package com.android.spin.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.view.listview.adapter.BaseListAdapter;
import com.android.spin.R;
import com.android.spin.shop.holder.ShopListItemViewHolder;
import com.android.spin.shop.entity.ShopProductItemEntity;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：
 */

public class GoodListAdapter extends BaseListAdapter<ShopProductItemEntity> {

    private String status;

    public GoodListAdapter(Context activity) {
        super(activity);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_found, parent, false);
        ShopListItemViewHolder vh = new ShopListItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShopListItemViewHolder newHolder = (ShopListItemViewHolder) holder;
        newHolder.initData(getDataList().get(position));
    }
}