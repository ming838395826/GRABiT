package com.android.base.view.layout;
/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.android.base.util.DensityUtil;

public class OverScrollView2 extends ScrollView {

	/**
	 * Always allow a user to over-scroll this view, provided it is a
	 * view that can scroll.
	 *
	 * @see #getOverScrollMode()
	 * @see #setOverScrollMode(int)
	 */
	public static final int OVER_SCROLL_ALWAYS = 0;

	/**
	 * Allow a user to over-scroll this view only if the content is large
	 * enough to meaningfully scroll, provided it is a view that can scroll.
	 *
	 * @see #getOverScrollMode()
	 * @see #setOverScrollMode(int)
	 */
	public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;

	/**
	 * Never allow a user to over-scroll this view.
	 *
	 * @see #getOverScrollMode()
	 * @see #setOverScrollMode(int)
	 */
	public static final int OVER_SCROLL_NEVER = 2;

	public OverScrollView2(Context context) {
		this(context,null,0);
	}

	public OverScrollView2(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	public OverScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context,attrs,defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr){
		setScrollBarDefaultDelayBeforeFade(SCROLL_AXIS_NONE);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public OverScrollView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected boolean overScrollBy(
			int deltaX, int deltaY,
			int scrollX, int scrollY,
			int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY,
			boolean isTouchEvent) {

		return super.overScrollBy(
				deltaX, deltaY, scrollX, scrollY,
				scrollRangeX, scrollRangeY, maxOverScrollX, DensityUtil.getHeight()/3,
				isTouchEvent);
	}
}