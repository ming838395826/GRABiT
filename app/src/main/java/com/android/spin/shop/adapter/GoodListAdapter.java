package com.android.spin.shop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.util.ToastUtil;
import com.android.base.view.listview.adapter.BaseListAdapter;
import com.android.spin.R;
import com.android.spin.shop.holder.ShopListItemViewHolder;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.util.DialogUtil;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ShopListItemViewHolder newHolder = (ShopListItemViewHolder) holder;
        newHolder.initData(getDataList().get(position));
        if(getDataList().get(position).getIsRecerve()==null){
            if(onViewClickListener!=null){
                onViewClickListener.checkRecevoerStatus(position);
            }
        }
        newHolder.Recevier(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItemCount()>position) {
                    ToastUtil.shortShow("ERROR");
                }
                boolean isRanOut=true;
                for (int i=0;i<getItem(position).getItems().size();i++){
                    if(getItem(position).getItems().get(i).getCurrent_stock() < getItem(position).getItems().get(i).getStock()){
                        isRanOut=false;
                        break;
                    }
                }
                if (isRanOut) {//如果已经满库存
                    newHolder.setSubmitRanOut();
                    DialogUtil.getNoGoodSDialog(getmActivity(), true, null).show();
                    return;
                }
                if(onViewClickListener!=null){
                    onViewClickListener.recevier(position);
                }
            }
        });
        newHolder.showPerson(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onViewClickListener!=null){
                    onViewClickListener.showPerson(position);
                }
            }
        });
    }

    private OnViewClickListener onViewClickListener;

    public OnViewClickListener getOnViewClickListener() {
        return onViewClickListener;
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public interface OnViewClickListener{
        void recevier(int position);

        void showPerson(int position);

        void checkRecevoerStatus(int position);
    }
}
