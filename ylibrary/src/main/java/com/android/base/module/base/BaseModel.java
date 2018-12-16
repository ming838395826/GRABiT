package com.android.base.module.base;

import com.android.base.callback.entity.TokenResultDo;
import com.android.base.module.http.callback.ChildSubscriber;
import com.android.base.module.http.callback.OnNetRequestListener;
import com.android.base.module.http.callback.ShowApiResponse;
import com.android.base.mvp.model.IModel;
//import com.android.base.util.JfwSignHelper;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 公共
 * Created by YANGQIYUN on 2017/4/6.
 */

public class BaseModel implements IModel {

    /**
     * 获取sessiontoken
     */
    public void getSessionToken(Map<String, Object> params, OnNetRequestListener listener) {
        Observable<ShowApiResponse<TokenResultDo>> observable = BaseRetrofitService.getInstance().
                createShowApi().getSessionToken(BaseRetrofitService.getCacheControl(), params);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ChildSubscriber<ShowApiResponse<TokenResultDo>>(listener) {
                });
    }

}
