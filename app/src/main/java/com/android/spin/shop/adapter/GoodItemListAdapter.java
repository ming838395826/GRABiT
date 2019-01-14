package com.android.spin.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.util.image.GlideUtil;
import com.android.spin.R;
import com.android.spin.base.SingleTypeAdapter;
import com.android.spin.shop.entity.ShopItemEntity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ming on 2019/1/15.
 */

public class GoodItemListAdapter extends SingleTypeAdapter<ShopItemEntity> {


    public GoodItemListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_good_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        ShopItemEntity entity = getItem(position);
        GlideUtil.defaultLoad(viewHolder.mIvUrl.getContext(), entity.getFront_cover(), viewHolder.mIvUrl);
        viewHolder.mTvGoodName.setText(entity.getName());
        viewHolder.mTvCount.setText(viewHolder.mTvCount.getContext().getString(R.string.text_good_count,entity.getCurrent_stock()));
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_good_name)
        TextView mTvGoodName;
        @Bind(R.id.tv_count)
        TextView mTvCount;
        @Bind(R.id.iv_url)
        ImageView mIvUrl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
