package com.android.spin.logreg.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.mvp.view.IView;
import com.android.base.util.DateUtil;
import com.android.base.util.ToastUtil;
import com.android.spin.R;
import com.android.spin.logreg.RegisterActivity;
import com.android.spin.logreg.presenter.RegisterPresenter;
import com.android.spin.util.DialogUtil;
import com.android.spin.view.widget.CustomDatePicker;
import com.taobao.uikit.feature.view.TRecyclerView;
import com.taobao.uikit.feature.view.TTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/7/20
 * 1120389276@qq.com
 * 描述：注册第五步
 */
public class RegisterStepFifthFragment extends BaseRegisterFragment implements View.OnClickListener{

    @Bind(R.id.ttv_sex)
    TTextView ttvSex;
    @Bind(R.id.ttv_date)
    TTextView ttvDate;
    @Bind(R.id.tbtn_next)
    TTextView tbtnNext;

    private final String DATE_PATTERN = "yyyy-MM-dd";
    private final int TYPE_UPDATE_USER_INFO_CODE = 0x00;

    public RegisterStepFifthFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterStepFifthFragment newInstance() {
        RegisterStepFifthFragment fragment = new RegisterStepFifthFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_step_fifth;
    }

    @Override
    public void initView() {

        ttvSex.setTag(1);

    }
    @OnClick({R.id.ttv_sex,R.id.ttv_date,R.id.tbtn_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ttv_sex:
                //选择性别
                showSelectGenderDialog();
                break;
            case R.id.ttv_date:
                //选择出生日期
                initDatePicker().show("2017-07-11");
                break;
            case R.id.tbtn_next:
                //下一步
                checkParams();
                break;
        }
    }

    /**
     * 下一步
     */
    private void checkParams() {
        if (ttvSex.getText().toString().trim().length() == 0) {
            ToastUtil.shortShow(getString(R.string.text_sex_empty_error));
            return;
        }
        if (ttvDate.getText().toString().trim().length() == 0) {
            ToastUtil.shortShow(getString(R.string.text_birthday_empty_error));
            return;
        }

        Map<String,Object> params = new HashMap<>();
        long date = DateUtil.formatToLong(ttvDate.getText().toString().trim(),DATE_PATTERN);

        if("0".equals(ttvSex.getTag().toString())){
            ToastUtil.shortShow(getString(R.string.text_user_info_gender_hint));
            return;
        }
        if(date == 0){
            ToastUtil.shortShow(getString(R.string.text_user_info_date_birth_hint));
            return;
        }
        params.put("birthday",date/1000);
        params.put("gender",ttvSex.getTag());

        showLoadDialog();
        getPresenter().updateUserInfo(params,TYPE_UPDATE_USER_INFO_CODE);
//        ((RegisterActivity) getActivity()).getViewDelegate()
//                .getQuestEntity().setPhone(etPhone.getText().toString().trim());
    }

    @Override
    public void onFail(String code, int type) {

    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
    }

    @Override
    public void onSuccess(Object data, int type) {
        switch (type){
            case TYPE_UPDATE_USER_INFO_CODE:
                //提交更新成功
                if (mListener != null) {
                    mListener.onFragmentInteraction();
                }
                break;
        }

    }

    private CustomDatePicker mCustomDatePicker;
    private CustomDatePicker initDatePicker() {
        if (mCustomDatePicker != null){
            return mCustomDatePicker;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());

        mCustomDatePicker = new CustomDatePicker(this.getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                ttvDate.setText(time.split(" ")[0]);
            }
        }, "1970-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        mCustomDatePicker.showSpecificTime(false); // 不显示时和分
        mCustomDatePicker.setIsLoop(false); // 不允许循环滚动

        return mCustomDatePicker;
    }

    /**
     * 选择性别
     */
    private Dialog mDialog;
    private void showSelectGenderDialog() {
        if(mDialog == null){
            String [] genders = {getString(R.string.text_user_info_man),getString(R.string.text_user_info_women)};
            mDialog = DialogUtil.getListDialog(getActivity(), getString(R.string.text_user_info_gender_hint), null,
                    genders, new TRecyclerView.OnItemClickListener() {
                        @Override
                        public void onItemClick(TRecyclerView parent, View view, int position, long id) {
                            if(mDialog != null){
                                mDialog.dismiss();
                            }
                            if(position == 0){
                                ttvSex.setText(getString(R.string.text_user_info_man));
                                ttvSex.setTag(1);
                            }else{
                                ttvSex.setText(getString(R.string.text_user_info_women));
                                ttvSex.setTag(2);
                            }
                        }
                    });
        }
        mDialog.show();
    }
}
