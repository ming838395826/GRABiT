package com.android.spin.common.delegate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.persenrter.MvpPresenter;
import com.android.base.util.DensityUtil;
import com.android.base.util.ToastUtil;
import com.android.base.view.image.GlideRoundTransform;
import com.android.spin.R;
import com.android.spin.db.UserManager;
import com.android.spin.shop.entity.ShopProductItemEntity;
import com.android.spin.util.image.BlurBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.whatsapp.WhatsApp;

/**
 * 作者：yangqiyun
 * 时间：2017/9/27
 * 1120389276@qq.com
 * 描述：
 */

public class CommonShareDelegate extends MvpDelegate implements View.OnClickListener {

    @Bind(R.id.ttv_share_facebook)
    TTextView ttvShareFacebook;
    @Bind(R.id.ttv_share_whats)
    TTextView ttvShareWhats;
    @Bind(R.id.timg_avatar)
    RoundedImageView timgAvatar;
    @Bind(R.id.timg_close)
    TImageView timgClose;
    @Bind(R.id.timg_bg)
    TImageView timgBg;
    @Bind(R.id.lay)
    FrameLayout lay;
    @Bind(R.id.bottom)
    LinearLayout bottom;
    @Bind(R.id.top)
    FrameLayout top;

    @Override
    public void onFail(String code, int type) {

    }

    @Override
    public void onComplete(int type) {

    }

    @Override
    public void onSuccess(Object data, int type) {

    }

    @NonNull
    @Override
    protected MvpPresenter createPresenter() {
        return null;
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_common_share;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {

            int laHeight = DensityUtil.getHeight() - bottom.getHeight() - top.getHeight() - DensityUtil.dp2px(40);
            int laWidth = DensityUtil.getWidth() - DensityUtil.dp2px(40);
            if (laHeight >= laWidth) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) timgAvatar.getLayoutParams();
                params.width = laWidth;
                params.height = laWidth;
                timgAvatar.setLayoutParams(params);
            }else{
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) timgAvatar.getLayoutParams();
                params.width = laHeight;
                params.height = laHeight;
                timgAvatar.setLayoutParams(params);
            }
            Glide.with(this.getActivity()).load(getShopProductItemEntity().getFront_cover())
                    .transform(new CenterCrop(this.getActivity()), new GlideRoundTransform(this.getActivity(), 5)).into(timgAvatar);
        }
    }

    @Override
    public void initWidget() {

//        lay.setLayoutParams(new ViewGroup.MarginLayoutParams(DensityUtil.getWidth(),DensityUtil.getHeight()));

//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) timgAvatar.getLayoutParams();
//        params.width = (int) (DensityUtil.getWidth() * 0.8);
//        params.height = (int) (DensityUtil.getWidth() * 0.8);
//        timgAvatar.setLayoutParams(params);

//        ttvShareFacebook.setCompoundDrawablesWithIntrinsicBounds();
//        Glide.with(this.getActivity()).load(getShopProductItemEntity().getFront_cover())
//                .transform(new CenterCrop(this.getActivity()),new GlideRoundTransform(this.getActivity(),5)).into(timgAvatar);
        applyBlur();
    }

    @OnClick({R.id.timg_close, R.id.ttv_share_facebook, R.id.ttv_share_whats})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timg_close:
                finish();
                break;
            case R.id.ttv_share_facebook:
                ShareByMob(Facebook.NAME);
//                share(SHARE_MEDIA.FACEBOOK);
                break;
            case R.id.ttv_share_whats:
                Platform platform = ShareSDK.getPlatform(WhatsApp.NAME);
                if(platform == null || !platform.isClientValid()){
                    ToastUtil.shortShow(getString(R.string.text_share_install) + platform.getName());
                    return;
                }
//                ShareByMob(WhatsApp.NAME);
                share(SHARE_MEDIA.WHATSAPP);
                break;

        }
    }

    private void applyBlur() {
        timgBg.setImageBitmap(BlurBuilder.blur(timgBg));
        if (BlurBuilder.isBlurFlag()) {
            timgBg.setVisibility(View.VISIBLE);
        }
    }

    private ShopProductItemEntity getShopProductItemEntity() {
        return (ShopProductItemEntity) getActivity().getIntent().getSerializableExtra("url");
    }

    private void share(SHARE_MEDIA qq) {
        //开启自定义分享页面
        UMImage image = new UMImage(this.getActivity(), getShopProductItemEntity().getFront_cover());
        UMWeb web = new UMWeb("https://api.spin-hk.com/items/" + getShopProductItemEntity().getId() + ".html");
        web.setTitle(UserManager.getInstance().getUser().getName() + getString(R.string.text_share_title_1)
                + getShopProductItemEntity().getName() + getString(R.string.text_share_title_2));//标题
        web.setThumb(image);  //缩略图
        web.setDescription(getShopProductItemEntity().getDescription());//描述
        new ShareAction(this.getActivity())
                .setPlatform(qq)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
//                        Toast.makeText(getActivity(), share_media + " finish", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Toast.makeText(getActivity(), share_media + " Fail Share", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                })
                .withText(UserManager.getInstance().getUser().getName() + getString(R.string.text_share_title_1)
                        + getShopProductItemEntity().getName() + getString(R.string.text_share_title_2))
                .withMedia(web)
                .share();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this.getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    private void ShareByMob(String name){
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(UserManager.getInstance().getUser().getName() + getString(R.string.text_share_title_1)
                + getShopProductItemEntity().getName() + getString(R.string.text_share_title_2));
        sp.setUrl("https://api.spin-hk.com/items/" + getShopProductItemEntity().getId() + ".html");
        sp.setTitleUrl("https://api.spin-hk.com/items/" + getShopProductItemEntity().getId() + ".html");
        sp.setExecuteUrl();
        sp.setImageUrl(getShopProductItemEntity().getFront_cover());
        sp.setTitle(UserManager.getInstance().getUser().getName() + getString(R.string.text_share_title_1)
                + getShopProductItemEntity().getName() + getString(R.string.text_share_title_2));
        Platform fb = ShareSDK.getPlatform(name);
        sp.setShareType(Platform.SHARE_WEBPAGE);
        fb.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                Toast.makeText(getActivity(), platform.getName() + " Success Share", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if(i == 9){
                    ToastUtil.shortShow(getString(R.string.text_share_install) + platform.getName());
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(getActivity(), platform.getName() + " Cancel Share", Toast.LENGTH_SHORT).show();
            }
        });
        fb.share(sp);
    }
}
