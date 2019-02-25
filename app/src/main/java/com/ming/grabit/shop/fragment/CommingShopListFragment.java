package com.ming.grabit.shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by ming on 2019/1/14.
 */

public class CommingShopListFragment extends ShopListNewFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setType(1);
    }
}
