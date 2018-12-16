package com.android.spin.shop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.base.MvpFragment;
import com.android.base.mvp.persenrter.MvpPresenter;
import com.android.base.mvp.view.IView;

import java.util.Map;

/**
 * User: huangruimin Date: 2014-12-13 Time: 19:43 Description:
 */
public abstract class AbsBaseFragment<V extends IView,T extends MvpPresenter<V>> extends MvpFragment<V,T> {


	protected abstract void initActions(View paramView);

	protected abstract void initData();

	protected abstract View initViews(LayoutInflater paramLayoutInflater);

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		initData();
	}

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
                             ViewGroup paramViewGroup, Bundle paramBundle) {
		super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
		View localView = initViews(paramLayoutInflater);
		initActions(localView);
		return localView;
	}
}
