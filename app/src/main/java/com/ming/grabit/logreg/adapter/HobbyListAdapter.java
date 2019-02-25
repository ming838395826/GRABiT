package com.ming.grabit.logreg.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.view.listview.adapter.BaseListAdapter;
import com.ming.grabit.R;
import com.ming.grabit.logreg.entity.HobbyItemEntity;
import com.ming.grabit.logreg.viewholder.HobbyListViewHolder;

/**
 * 兴趣爱好列表Ø
 */
public class HobbyListAdapter extends BaseListAdapter<HobbyItemEntity> {

    private Context context;

    public HobbyListAdapter(Activity activity) {
        super(activity);
        context = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_hobby, parent, false);
        HobbyListViewHolder vh = new HobbyListViewHolder(v);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HobbyListViewHolder newHolder = (HobbyListViewHolder) holder;
        newHolder.initData(getDataList().get(position));
    }
}
