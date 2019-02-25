package com.ming.grabit.logreg.viewholder;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.base.util.DensityUtil;
import com.ming.grabit.R;
import com.ming.grabit.logreg.entity.HobbyItemEntity;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HobbyListViewHolder extends RecyclerView.ViewHolder {

    protected View itemView;

    @Bind(R.id.img)
    TImageView img;
    @Bind(R.id.ttv_content)
    TTextView ttvContent;
    @Bind(R.id.bg_checked)
    View bgChecked;
    @Bind(R.id.layout_container)
    FrameLayout layoutContainer;

    public HobbyListViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);

        ViewGroup.LayoutParams params = layoutContainer.getLayoutParams();
        int itemwidth = (DensityUtil.getWidth() - DensityUtil.dp2px(41))/3;
        params.width = itemwidth;
//        params.height = itemwidth;
        layoutContainer.setLayoutParams(params);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initData(HobbyItemEntity entity){
//        img.setImageResource(entity.getResId());
        ttvContent.setText(entity.getContent());
        if(entity.isChecked()){
//            bgChecked.setVisibility(View.VISIBLE);
            ttvContent.setTextColor(Color.parseColor("#ffffffff"));
            img.setBackground(itemView.getResources().getDrawable(R.drawable.btn_color_191917));
//            img.setBackground(itemView.getResources().getDrawable(R.drawable.btn_border_color_blue));
        }else{
            ttvContent.setTextColor(Color.parseColor("#ff333333"));
            img.setBackground(itemView.getResources().getDrawable(R.drawable.btn_border_color_191917));
//            img.setBackground(null);
//            bgChecked.setVisibility(View.GONE);

        }
    }

}
