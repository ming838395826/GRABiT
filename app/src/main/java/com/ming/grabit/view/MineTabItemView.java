package com.ming.grabit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ming.grabit.R;
import com.taobao.uikit.feature.view.TImageView;
import com.taobao.uikit.feature.view.TTextView;

/**
 * @Title: ydsm
 * @Description:
 * @author: YANGQIYUN
 * @data: 17/3/31
 */

public class MineTabItemView extends LinearLayout {

    TImageView timgItemIcon;
    TTextView ttvItemTitle;
    TImageView timgItemGo;
    TTextView ttvItemValue;
    TTextView ttvItemCount;
    View line;

    public MineTabItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public MineTabItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineTabItemView(Context context) {
        this(context, null, 0);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        LayoutInflater.from(context).inflate(R.layout.layout_mine_tab_item, this);
        timgItemIcon = (TImageView) findViewById(R.id.timg_item_icon);
        ttvItemTitle = (TTextView) findViewById(R.id.ttv_item_title);
        timgItemGo = (TImageView) findViewById(R.id.timg_item_go);
        ttvItemValue = (TTextView) findViewById(R.id.ttv_item_value);
        ttvItemCount = (TTextView) findViewById(R.id.ttv_item_count);
        line = findViewById(R.id.line);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.MineTabItemView);

        Drawable icon = obtainStyledAttributes.getDrawable(R.styleable.MineTabItemView_titleIcon);

        boolean enable = obtainStyledAttributes.getBoolean(R.styleable.MineTabItemView_enable, true);
        boolean showLine = obtainStyledAttributes.getBoolean(R.styleable.MineTabItemView_showLine, true);

        String title = obtainStyledAttributes.getString(R.styleable.MineTabItemView_title);
        String value = obtainStyledAttributes.getString(R.styleable.MineTabItemView_value);
        String count = obtainStyledAttributes.getString(R.styleable.MineTabItemView_count);

        obtainStyledAttributes.recycle();

        if (icon != null) {
            timgItemIcon.setImageDrawable(icon);
        }else{
            timgItemIcon.setVisibility(View.GONE);
        }
        timgItemGo.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);

        if (title != null) {
            ttvItemTitle.setText(title);
        }
        if (value != null) {
            ttvItemValue.setVisibility(View.VISIBLE);
            ttvItemValue.setText(title);
        } else {
            ttvItemValue.setVisibility(View.GONE);
        }

        if (count != null) {
            ttvItemCount.setVisibility(View.VISIBLE);
            ttvItemTitle.setText(title);
        } else {
            ttvItemCount.setVisibility(View.GONE);
        }

        line.setVisibility(showLine ? View.VISIBLE : View.GONE);

    }

    /**
     * 设置文本
     * @param value
     */
    public void setValue(String value){
        if(ttvItemValue != null){
            ttvItemValue.setText(value);
            ttvItemValue.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 设置文本
     * @param resid
     */
    public void setValueLeftDrawable(int resid){
        if(ttvItemValue != null){
            ttvItemValue.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resid),null,null,null);
        }
    }
    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        if(ttvItemTitle != null){
            ttvItemTitle.setText(title);
        }
    }

}
