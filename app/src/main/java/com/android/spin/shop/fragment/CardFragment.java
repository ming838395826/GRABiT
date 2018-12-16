package com.android.spin.shop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.base.base.BaseFragment;
import com.android.base.util.DensityUtil;
import com.android.base.util.image.GlideUtil;
import com.android.spin.R;
import com.android.spin.common.selector.view.CircleImageView;
import com.android.spin.shop.entity.ShopHistroyItemEntity;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;
import butterknife.OnClick;

public class CardFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.timg_avatar)
    TImageView timgAvatar;
    @Bind(R.id.ttv_goods_content)
    TTextView ttvGoodsContent;
    @Bind(R.id.timg_shop_avatar)
    CircleImageView timgShopAvatar;
    @Bind(R.id.ttv_shop_name)
    TTextView ttvShopName;
    @Bind(R.id.ttv_status)
    TTextView ttvStatus;

    public static CardFragment getInstance(ShopHistroyItemEntity card) {
        CardFragment localCardFragment = new CardFragment();
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("card", card);
        localCardFragment.setArguments(localBundle);
        return localCardFragment;
    }

    private ShopHistroyItemEntity getShopHistroyItemEntity() {
        return (ShopHistroyItemEntity) getArguments().getSerializable("card");
    }

    @Override
    public void onDestroy() {
//		this.mCoverImageView.setImageBitmap(null);
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_card;
    }

    @Override
    public void initView() {

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) timgAvatar.getLayoutParams();
        params.height = (int) (DensityUtil.getWidth() * 0.674);
        timgAvatar.setLayoutParams(params);
        setBackgroudColor(Color.TRANSPARENT);
        initdatas();

    }

    private void initdatas() {
        try {
            ShopHistroyItemEntity entity = getShopHistroyItemEntity();
            GlideUtil.defaultLoad(getActivity(), entity.getFront_cover(), timgAvatar);
            ttvGoodsContent.setText(entity.getName());
            ttvShopName.setText(entity.getBusiness().getName());
            GlideUtil.defaultLoad(getActivity(), entity.getBusiness().getAvatar(), timgShopAvatar);
            if(entity.getUser_coupon() == null){
                ttvStatus.setVisibility(View.GONE);
            }else{
                ttvStatus.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
        }

    }

    @OnClick(R.id.layout_base_1)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_base_1:
                ARouter.getInstance().build("/app/HistoryFundDetail").withTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom).
                        withSerializable("entity", getShopHistroyItemEntity()).navigation();
//                HistoryFundDetailActivity.star(getActivity(),getShopHistroyItemEntity());
                break;
        }
    }
}