package com.ming.grabit.shop.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.base.util.image.GlideUtil;
import com.ming.grabit.R;
import com.ming.grabit.shop.entity.ShopHistroyItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuzhen
 * @since 2017/07/24
 */
public class CardGalleryAdapter extends PagerAdapter {

    private List<ShopHistroyItemEntity> list = new ArrayList<>();

    public void addList(List<ShopHistroyItemEntity> datas){
        list.addAll(datas);
    }

    public void newList(List<ShopHistroyItemEntity> datas){
        list.clear();
        list.addAll(datas);
    }

    public ShopHistroyItemEntity getShopHistroyItemEntity(int position){
        return list.get(position);
    }

    @Override
    public int getCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = View.inflate(container.getContext(), R.layout.adapter_rhythm_icon, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        GlideUtil.defaultLoad(container.getContext(),list.get(position).getFront_cover(),iv);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object != null && object instanceof View) {
            container.removeView((View) object);
        }
    }
}
