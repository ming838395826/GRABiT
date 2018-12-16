package com.android.spin.logreg.presenter;

import com.alibaba.fastjson.JSONObject;
import com.android.base.callback.impl.OnNetRequestImplListener;
import com.android.base.mvp.persenrter.MvpPresenter;
import com.android.base.mvp.view.IView;
import com.android.spin.common.entity.GetPhoneResultEntity;
import com.android.spin.common.entity.SendSmsResultEntity;
import com.android.spin.logreg.entity.LoginResultEntity;
import com.android.spin.common.model.CommonModel;
import com.android.spin.db.UserManager;
import com.android.spin.logreg.entity.RegisterResultEntity;
import com.android.spin.logreg.model.RegisterModel;
import com.android.spin.mine.entity.UserEntity;
import com.android.spin.mine.model.MineModel;

import java.util.Map;

/**
 * 作者：yangqiyun
 * 时间：2017/8/2
 * 1120389276@qq.com
 * 描述：
 */

public class RegisterPresenter extends MvpPresenter<IView> {

    private RegisterModel mRegisterModel = new RegisterModel();

    private CommonModel mCommonModel = null;

    private CommonModel getCommonModel(){
        if(mCommonModel == null){
            mCommonModel = new CommonModel();
        }
        return mCommonModel;
    }

    private MineModel mMineModel = null;

    private MineModel getMineModel(){
        if(mMineModel == null){
            mMineModel = new MineModel();
        }
        return mMineModel;
    }

    /**
     * 注册第一步（公用）
     */
    public void doRegister(Map<String,Object> params, final int type) {
        mRegisterModel.doRegister(params, new OnNetRequestImplListener<RegisterResultEntity>(getView(), type) {
            @Override
            public void onSuccess(RegisterResultEntity data) {
                super.onSuccess(data);
                //基础数据持久化
                UserManager.getInstance().saveToken(data.getToken());
                UserManager.getInstance().setUserInfo(JSONObject.toJSONString(data.getUser()));
            }
        });
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfo(Map<String,Object> params, final int type) {
        getMineModel().updateUserInfo(params, new OnNetRequestImplListener<UserEntity>(getView(), type) {
            @Override
            public void onSuccess(UserEntity data) {
                UserManager.getInstance().setUserInfo(JSONObject.toJSONString(data));
                super.onSuccess(data);
                //基础数据持久化
            }
        });
    }

    /**
     * 登陆
     */
    public void doLogin(Map<String,Object> params, final int type) {
        mRegisterModel.doLogin(params, new OnNetRequestImplListener<RegisterResultEntity>(getView(), type) {
            @Override
            public void onSuccess(RegisterResultEntity data) {
                super.onSuccess(data);
                //基础数据持久化
                UserManager.getInstance().saveToken(data.getToken());
                UserManager.getInstance().setUserInfo(JSONObject.toJSONString(data.getUser()));
            }
        });
    }

    /**
     * 登陆
     */
    public void doFbLogin(Map<String,Object> params, final int type) {
        mRegisterModel.doFbLogin(params, new OnNetRequestImplListener<RegisterResultEntity>(getView(), type) {
            @Override
            public void onSuccess(RegisterResultEntity data) {
                super.onSuccess(data);
                //基础数据持久化
                UserManager.getInstance().saveToken(data.getToken());
                UserManager.getInstance().setUserInfo(JSONObject.toJSONString(data.getUser()));
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
     * 发送注册验证码
     * @param params
     * @param type
     */
    public void sendRegisterSms(Map<String,Object> params, final int type){
        params.put("type","register");
        getCommonModel().doSendSms(params,new OnNetRequestImplListener<SendSmsResultEntity>(getView(),type){
            @Override
            public void onSuccess(SendSmsResultEntity data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 发送忘记密码验证码
     * @param params
     * @param type
     */
    public void sendRForgetPwdSms(Map<String,Object> params, final int type){
        params.put("type","resetPassword");
        getCommonModel().doSendSms(params,new OnNetRequestImplListener<SendSmsResultEntity>(getView(),type){
            @Override
            public void onSuccess(SendSmsResultEntity data) {
                super.onSuccess(data);
            }
        });
    }

    /**
     * 重设密码
     * @param params
     * @param type
     */
    public void doResetPwd(Map<String,Object> params, final int type){
        mRegisterModel.doResetPwd(params,new OnNetRequestImplListener<SendSmsResultEntity>(getView(),type){
            @Override
            public void onSuccess(SendSmsResultEntity data) {
                super.onSuccess(data);
            }
        });
    }
}
