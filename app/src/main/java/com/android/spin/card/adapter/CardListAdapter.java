package com.android.spin.card.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.util.AppLanguageManager;
import com.android.base.view.listview.adapter.BaseListAdapter;
import com.android.spin.R;
import com.android.spin.card.entity.CardItemEntity;
import com.android.spin.card.viewholder.CardListItemViewHolder;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;

/**
 * 作者：yangqiyun
 * 时间：2017/9/6
 * 1120389276@qq.com
 * 描述：
 */

public class CardListAdapter extends BaseListAdapter<CardItemEntity> {

    private String status;

    public CardListAdapter(Context activity) {
        super(activity);
    }

    public void setStatus(String status){
        this.status = status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card, parent, false);
        CardListItemViewHolder vh = new CardListItemViewHolder(view);
        if("0".equals(status)){
            vh.setViewStyle();
        }else if("1".equals(status)){
            if(AppLanguageManager.isLanguageHK()){
                vh.showSattus(R.mipmap.useda_3);
            }else if(AppLanguageManager.isLanguageCN()){
                vh.showSattus(R.mipmap.usedb_3);
            }else {
                vh.showSattus(R.mipmap.usedc_3);
            }

        }else if("2".equals(status)){
            if(AppLanguageManager.isLanguageHK()){
                vh.showSattus(R.mipmap.overduea_3);
            }else if(AppLanguageManager.isLanguageCN()){
                vh.showSattus(R.mipmap.overdueb_3);
            }else {
                vh.showSattus(R.mipmap.overduec_3);
            }
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CardListItemViewHolder newHolder = (CardListItemViewHolder) holder;
        newHolder.initData(getDataList().get(position));
    }
}
