package com.android.base.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.base.R;
import com.android.base.base.application.BaseApplication;
import com.android.base.base.delegate.AppDelegate;
import com.android.base.util.AppLanguageManager;
import com.android.base.util.DensityUtil;
import com.android.base.util.DeviceManagerUtil;
import com.android.base.util.debug.LogUtil;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * 作者：yangqiyun
 * 时间：2017/6/11
 * 1120389276@qq.com
 * 描述：基础UIactivity
 */

public abstract class BaseUIActivity<T extends AppDelegate> extends BaseAdaptateActivity implements SwipeBackActivityBase {

    //右滑删除Activity帮助类
    private SwipeBackActivityHelper mHelper;

    private FrameLayout layoutContainer;
    private BaseHeaderTitleBarView mHeaderTitleBar;
    //在title下面
    protected int TITLEBARSTYLE_BELW = 0;
    //悬浮式
    protected int TITLEBARSTYLE_FLOAT = 1;
    //没有title
    protected int TITLEBARSTYLE_NOT = 2;

    protected T viewDelegate;

    protected InputMethodManager inputMethodManager;

    protected RelativeLayout mainBg;

    public BaseUIActivity() {
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
    }

    public T getViewDelegate(){
        return viewDelegate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!BaseApplication.isInitLanguage && Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            AppLanguageManager.changeAppLanguage(this);
            BaseApplication.isInitLanguage = false;
        }
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);
//        setWindowStatusBarColor(this,R.color.app_font_color_main_title);
        super.setContentView(R.layout.activity_base_ui);

        mainBg = (RelativeLayout) findViewById(R.id.main_bg);

        ARouter.getInstance().inject(this);

        init();
        initHelper();
        initBaseView();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(AppLanguageManager.attachBaseContext(newBase));
        BaseApplication.isInitLanguage = false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("create IDelegate error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create IDelegate error");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewDelegate.onPause();
    }

    @Override
    protected void onDestroy() {
        viewDelegate.onDestroy();
        viewDelegate = null;
        if (getRootView() != null) {
            try {
                unbindDrawables(getRootView());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    public void setMainBg(int color){
        if(mainBg != null){
            mainBg.setBackgroundColor(color);
        }
    }

    public View getRootView() {
        return this.findViewById(android.R.id.content);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);//启动手势
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);//将当前类转换为半透明效果（精髓所在）
        getSwipeBackLayout().scrollToFinishActivity();//关闭当前activity
    }

    /**
     * sp单位字体不随系统改变
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 清除背景
     *
     * @param view
     */
    public void unbindDrawables(View view) {
        if (view != null) {
            view.setBackgroundDrawable(null);
        }
        if (view instanceof ViewGroup) {
            ((ViewGroup) view).removeAllViews();
        }
    }

    /**
     * 初始化右滑finish
     */
    private void initHelper() {
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 初始化基础布局样式
     */
    private void init() {
        layoutContainer = (FrameLayout) findViewById(R.id.layout_container);
        mHeaderTitleBar = (BaseHeaderTitleBarView) findViewById(R.id.htb_top_bar);

        if (TITLEBARSTYLE_BELW == getLayoutStyle()) {
            initBack(enableClickBack());
            //布局在title下方
            //调整布局位置
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layoutContainer.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, mHeaderTitleBar.getId());
            layoutContainer.setLayoutParams(params);
        } else if (TITLEBARSTYLE_FLOAT == getLayoutStyle()) {
            //title悬浮在布局上方 默认
            initBack(enableClickBack());
        } else if (TITLEBARSTYLE_NOT == getLayoutStyle()) {
            //没有title
            mHeaderTitleBar.setVisibility(View.GONE);

        }
    }

    protected void setOnStatubarBelw() {
        if (layoutContainer != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int stateHeight = DeviceManagerUtil.getStatusBarHeight(this);
                if (stateHeight == 0) {
                    //防止部分手机无法获取到高度
                    stateHeight = DensityUtil.dp2px(20);
                }
                layoutContainer.setPadding(0, stateHeight, 0, 0);
            }
        }
    }

    /**
     * 实例化返回事件
     *
     * @param enableClickBack
     */
    private void initBack(boolean enableClickBack) {
        View.OnClickListener listener = null;
        if (enableClickBack) {
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        BaseApplication.mAppHandle.activeActivity.finish();
                    } catch (Exception e) {
                        LogUtil.d("nullException");
                    }
                }
            };
            mHeaderTitleBar.setHeaderLeftImage(R.mipmap.icon_com_back);
        }
        if(mHeaderTitleBar == null){
            return;
        }
        mHeaderTitleBar.setOnClickListener(listener, R.id.tv_top_bar_left);
    }

    /**
     * 初始化试图
     */
    private void initBaseView() {
        layoutContainer.addView(viewDelegate.getRootView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        initView();

        initHeaderView();

        initViewByhandler();
    }

    //收起键盘
    protected void hideSoftKeyboard(View view) {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //弹出键盘
    protected void showSoftKeyboard(View view) {
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.showSoftInput(view,
                        InputMethodManager.SHOW_FORCED);
        }
    }

    public void setHeaderTitle(String title) {

        if (mHeaderTitleBar == null) {
            return;
        }
        mHeaderTitleBar.setHeaderTitle(title);
    }

    public void setHeaderTitleAndColor(String title,int color) {

        if (mHeaderTitleBar == null) {
            return;
        }
        mHeaderTitleBar.setHeaderTitle(title);
        mHeaderTitleBar.setTitleColor(color);
    }

    /**
     * 获取样式类型
     *
     * @return TITLEBARSTYLE_BELW 默认
     */
    public abstract int getLayoutStyle();


    /**
     * 初始化试图层
     *
     * @return
     */
    public abstract void initView();

    /**
     * 初始化头部
     *
     * @return
     */
    public abstract void initHeaderView();

    public BaseHeaderTitleBarView getBaseHeaderTitleBarVie(){
        return mHeaderTitleBar;
    }

    protected void setHeaderLeftOnClickListener(View.OnClickListener listener){
        if(mHeaderTitleBar != null){
            mHeaderTitleBar.setOnClickListener(listener,R.id.tv_top_bar_left);
        }
    }

    protected void setHeaderRightOnClickListener(View.OnClickListener listener){
        if(mHeaderTitleBar != null){
            mHeaderTitleBar.setOnClickListener(listener,R.id.tv_top_bar_right);
        }
    }

    /**
     * 设置头部背景
     * @param resId
     */
    protected void setHeaderTitleViewBackground(int resId){
        if(mHeaderTitleBar != null){
            mHeaderTitleBar.setBackground(getResources().getDrawable(resId));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int stateHeight = DeviceManagerUtil.getStatusBarHeight(this);
                if (stateHeight == 0) {
                    //防止部分手机无法获取到高度
                    stateHeight = DensityUtil.dp2px(20);
                }
                mHeaderTitleBar.setPadding(0, stateHeight, 0, 0);
            }
        }

    }

    /**
     * 设置头部背景颜色
     * @param resId
     */
    protected void setHeaderTitleViewBackgroundColor(int resId){
        if(mHeaderTitleBar != null){
            mHeaderTitleBar.setBackgroundColor(getResources().getColor(resId));
        }

    }

    /**
     * 设置左边图标
     * @param resId
     */
    protected void setHeaderTitleLeftDrawable(int resId){
        if(mHeaderTitleBar != null){
            mHeaderTitleBar.setHeaderLeftImage(resId);
        }
    }

    /**
     * 设置右边文本
     * @param content
     */
    public void setHeaderTitleRightContent(String content){
        if(mHeaderTitleBar != null) {
            mHeaderTitleBar.setHeaderRightContent(content);
        }
    }
    /**
     * 设置右边图标
     * @param resId
     */
    protected void setHeaderTitleRightColor(int resId){
        if(mHeaderTitleBar != null){
            mHeaderTitleBar.setHeaderRightContentColor(getResources().getColor(resId));
        }
    }


    /**
     * 设置右边图标
     * @param resId
     */
    protected void setHeaderTitleRightDrawable(int resId){
        if(mHeaderTitleBar != null){
            mHeaderTitleBar.setHeaderRightImage(resId);
        }
    }

    /**
     * 是否需要左侧返回事件
     *
     * @return
     */
    public boolean enableClickBack() {
        return true;
    }

    ;

    protected abstract Class<T> getDelegateClass();

    /**
     * 通过handler防止阻塞主线程耗时操作方法
     *
     * @return
     */
    public void initViewByhandler() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(viewDelegate != null){
            viewDelegate.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(viewDelegate != null){
            return viewDelegate.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        viewDelegate.onWindowFocusChanged(hasFocus);
    }
}
