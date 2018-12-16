package com.android.base.module.base;

import com.android.base.base.application.BaseApplication;
import com.android.base.callback.service.RetrofitService;
import com.android.base.module.http.DecodeConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * <Pre>
 *    公共
 * 网络请求引擎类
 * </Pre>
 *
     * @author YANGQIYUN
 * @version 1.0
 *          <p/>
 *          Create by 2016/1/27 15:14
 */
public class BaseRetrofitService extends RetrofitService{

    private BaseRetrofitService() {
    }

    private volatile static BaseRetrofitService instance = null;

    public static BaseRetrofitService getInstance() {
        if (instance == null) {
            synchronized (BaseRetrofitService.class) {
                if (instance == null) {
                    instance = new BaseRetrofitService();
                }
            }
        }
        return instance;
    }

    private volatile static BaseShowApi mComShowApi = null;

    public static BaseShowApi createShowApi() {
        if (mComShowApi == null) {
            synchronized (BaseRetrofitService.class) {
                if (mComShowApi == null) {
                    mComShowApi = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BaseApplication.info.getAPI())
                            .addConverterFactory(DecodeConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build().create(BaseShowApi.class);
                }
            }
        }
        return mComShowApi;
    }
}
