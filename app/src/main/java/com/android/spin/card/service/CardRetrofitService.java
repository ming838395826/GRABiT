package com.android.spin.card.service;

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
public class CardRetrofitService extends RetrofitService {

    private CardRetrofitService() {
    }

    private volatile static CardRetrofitService instance = null;

    public static CardRetrofitService getInstance() {
        if (instance == null) {
            synchronized (CardRetrofitService.class) {
                if (instance == null) {
                    instance = new CardRetrofitService();
                }
            }
        }
        return instance;
    }

    private volatile static CardShowApi mCardShowApi = null;

    public static CardShowApi createShowApi() {
        if (mCardShowApi == null) {
            synchronized (CardRetrofitService.class) {
                if (mCardShowApi == null) {
                    mCardShowApi = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BaseApplication.info.getAPI())
                            .addConverterFactory(DecodeConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build().create(CardShowApi.class);
                }
            }
        }
        return mCardShowApi;
    }
}
