package com.ming.grabit.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.taobao.uikit.feature.view.TRecyclerView;

/**
 * Created by ming on 2019/1/15.
 */

public class NoScrollRecyclerView extends TRecyclerView {
    public NoScrollRecyclerView(Context context) {
        super(context);
    }
    public NoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public NoScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int IheightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, IheightSpec);
    }
}
