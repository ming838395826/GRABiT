package com.android.spin.mine.presenter;

import com.alibaba.fastjson.JSONObject;
import com.android.base.callback.impl.OnNetRequestImplListener;
import com.android.base.module.http.callback.ShowApiResponse;
import com.android.base.mvp.persenrter.MvpPresenter;
import com.android.base.mvp.view.IView;
import com.android.spin.common.entity.GetPhoneResultEntity;
import com.android.spin.common.entity.SendSmsResultEntity;
import com.android.spin.common.model.CommonModel;
import com.android.spin.db.UserManager;
import com.android.spin.event.UpdateUserInfoEvent;
import com.android.spin.logreg.entity.RegisterResultEntity;
import com.android.spin.logreg.model.RegisterModel;
import com.android.spin.mine.entity.ContactUsEntity;
import com.android.spin.mine.entity.UserEntity;
import com.android.spin.mine.model.MineModel;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * 作者：yangqiyun
 * 时间：2017/9/8
 * 1120389276@qq.com
 * 描述：
 */

public class MinePresenter extends MvpPresenter<IView> {

    private MineModel mMineModel = new MineModel();

    private CommonModel mCommonModel = null;

    private CommonModel getCommonModel() {
        if (mCommonModel == null) {
            mCommonModel = new CommonModel();
        }

        return mCommonModel;
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfo(Map<String, Object> params, final int type) {
        mMineModel.updateUserInfo(params, new OnNetRequestImplListener<UserEntity>(getView(), type) {
            @Override
            public void onSuccess(UserEntity data) {
                UserManager.getInstance().setUserInfo(JSONObject.toJSONString(data));
                super.onSuccess(data);
                //基础数据持久化
            }
        });
    }

    /**
     * 绑定facebook
     */
    public void doFacebookBind(Map<String, Object> params, final String openid,final int type) {
        mMineModel.doFacebookBind(params, new OnNetRequestImplListener<Object>(getView(), type) {
            @Override
            public void onSuccess(Object data) {
                //基础数据持久化
                UserEntity mUserEntity = UserManager.getInstance().getUser();
                mUserEntity.setFb_openid(openid);
                UserManager.getInstance().setUserInfo(JSONObject.toJSONString(mUserEntity));
                EventBus.getDefault().post(new UpdateUserInfoEvent());
            }
        });
    }

    /**
     * 解绑facebook
     */
    public void doFacebookUnBind(Map<String, Object> params, final int type) {
        mMineModel.doFacebookUnBind(params, new OnNetRequestImplListener<Object>(getView(), type) {
            @Override
            public void onSuccess(Object data) {
                //基础数据持久化
                UserEntity mUserEntity = UserManager.getInstance().getUser();
                mUserEntity.setFb_openid(null);
                UserManager.getInstance().setUserInfo(JSONObject.toJSONString(mUserEntity));
                EventBus.getDefault().post(new UpdateUserInfoEvent());
            }
        });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(Map<String, Object> params, final int type) {
        mMineModel.getUserInfo(params, new OnNetRequestImplListener<UserEntity>(getView(), type) {
            @Override
            public void onSuccess(UserEntity data) {
                //基础数据持久化
                Logger.d(data);
                UserManager.getInstance().setUserInfo(JSONObject.toJSONString(data));
                super.onSuccess(data);
            }
        });
    }

    /**
     * 退出登陆
     *
     * @param type
     */
    public void doLogout(final int type) {
        getCommonModel().doLogout(new OnNetRequestImplListener(getView(), type));
    }

    /**
     * 获取重设密码短信
     * @param params
     * @param type
     */
    public void sendForgetPwdSms(Map<String,Object> params, final int type){
        params.put("type","resetPassword");
        getCommonModel().doSendSms(params,new OnNetRequestImplListener<SendSmsResultEntity>(getView(),type){
            @Override
            public void onSuccess(SendSmsResultEntity data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 重设手机号码
     * @param params
     * @param type
     */
    public void sendChangeSms(Map<String,Object> params, final int type){
        params.put("type","changePhone");
        getCommonModel().doSendSms(params,new OnNetRequestImplListener<SendSmsResultEntity>(getView(),type){
            @Override
            public void onSuccess(SendSmsResultEntity data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 更换手机号码
     */
    public void doChangePhone(Map<String, Object> params, final String phone,final int type) {
        mMineModel.doChangePhone(params, new OnNetRequestImplListener<Object>(getView(), type) {
            @Override
            public void onSuccess(Object data) {
                //基础数据持久化
                UserEntity user = UserManager.getInstance().getUser();
                user.setPhone(phone);
                UserManager.getInstance().setUserInfo(JSONObject.toJSONString(user));
                super.onSuccess(data);
            }
        });
    }

    /**
     * 验证手机号码
     */
    public void doVerifyPhone(Map<String, Object> params, final int type) {
        mMineModel.doVerifyPhone(params, new OnNetRequestImplListener<UserEntity>(getView(), type) {
            @Override
            public void onSuccess(UserEntity data) {
                //基础数据持久化
                super.onSuccess(data);
            }
        });
    }

    /**
     * 获取手机号码是否已存在
     * @param params
     * @param type
     */
    public void getIsExistPhone(Map<String,Object> params, final int type){
        getCommonModel().getIsExistPhone(params,new OnNetRequestImplListener<GetPhoneResultEntity>(getView(),type){
            @Override
            public void onSuccess(GetPhoneResultEntity data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 获取联系信息
     */
    public void getContactUsInfo(final int type) {
        mMineModel.getContactUsInfo(new OnNetRequestImplListener<ContactUsEntity>(getView(), type) {
            @Override
            public void onSuccess(ContactUsEntity data) {
                //基础数据持久化
                super.onSuccess(data);
            }
        });
    }

    /**
     * 获取联系信息
     */
    public void feedback(Map<String,Object> params, final int type) {
        mMineModel.feekback(params,new OnNetRequestImplListener<Object>(getView(), type) {
            @Override
            public void onSuccess(Object data) {
                //基础数据持久化
                super.onSuccess(data);
            }
        });
    }
}
