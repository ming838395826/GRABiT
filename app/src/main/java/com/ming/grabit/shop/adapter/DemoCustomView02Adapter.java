package com.ming.grabit.shop.adapter;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.base.util.DensityUtil;
import com.android.base.util.image.GlideUtil;
import com.ming.grabit.R;
import com.ming.grabit.shop.entity.ShopHistroyItemEntity;
import com.ming.grabit.view.tab.RecyclerTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinichi Nishimura on 2015/07/22.
 */
public class DemoCustomView02Adapter
        extends RecyclerTabLayout.Adapter<DemoCustomView02Adapter.ViewHolder> {

//    private DemoImagePagerAdapter mAdapater;

    private List<ShopHistroyItemEntity> histroys = new ArrayList<>();

    public DemoCustomView02Adapter(ViewPager viewPager) {
        super(viewPager);
//        mAdapater = (DemoImagePagerAdapter) mViewPager.getAdapter();
    }

    /**
     * 直接添加
     * @param histroys
     */
    public void addList(List<ShopHistroyItemEntity> histroys){
        if(histroys == null){
            return;
        }
        this.histroys.addAll(histroys);
        notifyDataSetChanged();
    }

    /**
     * 新添加
     * @param histroys
     */
    public void newList(List<ShopHistroyItemEntity> histroys){
        this.histroys.clear();
        addList(histroys);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_custom_view02_tab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Drawable drawable = loadIconWithTint(holder.imageView.getContext(),
//                mAdapater.getImageResourceId(position));

        GlideUtil.defaultLoad(holder.imageView.getContext(),histroys.get(position).getBusiness().getAvatar(),holder.imageView);
        holder.imageView.setSelected(position == getCurrentIndicatorPosition());

        if(position == getCurrentIndicatorPosition()){
            //选中
            ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
            params.width = DensityUtil.dp2px(40);
            params.height = DensityUtil.dp2px(40);
//            holder.imageView.setScaleX(1.6f);
//            holder.imageView.setScaleY(1.6f);
        }else{
            ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
            params.width = DensityUtil.dp2px(25);
            params.height = DensityUtil.dp2px(25);
//            holder.imageView.setScaleX(1.0f);
//            holder.imageView.setScaleY(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return histroys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getViewPager().setCurrentItem(getAdapterPosition());
                }
            });
        }
    }
}
