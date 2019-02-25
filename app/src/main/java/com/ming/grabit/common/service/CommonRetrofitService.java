package com.ming.grabit.common.service;

import com.android.base.base.application.BaseApplication;
import com.android.base.callback.service.RetrofitService;
import com.android.base.module.http.DecodeConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * <Pre>
 * 网络请求引擎类
 * </Pre>
 *
     * @author YANGQIYUN
 * @version 1.0
 *          <p/>
 *          Create by 2016/1/27 15:14
 */
public class CommonRetrofitService extends RetrofitService {

    private CommonRetrofitService() {
    }

    private volatile static CommonRetrofitService instance = null;

    public static CommonRetrofitService getInstance() {
        if (instance == null) {
            synchronized (CommonRetrofitService.class) {
                if (instance == null) {
                    instance = new CommonRetrofitService();
                }
            }
        }
        return instance;
    }

    private volatile static CommonShowApi mCommonShowApi = null;

    public static CommonShowApi createShowApi() {
        if (mCommonShowApi == null) {
            synchronized (CommonRetrofitService.class) {
                if (mCommonShowApi == null) {
                    mCommonShowApi = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BaseApplication.info.getAPI())
                            .addConverterFactory(DecodeConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build().create(CommonShowApi.class);
                }
            }
        }
        return mCommonShowApi;
    }
}
