package com.ming.grabit.shop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ming.grabit.shop.entity.ShopHistroyItemEntity;
import com.ming.grabit.shop.fragment.CardFragment;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends FragmentStatePagerAdapter {
    private List<ShopHistroyItemEntity> mPostList;
    private List<Fragment> mFragments = new ArrayList();

    public CardPagerAdapter(FragmentManager paramFragmentManager,
                            List<ShopHistroyItemEntity> paramList) {
        super(paramFragmentManager);
        for (int i = 0; i < paramList.size(); i++) {
            this.mFragments.add(CardFragment.getInstance(paramList.get(i)));
        }
        this.mPostList = paramList;
    }

    public void newCardList(List<ShopHistroyItemEntity> cardList){
        if(mPostList != null){
            mPostList.clear();
        }
        mFragments.clear();

        addCardList(cardList);
    }

    public void addCardList(List<ShopHistroyItemEntity> cardList) {
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i < cardList.size(); i++) {
            this.mFragments.add(CardFragment.getInstance(cardList.get(i)));
        }
        if (this.mFragments == null)
            this.mFragments = new ArrayList();
        this.mFragments.addAll(localArrayList);
        this.mPostList.addAll(cardList);
    }

    public List<ShopHistroyItemEntity> getCardList() {
        return this.mPostList;
    }

    @Override
    public int getCount() {
        return this.mFragments.size();
    }

    public List<Fragment> getFragments() {
        return this.mFragments;
    }

    @Override
    public Fragment getItem(int paramInt) {
        return this.mFragments.get(paramInt);
    }

    public void setCardList(List<ShopHistroyItemEntity> cardList) {
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i < cardList.size(); i++) {
            this.mFragments.add(CardFragment.getInstance(cardList.get(i)));
        }
        this.mFragments = localArrayList;
        this.mPostList = cardList;
    }

    public void setFragments(List<Fragment> paramList) {
        this.mFragments = paramList;
    }
}