package com.android.base.module.http.callback;

import android.util.Log;

import com.android.base.event.TokenFailEvent;
import com.android.base.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.observers.DisposableObserver;


/**
 * Created by YANGQIYUN on 2016/5/4.
 */
public class ChildSubscriber<T extends ShowApiResponse> extends DisposableObserver<T> {

    protected OnNetRequestListener listener;

    protected ChildSubscriber(OnNetRequestListener listener){
        this.listener = listener;
    }

    @Override
    public void onError(Throwable e) {
        Log.d("ChildSubscriber"," onError  " + e.toString());
        if(listener != null){
            listener.onFailure("-1");
            listener.onFinish();
        }
    }

    @Override
    public void onComplete() {
        if(listener != null){
            //仅成功后会回调
            listener.onFinish();
        }
    }

    @Override
    public void onNext(T o) {
        if("0".equals(o.getCode())){
            if(listener != null){
                onComplete();
                listener.onSuccess(o.data);
            }
        }else if("1".equals(o.getCode())){
            ToastUtil.shortShow(o.getMsg());
        }else if("1002".equals(o.getCode())){
//            EventBus.getDefault().post(new TokenFailEvent());
        } else {
            //错误代码处理
            //ERROR.VERIFYCODE.ERROR  验证码错误
            listener.onFailure(o.getCode());
        }
    }
}
