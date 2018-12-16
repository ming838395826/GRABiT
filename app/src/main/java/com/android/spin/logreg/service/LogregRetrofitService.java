package com.android.spin.logreg.service;

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
public class LogregRetrofitService extends RetrofitService {

    private LogregRetrofitService() {
    }

    private volatile static LogregRetrofitService instance = null;

    public static LogregRetrofitService getInstance() {
        if (instance == null) {
            synchronized (LogregRetrofitService.class) {
                if (instance == null) {
                    instance = new LogregRetrofitService();
                }
            }
        }
        return instance;
    }

    private volatile static LogregShowApi mLogregShowApi = null;

    public static LogregShowApi createShowApi() {
        if (mLogregShowApi == null) {
            synchronized (LogregRetrofitService.class) {
                if (mLogregShowApi == null) {
                    mLogregShowApi = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BaseApplication.info.getAPI())
                            .addConverterFactory(DecodeConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build().create(LogregShowApi.class);
                }
            }
        }
        return mLogregShowApi;
    }
}
