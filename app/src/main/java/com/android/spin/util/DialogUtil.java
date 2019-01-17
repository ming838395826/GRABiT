package com.android.spin.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.util.DensityUtil;
import com.android.base.util.image.GlideUtil;
import com.android.base.view.RoundAngleImageView;
import com.android.base.view.image.GlideCircleTransform;
import com.android.base.view.image.GlideRoundTransform;
import com.android.base.view.listview.ListItemDecoration;
import com.android.spin.R;
import com.android.spin.common.selector.bean.Image;
import com.android.spin.common.selector.view.RundCircleImageView;
import com.android.spin.home.entity.NoticeResult;
import com.android.spin.util.adapter.DialogListAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TRecyclerView;
import com.taobao.uikit.feature.view.TTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 作者：yangqiyun
 * 时间：2017/7/22
 * 1120389276@qq.com
 * 描述：对话框工具类
 */

public class DialogUtil {

    /**
     * 标准对话框
     * @param mContent
     * @param title
     * @param content
     * @param left
     * @param right
     * @param cancelable
     * @param listener
     * @return
     */
    public static Dialog getStandDialog(Context mContent, String title, String content, String left, String right, boolean cancelable, OnClickListener listener) {

        Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_standard_layout, null);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.88);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);

        TTextView ttvTitle = (TTextView)contentView.findViewById(R.id.ttv_title);
        TTextView ttvContent = (TTextView)contentView.findViewById(R.id.ttv_content);
        TTextView ttvLeft = (TTextView)contentView.findViewById(R.id.ttv_left);
        TTextView ttvRight = (TTextView)contentView.findViewById(R.id.ttv_right);
        if(title != null){
            ttvTitle.setVisibility(View.VISIBLE);
            ttvTitle.setText(title);
        }
        if(content != null){
            ttvContent.setText(content);
        }
        if(left != null){
            ttvLeft.setText(left);
            setOnClickListener(listener,dialog,ttvLeft,0);
        }
        if(right != null){
            ttvRight.setText(right);
            setOnClickListener(listener,dialog,ttvRight,1);
        }
        return dialog;
    }

    /**
     * 设置密码成功dialog
     * @param mContent
     * @param cancelable
     * @param listener
     * @return
     */
    public static Dialog getModifyPwdSuccessDialog(Context mContent,  boolean cancelable, final OnClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_pwd_reset_success_layout, null);
        View btn = contentView.findViewById(R.id.tbtn_to_login);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.8);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);
        //登陆
        setOnClickListener(listener,dialog,btn,1);
        return dialog;
    }

    /**
     * 设置提醒成功dialog
     * @param mContent
     * @param cancelable
     * @param listener
     * @return
     */
    public static Dialog getRemindSetSuccessDialog(Context mContent,  boolean cancelable, final OnClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_remind_reset_success_layout, null);
        View timgClose = contentView.findViewById(R.id.timg_close);
        View btn = contentView.findViewById(R.id.tbtn_to_login);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.8);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);
        //关闭
        setOnClickListener(listener,dialog,timgClose,0);
        //好的
        setOnClickListener(listener,dialog,btn,1);
        return dialog;
    }

    /**
     * 好物抢光dialog
     * @param mContent
     * @param cancelable
     * @param listener
     * @return
     */
    public static Dialog getNoGoodSDialog(Context mContent,  boolean cancelable, final OnClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_no_goods_layout, null);
        View timgClose = contentView.findViewById(R.id.timg_close);
        View btn = contentView.findViewById(R.id.tbtn_to_login);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.8);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);
        //关闭
        setOnClickListener(listener,dialog,timgClose,0);
        //好的
        setOnClickListener(listener,dialog,btn,1);
        return dialog;
    }

    /**
     * 使用优惠券dialog
     * @param mContent
     * @param cancelable
     * @param listener
     * @return
     */
    public static Dialog useCouponsDialog(Context mContent,  boolean cancelable, String couponName,final OnClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_use_coupons_layout, null);
        View timgClose = contentView.findViewById(R.id.timg_close);
        View tbtn_to_cancel = contentView.findViewById(R.id.tbtn_to_cancel);
        View tbtn_to_use = contentView.findViewById(R.id.tbtn_to_use);
        TTextView tv_coupon_name= (TTextView) contentView.findViewById(R.id.tv_coupon_name);
        tv_coupon_name.setText(couponName);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.8);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);
        //关闭
        setOnClickListener(listener,dialog,timgClose,0);
        //好的
        setOnClickListener(listener,dialog,tbtn_to_use,1);
        //关闭
        setOnClickListener(listener,dialog,tbtn_to_cancel,2);
        return dialog;
    }

    /**
     * 删除优惠券dialog
     * @param mContent
     * @param cancelable
     * @param listener
     * @return
     */
    public static Dialog deleteCouponsDialog(Context mContent,  boolean cancelable,final OnClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_delete_coupons_layout, null);
        View timgClose = contentView.findViewById(R.id.timg_close);
        View tbtn_to_cancel = contentView.findViewById(R.id.tbtn_to_cancel);
        View tbtn_to_use = contentView.findViewById(R.id.tbtn_to_use);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.8);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);
        //关闭
        setOnClickListener(listener,dialog,timgClose,0);
        //好的
        setOnClickListener(listener,dialog,tbtn_to_use,1);
        //关闭
        setOnClickListener(listener,dialog,tbtn_to_cancel,2);
        return dialog;
    }

    /**
     * 删除优惠券dialog
     * @param mContent
     * @param cancelable
     * @param listener
     * @return
     */
    public static Dialog havaUseCouponsDialog(Context mContent,  boolean cancelable,final OnClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_hava_use_coupons_layout, null);
        View timgClose = contentView.findViewById(R.id.timg_close);
        View tbtn_to_use = contentView.findViewById(R.id.tbtn_to_use);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.8);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);
        //关闭
        setOnClickListener(listener,dialog,timgClose,0);
        //好的
        setOnClickListener(listener,dialog,tbtn_to_use,1);
        return dialog;
    }


    public static Dialog guideCouponsDialog(Context mContent,  boolean cancelable,final OnClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_glide, null);
        ImageView iv_figure= (ImageView) contentView.findViewById(R.id.iv_figure);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.8);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Animation shake = AnimationUtils.loadAnimation(mContent, R.anim.shake);//加载动画资源文件
        iv_figure.startAnimation(shake);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        translateAnimation.setDuration(2000);
        //关闭
