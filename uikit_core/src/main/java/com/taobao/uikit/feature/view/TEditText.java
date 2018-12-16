package com.taobao.uikit.feature.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.taobao.uikit.R;


/**
 * 
 * TTextView: UIKit's custom TextView
 * @author jiajing
 *
 */
public class TEditText extends EditText{

	public TEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public TEditText(Context context) {
		this(context, null);
	}

}
