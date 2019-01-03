package com.android.spin.mine.model;

import com.android.base.module.http.callback.ChildSubscriber;
import com.android.base.module.http.callback.OnNetRequestListener;
import com.android.base.module.http.callback.ShowApiResponse;
import com.android.base.util.SignHelper;
import com.android.spin.common.entity.LoginResultEntity;
import com.android.spin.db.UserManager;
import com.android.spin.logreg.entity.RegisterResultEntity;
import com.android.spin.logreg.service.LogregRetrofitService;
import com.android.spin.mine.entity.UserEntity;
import com.android.spin.mine.service.MineRetrofitService;

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

public class MineModel {

    /**
     * 更新个人资料
     * @param params
     * @param listener
     */
    public void updateUserInfo(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<UserEntity>> observable = MineRetrofitService.getInstance().
                createShowApi().putUserInfo(MineRetrofitService.getCacheControl(), UserManager.getInstance().getUser().getId(),SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<UserEntity>>(listener) {
                });
    }

    /**
     * 获取个人资料
     * @param params
     * @param listener
     */
    public void getUserInfo(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<UserEntity>> observable = MineRetrofitService.getInstance().
                createShowApi().getUserInfo(MineRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<UserEntity>>(listener) {
                });
    }

    /**
     * 更换手机号码
     * @param params
     * @param listener
     */
    public void doChangePhone(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<Object>> observable = MineRetrofitService.getInstance().
                createShowApi().doChangePhone(MineRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<Object>>(listener) {
                });
    }

    /**
     * 绑定facebook
     * @param params
     * @param listener
     */
    public void doFacebookBind(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<Object>> observable = MineRetrofitService.getInstance().
                createShowApi().doFacebookBind(MineRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<Object>>(listener) {
                });
    }

    /**
     * 解绑facebook
     * @param params
     * @param listener
     */
    public void doFacebookUnBind(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<Object>> observable = MineRetrofitService.getInstance().
                createShowApi().doFacebookUnBind(MineRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<Object>>(listener) {
                });
    }

    /**
     * 验证手机号码
     * @param params
     * @param listener
     */
    public void doVerifyPhone(Map<String,Object> params, OnNetRequestListener listener){
        Observable<ShowApiResponse<Object>> observable = MineRetrofitService.getInstance().
                createShowApi().doVerifyPhone(MineRetrofitService.getCacheControl(), SignHelper.addSign(params));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<Object>>(listener) {
                });
    }

    /**
     * 获取联系信息
     * @param listener
     */
    public void getContactUsInfo(OnNetRequestListener listener){
        Observable<ShowApiResponse<Object>> observable = MineRetrofitService.getInstance().
                createShowApi().getContactUsInfo(MineRetrofitService.getCacheControl());

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<Object>>(listener) {
                });
    }
}
