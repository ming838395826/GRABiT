package com.android.spin.shop.service;

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
public class ShopRetrofitService extends RetrofitService {

    private ShopRetrofitService() {
    }

    private volatile static ShopRetrofitService instance = null;

    public static ShopRetrofitService getInstance() {
        if (instance == null) {
            synchronized (ShopRetrofitService.class) {
                if (instance == null) {
                    instance = new ShopRetrofitService();
                }
            }
        }
        return instance;
    }

    private volatile static ShopShowApi mShopShowApi = null;

    public static ShopShowApi createShowApi() {
        if (mShopShowApi == null) {
            synchronized (ShopRetrofitService.class) {
                if (mShopShowApi == null) {
                    mShopShowApi = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BaseApplication.info.getAPI())
                            .addConverterFactory(DecodeConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build().create(ShopShowApi.class);
                }
            }
        }
        return mShopShowApi;
    }
}
