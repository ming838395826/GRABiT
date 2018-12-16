package com.android.spin.mine.service;

import com.android.base.base.application.BaseApplication;
import com.android.base.callback.service.RetrofitService;
import com.android.base.module.http.DecodeConverterFactory;
import com.android.spin.logreg.service.LogregShowApi;

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
public class MineRetrofitService extends RetrofitService {

    private MineRetrofitService() {
    }

    private volatile static MineRetrofitService instance = null;

    public static MineRetrofitService getInstance() {
        if (instance == null) {
            synchronized (MineRetrofitService.class) {
                if (instance == null) {
                    instance = new MineRetrofitService();
                }
            }
        }
        return instance;
    }

    private volatile static MineShowApi mMineShowApi = null;

    public static MineShowApi createShowApi() {
        if (mMineShowApi == null) {
            synchronized (MineRetrofitService.class) {
                if (mMineShowApi == null) {
                    mMineShowApi = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            //http://120.24.241.243:8081/api/v1/verify
                            .baseUrl(BaseApplication.info.getAPI())
                            .addConverterFactory(DecodeConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build().create(MineShowApi.class);
                }
            }
        }
        return mMineShowApi;
    }
}
