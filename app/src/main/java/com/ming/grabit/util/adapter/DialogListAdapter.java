package com.ming.grabit.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.view.listview.adapter.BaseListAdapter;
import com.ming.grabit.R;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

/**
 * 作者：yangqiyun
 * 时间：2017/9/11
 * 1120389276@qq.com
 * 描述：
 */

public class DialogListAdapter extends BaseListAdapter {

    private String[] titles;
    private int[] resIds;

    public void setData(String[] titles, int[] resIds) {
        this.titles = titles;
        this.resIds = resIds;
    }

    public DialogListAdapter(Context activity) {
        super(activity);
    }

    @Override
    public int getItemCount() {
        if (titles == null) {
            return 0;
        }
        return titles.length;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_list_item, parent, false);
        DialogListViewHolder vh = new DialogListViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DialogListViewHolder newHolder = (DialogListViewHolder) holder;
        if(resIds == null || resIds.length == 0){
            newHolder.initData(titles[position]);
        }else{
            newHolder.initData(titles[position],resIds[position]);
        }
    }


    public class DialogListViewHolder extends RecyclerView.ViewHolder {

        TImageView timgIcon;
        TTextView ttvTitle;

        private View itemView;

        public DialogListViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            timgIcon = (TImageView) itemView.findViewById(R.id.timg_icon);
            ttvTitle = (TTextView) itemView.findViewById(R.id.ttv_title);
        }

        public void initData(String title) {
            ttvTitle.setText(title);
            timgIcon.setVisibility(View.GONE);
        }

        public void initData(String title,int resId) {
            ttvTitle.setText(title);
            timgIcon.setImageResource(resId);
        }
    }
}
