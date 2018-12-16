package com.android.base.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.R;
import com.android.base.util.DensityUtil;
import com.android.base.util.DeviceManagerUtil;
import com.android.base.util.debug.LogUtil;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

import butterknife.Bind;

/**
 * 作者：yangqiyun
 * 时间：2017/6/16
 * 1120389276@qq.com
 * 描述：自定义统一title
 */

public class BaseHeaderTitleBarView extends LinearLayout {

    TextView tvTopBarLeft;
    TextView tvTopBarTitle;
    TextView tvTopBarRight;
    TImageView timgTitle;
    private String TAG = "BaseHeaderTitleBarView ===>> ";

    public BaseHeaderTitleBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public BaseHeaderTitleBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseHeaderTitleBarView(Context context) {
        this(context, null, 0);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        setOrientation(LinearLayout.VERTICAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View view = new View(context);
            view.setBackgroundColor(Color.parseColor("#191917"));
            LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,DensityUtil.dp2px(20));
            addView(view,params);
            LayoutInflater.from(context).inflate(R.layout.layout_base_top_title_bar, this);
        }

        tvTopBarLeft = (TextView) findViewById(R.id.tv_top_bar_left);
        tvTopBarTitle = (TextView) findViewById(R.id.tv_top_bar_title);
        tvTopBarRight = (TextView) findViewById(R.id.tv_top_bar_right);
        timgTitle = (TImageView) findViewById(R.id.timg_title);

//        initHeaderTop(context);
    }

    /**
     * 适配状态栏高度
     *
     * @param context
     */
    private void initHeaderTop(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int stateHeight = DeviceManagerUtil.getStatusBarHeight(context);
            if (stateHeight == 0) {
                //防止部分手机无法获取到高度
                stateHeight = DensityUtil.dp2px(20);
            }
            setPadding(0, stateHeight, 0, 0);
        }
    }

    public void setHeaderTitleImage(int resId){
        if(timgTitle != null){
            timgTitle.setVisibility(VISIBLE);
            timgTitle.setImageResource(resId);
        }
    }

    /**
     * 设置title颜色
     * @param color
     */
    public void setTitleColor(int color){
        if(tvTopBarTitle != null){
            tvTopBarTitle.setTextColor(color);
        }
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setHeaderTitle(String title) {
        if(tvTopBarTitle != null){
            tvTopBarTitle.setText(title);
        }
    }

    /**
     * 设置左边文本内容
     *
     * @param title
     */
    public void setHeaderLeftContent(String title) {
        if(tvTopBarLeft != null){
            tvTopBarLeft.setText(title);
        }
    }

    /**
         * 设置右边文本内容
     *
     * @param title
     */
    public void setHeaderRightContent(String title) {
        if(tvTopBarRight != null){
            tvTopBarRight.setText(title);
        }
    }

    /**
     * 设置右边文本颜色
     *
     * @param resid
     */
    public void setHeaderRightContentColor(int resid) {
        if(tvTopBarRight != null){
            tvTopBarRight.setTextColor(resid);
        }
    }

    /**
     * 设置右边图标
     *
     * @param resid
     */
    public void setHeaderRightImage(int resid) {
        if(tvTopBarRight != null){
            tvTopBarRight.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resid),null,null,null);
        }
    }

    /**
     * 设置左边图标
     *
     * @param resid
     */
    public void setHeaderLeftImage(int resid) {
        if(tvTopBarLeft != null){
            tvTopBarLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resid),null,null,null);
        }
    }

    /**
     * 设置点击事件
     *
     * @param listener
     * @param resId
     */
    public void setOnClickListener(OnClickListener listener, int... resId) {
        for (int id : resId) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    public TextView getHeaderRightView(){
        return tvTopBarRight;
    }

}
