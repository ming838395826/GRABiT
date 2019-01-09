package com.android.spin.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.base.view.listview.adapter.BaseListAdapter;
import com.android.spin.shop.entity.ShopProductItemEntity;

/**
 * Created by ming on 2019/1/10.
 */

public class CardUserAdapter extends BaseListAdapter<ShopProductItemEntity> {
    public CardUserAdapter(Context activity) {
        super(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
