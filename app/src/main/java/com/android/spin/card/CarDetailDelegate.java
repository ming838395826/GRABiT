package com.android.spin.card;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
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
import com.android.spin.event.AddCardEvent;
import com.android.spin.home.entity.ProUpdateEvent;
import com.android.spin.shop.entity.ShopProductDetailEntity;
import com.android.spin.shop.presenter.ShopPresenter;
import com.android.spin.util.DialogUtil;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/10/14
 * 1120389276@qq.com
 * 描述：
 */

public class CarDetailDelegate extends MvpDelegate<IView, ShopPresenter>  {

    private static final int DELETE_COUPONS = 0x03;
    private static final int SET_COUPONS_USER = 0x02;

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
    @Bind(R.id.iv_view_left)
    ImageView iv_view_left;
    @Bind(R.id.iv_view_right)
    ImageView iv_view_right;
    @Bind(R.id.timg_back)
    TImageView mTimgBack;
    @Bind(R.id.iv_delete)
    TImageView mIvDelete;
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

//                iv_view_left.setVisibility(View.VISIBLE);
//                iv_view_right.setVisibility(View.VISIBLE);
//                mRlCard.setVisibility(View.GONE);
                dismissLoadDialog();
                break;
            case 1:
                break;
            case SET_COUPONS_USER:
                dismissLoadDialog();
                DialogUtil.havaUseCouponsDialog(this.getActivity(), true, new DialogUtil.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, int position) {
                        finish();
                    }
                }).show();
                EventBus.getDefault().post(new AddCardEvent(0));
                EventBus.getDefault().post(new ProUpdateEvent());
                break;
            case DELETE_COUPONS:
                dismissLoadDialog();
                EventBus.getDefault().post(new AddCardEvent(0));
                EventBus.getDefault().post(new ProUpdateEvent());
                finish();
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
        initTouchListener();
        mRlCard.setDrawingCacheEnabled(true);
