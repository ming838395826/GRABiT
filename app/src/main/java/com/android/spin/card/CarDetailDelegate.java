package com.android.spin.card;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.view.IView;
import com.android.base.util.BitMapUtil;
import com.android.base.util.image.GlideUtil;
import com.android.spin.R;
import com.android.spin.common.selector.view.CircleImageView;
import com.android.spin.common.util.Constant;
import com.android.spin.shop.entity.ShopProductDetailEntity;
import com.android.spin.shop.presenter.ShopPresenter;
import com.android.spin.util.DialogUtil;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * 作者：yangqiyun
 * 时间：2017/10/14
 * 1120389276@qq.com
 * 描述：
 */

public class CarDetailDelegate extends MvpDelegate<IView, ShopPresenter> {


    @Bind(R.id.img_card_avatar)
    TImageView mImgCardAvatar;
    @Bind(R.id.ttv_card_title)
    TTextView mTtvCardTitle;
    @Bind(R.id.ttv_card_content)
    TTextView mTtvCardContent;
    @Bind(R.id.img_shop_avatar)
    CircleImageView mImgShopAvatar;
    @Bind(R.id.ttv_shop_name)
    TTextView mTtvShopName;
    @Bind(R.id.ttv_code)
    TTextView mTtvCode;
    @Bind(R.id.iv_line)
    ImageView mIvLine;
    @Bind(R.id.ttv_card_shop_date)
    TTextView mTtvCardShopDate;
    @Bind(R.id.ttv_card_exchange_addr)
    TTextView mTtvCardExchangeAddr;
    @Bind(R.id.ll_card_bottom)
    LinearLayout mLlCardBottom;
    @Bind(R.id.rl_card)
    RelativeLayout mRlCard;
    @Bind(R.id.iv_view_picture)
    ImageView mIvViewPicture;
    private ShopProductDetailEntity mShopProductDetailEntity;

    @Override
    public void onFail(String code, int type) {
        if (Constant.FAIL_GET_AGAIN_CODE.equals(code)) {
            //已抢到
//            setVisibility(ttvStatus, View.VISIBLE);
        }
    }

    @Override
    public void onComplete(int type) {

    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case 0:
                ShopProductDetailEntity entity = (ShopProductDetailEntity) data;
                mRlCard.setDrawingCacheEnabled(true);
                mRlCard.invalidate();
                initData(entity);


//                Bitmap bmp = mRlCard.getDrawingCache();
//                saveCroppedImage(bmp);
                Bitmap viewBitmap = BitMapUtil.getViewToBitmap(mRlCard);
                mIvViewPicture.setImageBitmap(viewBitmap);
                mIvViewPicture.setVisibility(View.VISIBLE);
//                mRlCard.setVisibility(View.GONE);
                dismissLoadDialog();
                break;
            case 1:
                break;
        }
    }

    @NonNull
    @Override
    protected ShopPresenter createPresenter() {
        return new ShopPresenter();
    }

    private int getShopItemId() {
        return getActivity().getIntent().getIntExtra("id", 0);
    }

    @Override
    public void initWidget() {
        mRlCard.setDrawingCacheEnabled(true);
//        mRlCard.buildDrawingCache();
        boolean show=getActivity().getSharedPreferences("GLIDE",200).getBoolean("Show",false);
        if(!show){
            DialogUtil.guideCouponsDialog(this.getActivity(),false,null);
        }
        initData();

    }

    private void initData(ShopProductDetailEntity entity) {
        try {
            this.mShopProductDetailEntity = entity;
            if (entity != null) {
                GlideUtil.defaultLoad(this.getActivity(), entity.getFront_cover(), mImgCardAvatar);
                mTtvCardTitle.setText(entity.getName());
                GlideUtil.defaultLoad(this.getActivity(), entity.getBusiness().getAvatar(), mImgShopAvatar);
                mTtvShopName.setText(entity.getBusiness().getName());
                mTtvCode.setText(getIntent().getStringExtra("code"));
                mTtvCardExchangeAddr.setText(getString(R.string.text_home_garb_address) + " : " + entity.getLocation());
                mTtvCardShopDate.setText(getString(R.string.text_card_expiration_date) + "：" + entity.getExchange_deadline());
            }
        } catch (Exception e) {
        }
    }

    private void initData() {
        showLoadDialog();
        Map<String, Object> params = new HashMap<>();
        params.put("id", getShopItemId());
        getPrensenter().getShopItemDetail(params, 0);

        Map<String, Object> params1 = new HashMap<>();
        params1.put("id", getShopItemId());
        getPrensenter().getShopItemReceived(params, 1);
    }

//    @OnClick({R.id.timg_back, R.id.ll_shop_info})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.timg_back:
//                finish();
//                break;
//            case R.id.ll_shop_info:
//                if (mShopProductDetailEntity == null) {
//                    break;
//                }
//                if (mShopProductDetailEntity.getBusiness().getUrl() != null && !TextUtils.isEmpty(mShopProductDetailEntity.getBusiness().getUrl())) {
//                    CommonWebActivity.star(this.getActivity(), mShopProductDetailEntity.getBusiness().getUrl());
//                }
//                break;
//        }
//    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_card_detail;
    }

    public  void saveCroppedImage(Bitmap bmp) {
        // 判断是否可以对SDcard进行操作
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED))
        {	  // 获取SDCard指定目录下
            String  sdCardDir = Environment.getExternalStorageDirectory()+ "/BmpImage/";
            //目录转化成文件夹
            File dirFile  = new File(sdCardDir);
            //如果不存在，那就建立这个文件夹
            if (!dirFile .exists()) {
                dirFile .mkdirs();
            }
            // 在SDcard的目录下创建图片文,以当前时间为其命名，注意文件后缀，若生成为JPEG则为.jpg,若为PNG则为.png
            File file = new File(sdCardDir, System.currentTimeMillis()+".jpg");
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                //将bitmap（数值100表示不压缩）存储到out输出流中去，图片格式为JPEG
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
                //bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
