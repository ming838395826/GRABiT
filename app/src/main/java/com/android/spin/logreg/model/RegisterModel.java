package com.android.spin.logreg.model;

import com.android.base.module.http.callback.ChildSubscriber;
import com.android.base.module.http.callback.OnNetRequestListener;
import com.android.base.module.http.callback.ShowApiResponse;
import com.android.base.util.SignHelper;
import com.android.spin.common.entity.LoginResultEntity;
import com.android.spin.logreg.entity.RegisterResultEntity;
import com.android.spin.logreg.service.LogregRetrofitService;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：yangqiyun
 * 时间：2017/8/2
 * 1120389276@qq.com
 * 描述：
 */

public class RegisterModel {

    /**
     * 注册
     * @param params
     * @param listener
     */
    public void doRegister(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<RegisterResultEntity>> observable = LogregRetrofitService.getInstance().
                createShowApi().doRegister(LogregRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<RegisterResultEntity>>(listener) {
                });
    }

    /**
     * 登陆
     * @param params
     * @param listener
     */
    public void doLogin(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<RegisterResultEntity>> observable = LogregRetrofitService.getInstance().
                createShowApi().doLogin(LogregRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<RegisterResultEntity>>(listener) {
                });
    }

    /**
     * fb登陆
     * @param params
     * @param listener
     */
    public void doFbLogin(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<RegisterResultEntity>> observable = LogregRetrofitService.getInstance().
                createShowApi().doFbLogin(LogregRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<RegisterResultEntity>>(listener) {
                });
    }

    /**
     * 重设密码
     * @param params
     * @param listener
     */
    public void doResetPwd(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<Object>> observable = LogregRetrofitService.getInstance().
                createShowApi().doResetPwd(LogregRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<Object>>(listener) {
                });
    }
}