//        mRlCard.buildDrawingCache();
        boolean show = getActivity().getSharedPreferences("GLIDE", 200).getBoolean("Show", false);
        if (true||!show) {
            DialogUtil.guideCouponsDialog(this.getActivity(), true, null).show();
            getActivity().getSharedPreferences("GLIDE", 200).edit().putBoolean("Show", true).commit();
        }
        initData();

    }

    private void initData(ShopProductDetailEntity entity) {
        try {
            this.mShopProductDetailEntity = entity;
            if (entity != null) {
                GlideUtil.defaultLoad(this.getActivity(), entity.getFront_cover(), mImgCardAvatar);
                mTtvCardTitle.setText(entity.getName());
                GlideUtil.defualtLoad(this.getActivity(), entity.getBusiness().getAvatar(),R.mipmap.ic_shop_defaut, mImgShopAvatar);
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

    public void saveCroppedImage(Bitmap bmp) {
        // 判断是否可以对SDcard进行操作
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {      // 获取SDCard指定目录下
            String sdCardDir = Environment.getExternalStorageDirectory() + "/BmpImage/";
            //目录转化成文件夹
            File dirFile = new File(sdCardDir);
            //如果不存在，那就建立这个文件夹
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            // 在SDcard的目录下创建图片文,以当前时间为其命名，注意文件后缀，若生成为JPEG则为.jpg,若为PNG则为.png
            File file = new File(sdCardDir, System.currentTimeMillis() + ".jpg");
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

    GestureDetector detectordetector;

    public void initTouchListener() {
        detectordetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY) {
                float minMove = 120; // 最小滑动距离
                float minVelocity = 0; // 最小滑动速度
                float beginX = motionEvent.getX();
                float endX = motionEvent1.getX();
                float beginY = motionEvent.getY();
                float endY = motionEvent1.getY();
                float distanceY = Math.abs(beginY - endY);
                float distanceX = Math.abs(beginX - endX);

                if ((distanceX > distanceY) && beginX - endX > minMove && Math.abs(velocityX) > minVelocity) { // 左滑

                } else if ((distanceX > distanceY) && endX - beginX > minMove && Math.abs(velocityX) > minVelocity) { // 右滑

                } else if ((distanceY > distanceX) && beginY - endY > minMove && Math.abs(velocityY) > minVelocity) { // 上滑

                } else if ((distanceY > distanceX) && endY - beginY > minMove && Math.abs(velocityY) > minVelocity) { // 下滑
                    //实现爆炸动画
//                    ExplosionField mExplosionField = ExplosionField.attach2Window(getActivity());
//                    mExplosionField.explode(mRlCard);
                    //弹出确认领取提示框
                    DialogUtil.useCouponsDialog(getActivity(), false, mShopProductDetailEntity.getName(), new DialogUtil.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, View view, int type) {
                            switch (type){
                                case 1:
                                    Bitmap viewBitmap = BitMapUtil.getViewToBitmap(mRlCard);
                                    int width = viewBitmap.getWidth();
                                    int height = viewBitmap.getHeight();
                                    Bitmap left = Bitmap.createBitmap(viewBitmap, 0, 0,
                                            width / 2, height);
                                    Bitmap right = Bitmap.createBitmap(viewBitmap, width / 2, 0,
                                            width / 2, height);
                                    iv_view_left.setImageBitmap(left);
                                    iv_view_right.setImageBitmap(right);

                                    AnimationSet LeftanimationSet = new AnimationSet(true);
                                    LeftanimationSet.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            iv_view_left.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                    AnimationSet RightanimationSet = new AnimationSet(true);

                                    RightanimationSet.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            iv_view_right.setVisibility(View.GONE);
                                            getPrensenter().setUserCoupons(getIntent().getStringExtra("USE_ID"), SET_COUPONS_USER, 0);

                                            DialogUtil.useCouponsDialog(getActivity(), false, mShopProductDetailEntity.getName(), new DialogUtil.OnClickListener() {
                                                @Override
                                                public void onClick(Dialog dialog, View view, int type) {
                                                    switch (type) {
                                                        case 1:
                                                            showLoadDialog();
                                                            getPrensenter().setUserCoupons(getIntent().getStringExtra("USE_ID"), SET_COUPONS_USER, 0);
                                                            break;
                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                    RotateAnimation leftanim = new RotateAnimation(0f, -110f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
                                    RotateAnimation rightanim = new RotateAnimation(0f, 110f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
                                    leftanim.setFillAfter(true); // 设置保持动画最后的状态
                                    leftanim.setDuration(2000); // 设置动画时间
                                    leftanim.setInterpolator(new AccelerateInterpolator()); // 设置插入器

                                    rightanim.setFillAfter(true); // 设置保持动画最后的状态
                                    rightanim.setDuration(2000); // 设置动画时间
                                    rightanim.setInterpolator(new AccelerateInterpolator()); // 设置插入器

                                    AlphaAnimation LeftalphaAnimation = new AlphaAnimation(1, 0);
                                    LeftalphaAnimation.setDuration(2000);
                                    AlphaAnimation RightalphaAnimation = new AlphaAnimation(1, 0);
                                    RightalphaAnimation.setDuration(2000);
                                    LeftanimationSet.addAnimation(leftanim);
                                    LeftanimationSet.addAnimation(LeftalphaAnimation);

                                    RightanimationSet.addAnimation(rightanim);
                                    RightanimationSet.addAnimation(RightalphaAnimation);

                                    mRlCard.setVisibility(View.GONE);
                                    iv_view_left.setVisibility(View.VISIBLE);
                                    iv_view_right.setVisibility(View.VISIBLE);

                                    iv_view_right.startAnimation(RightanimationSet);
                                    iv_view_left.startAnimation(LeftanimationSet);
                                    break;
                            }
                        }
                    }).show();




                }
                return false;
            }
        });
        mRlCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detectordetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    @OnClick({R.id.timg_back, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.timg_back:
                finish();
                break;
            case R.id.iv_delete:
                DialogUtil.deleteCouponsDialog(this.getActivity(), false, new DialogUtil.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, int type) {
                        switch (type) {
                            case 1:
                                showLoadDialog();
                                getPrensenter().deleteUserCoupons(getIntent().getStringExtra("USE_ID"), DELETE_COUPONS, 0);
                                break;
                        }
                    }
                }).show();
                break;
        }
    }
}