//        setOnClickListener(listener,dialog,timgClose,0);
//        //好的
//        setOnClickListener(listener,dialog,tbtn_to_use,1);
        return dialog;
    }

    public static Dialog getLoginDialog(Context mContent,  boolean cancelable,final OnClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_login, null);
        dialog.setContentView(contentView);
        View timgClose = contentView.findViewById(R.id.timg_close);
        View tbtn_to_use = contentView.findViewById(R.id.tbtn_to_use);
        View tbtn_to_cancel = contentView.findViewById(R.id.tbtn_to_cancel);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.8);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);
        //关闭
        setOnClickListener(listener,dialog,timgClose,0);
        setOnClickListener(listener,dialog,tbtn_to_cancel,2);
        //好的
        setOnClickListener(listener,dialog,tbtn_to_use,1);

        return dialog;
    }

    /**
     * 抽中dialog
     * @param mContent
     * @param cancelable
     * @param listener
     * @return
     */
    public static Dialog getGetterDialog(Context mContent,  boolean cancelable, String title ,String iamge,final OnClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_getter_layout, null);
        View timgClose = contentView.findViewById(R.id.timg_close);
        View btn = contentView.findViewById(R.id.tbtn_to_login);
        TTextView tv_good_name= (TTextView) contentView.findViewById(R.id.tv_good_name);
        TImageView iv_url= (TImageView) contentView.findViewById(R.id.iv_url);
        tv_good_name.setText(title);
        GlideUtil.defaultLoad(mContent, iamge, iv_url);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.8);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);
        //关闭
        setOnClickListener(listener,dialog,timgClose,0);
        //好的
        setOnClickListener(listener,dialog,btn,1);
        return dialog;
    }

    /**
     * 首页公告
     * @param mContent
     * @param cancelable
     * @param listener
     * @return
     */
    public static Dialog getHomeNoticeDialog(Context mContent,  boolean cancelable, final OnClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_getter_layout, null);
        View timgClose = contentView.findViewById(R.id.timg_close);
        View btn = contentView.findViewById(R.id.tbtn_to_login);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = (int) (DensityUtil.getWidth() * 0.8);
        contentView.setLayoutParams(params);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);
        //关闭
        setOnClickListener(listener,dialog,timgClose,0);
        //好的
        setOnClickListener(listener,dialog,btn,1);
        return dialog;
    }

    /**
     * 分享
     * @param mContent
     * @param listener
     * @return
     */
    public static Dialog getShareDialog(Context mContent, OnClickListener listener) {

        Dialog dialog = new Dialog(mContent, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_share_layout, null);
        dialog.setContentView(contentView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

        TImageView imgClose = (TImageView)contentView.findViewById(R.id.img_close);
        FrameLayout flShareFacebook = (FrameLayout)contentView.findViewById(R.id.fl_share_facebook);
        setOnClickListener(listener,dialog,imgClose,0);
        setOnClickListener(listener,dialog,flShareFacebook,1);
        return dialog;
    }

    /**
     * 公告
     * @param mContent
     * @param listener
     * @return
     */
    public static Dialog getHomeNotice(Context mContent, NoticeResult result,OnClickListener listener) {

        Dialog dialog = new Dialog(mContent, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_home_notice, null);
        dialog.setContentView(contentView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);

        TImageView imgClose = (TImageView)contentView.findViewById(R.id.timg_close);
        RoundedImageView timg_ = (RoundedImageView)contentView.findViewById(R.id.timg_);
        TTextView tbtn = (TTextView)contentView.findViewById(R.id.tbtn);
//        timg_.setDrawableRadius(50f);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) timg_.getLayoutParams();
//        params.width = DensityUtil.dp2px(200);
//        params.height = DensityUtil.dp2px((float) 266.7);
//        timg_.setLayoutParams(params);
        Glide.with(mContent).load(result.getValue().getImg())
                .centerCrop().transform(new CenterCrop(mContent),new GlideRoundTransform(mContent,5)).into(timg_);
//        GlideUtil.defualtLoad(mContent,result.getValue().getImg(),R.drawable.bg_pic_defualt,timg_);
        try {
            if(TextUtils.isEmpty(result.getValue().getUrl())){
                tbtn.setText(mContent.getResources().getString(R.string.text_ok));
            }else{
                tbtn.setText(mContent.getResources().getString(R.string.text_notice_go));
            }
        }catch (Exception e){}

        setOnClickListener(listener,dialog,imgClose,0);
        setOnClickListener(listener,dialog,tbtn,1);
        return dialog;
    }

    /**
     * 抢到
     * @param mContent
     * @param listener
     * @return
     */
    public static Dialog getReceive(Context mContent,OnClickListener listener) {

        Dialog dialog = new Dialog(mContent, R.style.StandDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_gift_receive, null);
        dialog.setContentView(contentView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setWindowAnimations(R.style.StandDialog_Animation);

        ImageView imgClose = (ImageView)contentView.findViewById(R.id.iv_close);
        TTextView tbtn = (TTextView)contentView.findViewById(R.id.tv_ok);

        setOnClickListener(listener,dialog,imgClose,0);
        setOnClickListener(listener,dialog,tbtn,1);
        return dialog;
    }

    /**
     * 获取listDialog
     * @param mContent
     * @param iconResId
     * @param dataList
     * @param listener
     * @return
     */
    public static Dialog getListDialog(Context mContent, String title, int[] iconResId, String[] dataList, TRecyclerView.OnItemClickListener listener) {

        final Dialog dialog = new Dialog(mContent, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContent).inflate(R.layout.dialog_list_layout, null);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.width = DensityUtil.getWidth();
        contentView.setLayoutParams(params);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

        TImageView imgClose = (TImageView)contentView.findViewById(R.id.img_close);
        TTextView ttvTitle = (TTextView)contentView.findViewById(R.id.ttv_title);
        TRecyclerView trvList = (TRecyclerView) contentView.findViewById(R.id.trv_list);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        trvList.addItemDecoration(new ListItemDecoration(
                mContent, LinearLayout.VERTICAL, mContent.getResources().getDrawable(R.drawable.list_divider_1px)));
        DialogListAdapter adapter = new DialogListAdapter(mContent);
        adapter.setData(dataList,iconResId);
        trvList.setLayoutManager(layoutManager);
        trvList.setAdapter(adapter);
        ttvTitle.setText(title);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
        trvList.setOnItemClickListener(listener);
        return dialog;
    }

    /**
     * 获取默认加载dialog
     * @return
     */
    public static ProgressDialog getDefaultLoadDialog(Context mContext){
        if(mContext == null){
            return null;
        }
        ProgressDialog mProgressDialog = new ProgressDialog(mContext);;
        return mProgressDialog;
    }

    public interface OnClickListener{
        void onClick(Dialog dialog,View view,int position);
    }

    /**
     * 封装点击方法，复用代码，避免空指针
     * @param listener
     * @param dialog
     * @param view
     * @param position
     */
    public static void setOnClickListener(final OnClickListener listener,
                                          final Dialog dialog,final View view,final int position){
        if(view == null){
            return;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(listener == null){
                    return;
                }
                listener.onClick(dialog,view,position);
            }
        });

    }
}
