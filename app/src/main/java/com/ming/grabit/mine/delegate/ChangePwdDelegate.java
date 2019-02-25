package com.ming.grabit.mine.delegate;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.android.base.base.delegate.AppDelegate;
import com.ming.grabit.R;
import com.ming.grabit.mine.fragment.ChangePhoneFirstFragment;
import com.ming.grabit.mine.fragment.ChangePhoneSecondFragment;
import com.ming.grabit.mine.fragment.ChangePhoneThirdFragment;

/**
 * 作者：yangqiyun
 * 时间：2017/9/8
 * 1120389276@qq.com
 * 描述：
 */

public class ChangePwdDelegate extends AppDelegate {

    private ChangePhoneFirstFragment mChangePhoneFirstFragment;
    private ChangePhoneSecondFragment mChangePhoneSecondFragment;
    private ChangePhoneThirdFragment mChangePhoneThirdFragment;


    private static final int TYPE_SEND_SMS_CODE = 0x01;

    private int position = 1;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initWidget() {

    }

    /**
     * 第一步
     */
    private void initStepFirst() {
        if (mChangePhoneFirstFragment == null) {
            mChangePhoneFirstFragment = ChangePhoneFirstFragment.newInstance();
        }
        addFragment("1", R.id.layout_container, mChangePhoneFirstFragment);
    }

    /**
     * 第二步
     */
    private void initStepSecond() {
        if (mChangePhoneSecondFragment == null) {
            mChangePhoneSecondFragment = ChangePhoneSecondFragment.newInstance();
        }
        addFragment("2", R.id.layout_container, mChangePhoneSecondFragment);
    }

    /**
     * 第三步
     */
    private void initStepThird() {
        if (mChangePhoneThirdFragment == null) {
            mChangePhoneThirdFragment = ChangePhoneThirdFragment.newInstance();
        }
        addFragment("4", R.id.layout_container, mChangePhoneThirdFragment);
    }

    private void hideFragment() {
        FragmentManager manager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (mChangePhoneFirstFragment != null && mChangePhoneFirstFragment.isAdded()) {
            transaction.hide(mChangePhoneFirstFragment);
        }
        if (mChangePhoneSecondFragment != null && mChangePhoneSecondFragment.isAdded()) {
            transaction.hide(mChangePhoneSecondFragment);
        }
        if (mChangePhoneThirdFragment != null && mChangePhoneThirdFragment.isAdded()) {
            transaction.hide(mChangePhoneThirdFragment);
        }

        transaction.commitAllowingStateLoss();
    }

//    /**
//     * 下一步
//     */
//    public void doNext() {
//        if (position != 3) {
//            hideFragment();
//        }
//        switch (position) {
//            case 1:
//                initStepSecond();
//                position = 1;
//                break;
//            case 2:
//                initStepThird();
//                position = 3;
//                break;
//            case 3:
//                initStepFourth();
//                position = 4;
//                break;
//            case 4:
//                Intent intent = new Intent(this.getActivity(), HomeActivity.class);
//                getActivity().startActivity(intent);
//                position = 1;
//                break;
//        }
//    }
//
//    /**
//     * 上一步
//     */
//    public void doBack() {
//        if (position == 1) {
//            getActivity().finish();
//        } else {
//            position--;
//            hideFragment();
//            switch (position) {
//                case 1:
//                    initStepFirst();
//                    mFacebookRegisterStepThirdFragment = null;
//                    break;
//                case 2:
//                    initStepSecond();
//                    mFacebookRegisterStepFourthFragment = null;
//                    break;
//                case 3:
//                    if (!TextUtils.isEmpty(UserManager.getInstance().getToken())) {
//                        HomeActivity.star(this.getActivity());
//                    }
//                    finish();
//                    break;
//            }
//            getBaseHeaderTitleBarView().setHeaderRightContent(position + "/4");
//        }
//    }



//    /**
//     * 获取短信验证码
//     */
//    private void getChangePwdSms() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("phone", this.getActivity().getIntent().getStringExtra("phone"));
//        setEnable(tbtnGetCodeAgain, false);
//        getPrensenter().sendChangeSms(params, TYPE_SEND_SMS_CODE);
//        showLoadDialog();
//    }
}
