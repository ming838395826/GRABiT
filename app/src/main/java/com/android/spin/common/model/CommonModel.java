package com.android.spin.common.model;

import com.android.base.module.http.callback.ChildSubscriber;
import com.android.base.module.http.callback.OnNetRequestListener;
import com.android.base.module.http.callback.ShowApiResponse;
import com.android.base.util.SignHelper;
import com.android.spin.common.entity.GetPhoneResultEntity;
import com.android.spin.common.entity.QiNiuTokenEntity;
import com.android.spin.common.entity.SendSmsResultEntity;
import com.android.spin.common.service.CommonRetrofitService;
import com.android.spin.logreg.entity.RegisterResultEntity;
import com.android.spin.logreg.service.LogregRetrofitService;

import java.util.HashMap;
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

public class CommonModel {

    /**
     * 判断手机是否已存在
     * @param params
     * @param listener
     */
    public void getIsExistPhone(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<GetPhoneResultEntity>> observable = CommonRetrofitService.getInstance().
                createShowApi().getIsExistPhone(CommonRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<GetPhoneResultEntity>>(listener) {
                });
    }

    /**
     * 退出登陆
     * @param listener
     */
    public void doLogout(OnNetRequestListener listener){
        Observable<ShowApiResponse<Object>> observable = CommonRetrofitService.getInstance().
                createShowApi().doLogout(CommonRetrofitService.getCacheControl(),new HashMap<>());

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<Object>>(listener) {
                });
    }

    /**
     * 退出登陆
     * @param listener
     */
    public void getQiNiuToken(OnNetRequestListener listener){
        Observable<ShowApiResponse<QiNiuTokenEntity>> observable = CommonRetrofitService.getInstance().
                createShowApi().getQiNiuToken(CommonRetrofitService.getCacheControl(),new HashMap<>());

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<QiNiuTokenEntity>>(listener) {
                });
    }

    /**
     * 发送短信验证码
     * @param listener
     */
    public void doSendSms(Map<String,Object> params,OnNetRequestListener listener){
        Observable<ShowApiResponse<SendSmsResultEntity>> observable = CommonRetrofitService.getInstance().
                createShowApi().doSendSms(CommonRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<SendSmsResultEntity>>(listener) {
                });
    }
}
