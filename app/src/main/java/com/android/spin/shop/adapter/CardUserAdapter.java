package com.android.spin.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.view.listview.adapter.BaseListAdapter;
import com.android.spin.R;
import com.android.spin.common.selector.view.CircleImageView;
import com.android.spin.shop.entity.CardUserEntity;
import com.android.spin.shop.holder.CardUserViewHolder;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;

/**
 * Created by ming on 2019/1/10.
 */

public class CardUserAdapter extends BaseListAdapter<CardUserEntity> {


    public CardUserAdapter(Context activity) {
        super(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_card_user, parent, false);
        CardUserViewHolder vh = new CardUserViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CardUserViewHolder newHolder = (CardUserViewHolder) holder;
        newHolder.initData(getDataList().get(position));
    }
}
