package com.ming.grabit.shop.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.android.base.base.MvpFragment;
import com.android.base.module.http.callback.ShowApiListResponse;
import com.android.base.mvp.view.IView;
import com.android.base.util.DensityUtil;
import com.ming.grabit.R;
import com.ming.grabit.common.util.Constant;
import com.ming.grabit.shop.adapter.CardGalleryAdapter;
import com.ming.grabit.shop.adapter.CardPagerAdapter;
import com.ming.grabit.shop.adapter.DemoCustomView02Adapter;
import com.ming.grabit.shop.control.ViewPagerScroller;
import com.ming.grabit.shop.entity.ShopHistroyItemEntity;
import com.ming.grabit.shop.presenter.ShopPresenter;
import com.ming.grabit.shop.utils.NetWorkHelper;
import com.ming.grabit.shop.widget.ProgressHUD;
import com.ming.grabit.view.XGallery;
import com.ming.grabit.view.tab.RecyclerTabLayout;
import com.handmark.pulltorefresh.extras.viewpager.PullToRefreshViewPager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * User: shine Date: 2014-12-13 Time: 19:45 Description:
 */
public class CardViewPagerFragment extends MvpFragment<IView, ShopPresenter> implements
        PullToRefreshBase.OnRefreshListener<ViewPager> {
    @Bind(R.id.xgallery)
    XGallery wgallery;
    @Bind(R.id.pager)
    PullToRefreshViewPager mPullToRefreshViewPager;

    private static final int TYPE_SHOP_REQUEST = 0x00;
    @Bind(R.id.recycler_tab_layout)
    RecyclerTabLayout recyclerTabLayout;
    /**
     * 接收PullToRefreshViewPager中的ViewPager控件
     */
    private ViewPager mViewPager;
    /**
     * ViewPager的适配器
     */
    private CardPagerAdapter mCardPagerAdapter;

    private CardGalleryAdapter mCardGalleryAdapter;

    private DemoCustomView02Adapter mDemoCustomView02Adapter;

    private boolean mHasNext = true;

    private boolean mIsRequesting;

    private boolean isAdapterUpdated;

    private int mCurrentViewPagerPage;

    private List<ShopHistroyItemEntity> mCardList = new ArrayList<>();

    private ProgressHUD mProgressHUD;

    private int page = 1;
    private int perPage = 20;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int paramInt) {
        }

        @Override
        public void onPageScrolled(int paramInt1, float paramFloat,
                                   int paramInt2) {
        }

        @Override
        public void onPageSelected(int position) {
            if (wgallery != null) {
                wgallery.setSelection(position, true);
            }
            if (mHasNext && (position > -10 + mCardList.size())
                    && !mIsRequesting
                    && NetWorkHelper.isWifiDataEnable(getActivity())) {
//                fetchData();
            }
        }
    };

    @Override
    public ShopPresenter initPresenter() {
        return new ShopPresenter();
    }

    public static CardViewPagerFragment getInstance() {
        CardViewPagerFragment mFragment = new CardViewPagerFragment();

        return mFragment;
    }

    /**
     * 设置ViewPager的滚动速度，即每个选项卡的切换速度
     *
     * @param viewPager ViewPager控件
     * @param speed     滚动速度，毫秒为单位
     */
    private void setViewPagerScrollSpeed(ViewPager viewPager, int speed) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            ViewPagerScroller viewPagerScroller = new ViewPagerScroller(
                    viewPager.getContext(), new OvershootInterpolator(0.6F));
            field.set(viewPager, viewPagerScroller);
            viewPagerScroller.setDuration(speed);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取历史商品
        getPageList();
        showLoadDialog();
    }

    private void getPageList() {
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.KEY_PAGE, page);
        params.put(Constant.KEY_PER_PAGE, perPage);
        getPresenter().getShopHistory(params, TYPE_SHOP_REQUEST);
    }

    private void updateAppAdapter(List<ShopHistroyItemEntity> cardList) {
        if ((getActivity() == null) || (getActivity().isFinishing())) {
            return;
        }
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            this.mProgressHUD.dismiss();
            this.isAdapterUpdated = true;
        }
        int size = mCardList.size();

        if (mCardPagerAdapter == null) {
            mCurrentViewPagerPage = 0;
            mCardPagerAdapter = new CardPagerAdapter(getActivity()
                    .getSupportFragmentManager(), cardList);
            mViewPager.setAdapter(mCardPagerAdapter);
            mDemoCustomView02Adapter = new DemoCustomView02Adapter(mViewPager);
            mDemoCustomView02Adapter.newList(cardList);
            recyclerTabLayout.setUpWithAdapter(mDemoCustomView02Adapter);
            recyclerTabLayout.setPositionThreshold(0.5f);
        } else {
            if (page == 1) {
                mCardPagerAdapter.newCardList(cardList);
                mDemoCustomView02Adapter.newList(cardList);
            } else {
                mCardPagerAdapter.addCardList(cardList);
                mDemoCustomView02Adapter.addList(cardList);
            }
            mCardPagerAdapter.notifyDataSetChanged();
        }
