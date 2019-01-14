package com.android.spin.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * 只包含一种类型的view的Adapter
 * 适用于GridView和ListView
 * 要求 T 为列表中项对应的实体
 * Created by chenmingzhen on 16-6-17.
 */
public abstract class SingleTypeAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> xItems = new ArrayList<>();

    public SingleTypeAdapter(Context context) {
        this.mContext = context;
    }

    public SingleTypeAdapter(Context context, List<T> items) {
        this.mContext = context;
        this.xItems = items;
    }

    public void removeItem(int position){
        if(position<xItems.size()){
            xItems.remove(position);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> items) {
        xItems.addAll(items);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return xItems;
    }

    public void setData(List<T> items) {
        xItems.clear();
        xItems.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        xItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
//        if (ListUtils.isEmpty(xItems)) {
//            return 0;
//        }
        return xItems.size();
    }

    @Override
    public T getItem(int position) {
        return xItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract View getView(int position, View convertView, ViewGroup parent);

    public List<T> getItems() {
        return xItems;
    }

    public Context getContext() {
        return this.mContext;
    }
}
