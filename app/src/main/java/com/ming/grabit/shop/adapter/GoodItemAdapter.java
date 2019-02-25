package com.ming.grabit.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.view.listview.adapter.BaseListAdapter;
import com.ming.grabit.R;
import com.ming.grabit.shop.entity.ShopItemEntity;
import com.ming.grabit.shop.holder.ShopItemViewHolder;

/**
 * Created by ming on 2019/1/6.
 */

public class GoodItemAdapter extends BaseListAdapter<ShopItemEntity> {


    private boolean isShowAll=false;

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setShowAll(boolean showAll) {
        isShowAll = showAll;
    }

    public GoodItemAdapter(Context activity) {
        super(activity);
    }

    @Override
    public int getItemCount() {
        if(super.getItemCount()>3&&!isShowAll()){
            return 3;
        }else {
            return super.getItemCount();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_good_item, parent, false);
        ShopItemViewHolder vh = new ShopItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShopItemViewHolder newHolder = (ShopItemViewHolder) holder;
        newHolder.initData(getDataList().get(position));
    }
}