//        addCardIconsToDock(cardList);

        this.mCardList = mCardPagerAdapter.getCardList();

        if (mViewPager.getCurrentItem() == size - 1)
            mViewPager.setCurrentItem(1 + mViewPager.getCurrentItem(), true);
    }

    /**
     * viewPager刷新或加载更多监听
     *
     * @param pullToRefreshBase
     */
    @Override
    public void onRefresh(PullToRefreshBase<ViewPager> pullToRefreshBase) {
        if (this.mIsRequesting)
            return;
        if (pullToRefreshBase.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {// 最右
            mIsRequesting = true;
            page++;
            getPageList();

        } else if (pullToRefreshBase.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {// 最左
            mIsRequesting = true;
            page = 1;
            getPageList();
        }
    }

    @Override
    public void onFail(String code, int type) {
        if (page != 1) {
            page--;
        }
        if (page == 1) {
            showNetWorkError();
        }
    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
        if (mPullToRefreshViewPager != null) {
            mPullToRefreshViewPager.onRefreshComplete();
        }
        mIsRequesting = false;
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type) {
            case TYPE_SHOP_REQUEST:
                ShowApiListResponse<ShopHistroyItemEntity> histroys = (ShowApiListResponse<ShopHistroyItemEntity>) data;
                if (histroys == null || histroys.getData() == null || histroys.getData().size() == 0) {
                    if (page == 1) {
                        showEmptyView(getString(R.string.text_fund_empty),R.mipmap.icon_empty);
                    }
                    return;
                }
                hideEmptyLayout();
                updateAppAdapter(histroys.getData());
                initGaller(histroys.getData());
                if (mCardGalleryAdapter.getShopHistroyItemEntity(mViewPager.getCurrentItem()) != null
                        && monPageSelectedListener != null) {
                    monPageSelectedListener.onPageSelected(mCardGalleryAdapter.getShopHistroyItemEntity(mViewPager.getCurrentItem()));
                }
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_card_pager;
    }

    @Override
    public void initView() {
        // 初始化控件
        mViewPager = mPullToRefreshViewPager.getRefreshableView();
        // 设置ViewPager的滚动速度
        setViewPagerScrollSpeed(mViewPager, 400);

        ViewGroup.LayoutParams vpParams = mViewPager.getLayoutParams();
        vpParams.height = (int) (DensityUtil.getWidth() * 1.13);
        mViewPager.setLayoutParams(vpParams);

//        mRhythmLayout.setRhythmListener(rhythmItemListener);
        mPullToRefreshViewPager.setOnRefreshListener(this);
        mViewPager.setOnPageChangeListener(onPageChangeListener);

    }

    private void initGaller(List<ShopHistroyItemEntity> list) {
        if (list == null || list.size() == 0) {
            return;
        }

//        wgallery.setPageOffscreenLimit(list.size());
        if (mCardGalleryAdapter == null) {
            if(wgallery == null){
                return;
            }
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) wgallery.getLayoutParams();
            params.setMargins(-(DensityUtil.getWidth() / 2 - DensityUtil.dp2px(5)), 0, 0, 0);
            wgallery.setLayoutParams(params);


            mCardGalleryAdapter = new CardGalleryAdapter();
            mCardGalleryAdapter.addList(list);
            wgallery.setAdapter(mCardGalleryAdapter);
            wgallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) wgallery.getLayoutParams();
                    int left = 0;
                    if (position < 4) {
                        left = DensityUtil.getWidth() / 2 - DensityUtil.dp2px(5);
                    } else {
                        left = 0;
                    }
                    params.setMargins(-left, 0, 0, 0);
                    wgallery.setLayoutParams(params);
                }

                @Override
                public void onPageSelected(int position) {
//                    mViewPager.setCurrentItem(position);
                    if(monPageSelectedListener != null){
                        monPageSelectedListener.onPageSelected(mCardGalleryAdapter.getShopHistroyItemEntity(position));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            if (page == 1) {
                mCardGalleryAdapter.newList(list);
            } else {
                mCardGalleryAdapter.addList(list);
            }
            mCardGalleryAdapter.notifyDataSetChanged();
        }

    }

    private onPageSelectedListener monPageSelectedListener;

    public interface onPageSelectedListener {
        void onPageSelected(ShopHistroyItemEntity entity);
    }

    public void setonPageSelectedListener(onPageSelectedListener monPageSelectedListener) {
        this.monPageSelectedListener = monPageSelectedListener;
    }
}
