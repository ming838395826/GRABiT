package com.android.spin.home.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.spin.R;
import com.android.spin.view.gallery.BannerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * banner轮播
 * Created by YANGQIYUN on 2016/5/3.
 */
public class HomeBannerAdapter extends BannerAdapter<String> {

    private OnItemClickListener onClickListener;

    @Override
    public View getView(LayoutInflater layoutInflater, final int position) {
        View convertView = layoutInflater.inflate(R.layout.home_banner_item, null);
        String item = getItem(position);
//        FootPrintBanerItemDo item = getItem(position);

//        if (item == null) {
//            return convertView;
//        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.home_banner_pic);
//        imageView.setImageResource(item);

        if (!TextUtils.isEmpty(item)) {
            Glide.with(imageView.getContext())
                    .load(item)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);
        }
        convertView.setTag(new InnerBannerItemDataWrapper(getPositionForIndicator(position), item));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onItemClick(view, position);
                }
            }
        });
        return convertView;
    }

    private class InnerBannerItemDataWrapper {

        private String mItem;
        private int mIndex;

        //        private InnerBannerItemDataWrapper(int index, FootPrintBanerItemDo item) {
//            mIndex = index;
//            mItem = item;
//        }
        private InnerBannerItemDataWrapper(int index, String item) {
            mIndex = index;
            mItem = item;
        }
    }

    /**
     * 暴露的点击监听接口
     */
    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
