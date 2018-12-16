package com.android.spin.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.base.callback.service.RetrofitService;
import com.android.base.module.http.callback.ChildSubscriber;
import com.android.base.module.http.callback.OnNetRequestListener;
import com.android.base.module.http.callback.ShowApiResponse;
import com.android.spin.common.entity.QiNiuTokenEntity;
import com.android.spin.common.model.CommonModel;
import com.android.spin.db.UserManager;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 图片上传管理
 * Created by YANGQIYUN on 2016/5/31.
 */
public class UploadFileManager {

    private static UploadManager uploadManager;
    //测试域名
    public static String main_domin = "http://owkpbbfyj.bkt.clouddn.com";
    //所有图片路径
    private static String path = "";
    /**
     * 批量上传
     * */
    public static void doUploadFileS(final List<String> paths) {
        position = 0;
        if (null == paths || paths.size() == 0) {
            mOnUploadListener.complete();
            return;
        }
        Map<String,String> parmas = new HashMap<>();
        parmas.put("token", UserManager.getInstance().getQiNiuToken());
        UploadFileManager.getQiNiuToken(parmas,new OnNetRequestListener<QiNiuTokenEntity>() {
            @Override
            public void onStart() {
                Log.d("yqy", "onStart");
            }

            @Override
            public void onFinish() {
                Log.d("yqy", "onStart");
            }

            @Override
            public void onSuccess(QiNiuTokenEntity data) {
                UserManager.getInstance().saveQiNiuToken(data.getUpload_token());
                byte[] imag = ImageUtil.fixPic(paths.get(position));
                if(imag == null){
                    mOnUploadListener.complete();
//                    ToastUtil.showToast(R.string.error_pic_not_exist);
                }else{
                    getUploadManager().put(imag, null, UserManager.getInstance().getQiNiuToken(),new CustomUpCompletionHandler(paths),null);
                }

            }

            @Override
            public void onFailure(String code) {
                mOnUploadListener.onFail(null);
                position = 0;
            }
        });
    }

    /**
     * 获取七牛token
     * */
    public static void getQiNiuToken(Map<String,String> parmas,OnNetRequestListener listener) {
        new CommonModel().getQiNiuToken(listener);
    }

    public static UploadManager getUploadManager(){
        if(uploadManager != null){
            return uploadManager;
        }
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
//            .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
//            .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
//                .zone(Zone) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
        return uploadManager;
    }
    /**
     * 文件上传
     * */
    public static void doUploadFile(final String path,final UpCompletionHandler handler){

        Map<String,String> parmas = new HashMap<>();
        parmas.put("token", UserManager.getInstance().getQiNiuToken());
        UploadFileManager.getQiNiuToken(parmas,new OnNetRequestListener<QiNiuTokenEntity>() {
            @Override
            public void onStart() {
                Log.d("yqy", "onStart");
            }

            @Override
            public void onFinish() {
                Log.d("yqy", "onStart");
            }

            @Override
            public void onSuccess(QiNiuTokenEntity data) {
                UploadFileManager.main_domin = data.getCdn_host();
                UserManager.getInstance().saveQiNiuToken(data.getUpload_token());
                getUploadManager().put(ImageUtil.getImageByByte(path), null, UserManager.getInstance().getQiNiuToken(),handler,null);
            }

            @Override
            public void onFailure(String code) {

            }

        });
    }
    /**
     * 上传接口
     * */
    public static OnUploadListener mOnUploadListener;
    public interface OnUploadListener{
        void complete();
        void onFail(String key);
        void onSuccess(String key, String path);
    }
    public void setOnUploadListener(OnUploadListener mOnUploadListener){
        this.mOnUploadListener = mOnUploadListener;
    }

    /**
     * 自定义上传图片回调
     * */
    static int position = 0;//上传标识
    public static class CustomUpCompletionHandler implements UpCompletionHandler {
        private List<String> paths;
        public CustomUpCompletionHandler(List<String> paths){
            this.paths = paths;
        }
        @Override
        public void complete(String key1, ResponseInfo info, JSONObject res) {

            if(mOnUploadListener == null){
                return;
            }
            if(paths.size()<=position){
                mOnUploadListener.onFail(null);
                return;
            }
            try {
                if(res==null){
                    mOnUploadListener.onFail(paths.get(position));
                    return;
                }
                mOnUploadListener.onSuccess(paths.get(position),"up-z2.qiniu.com" + res.getString("key"));
                position++;
            } catch (Exception e) {
                e.printStackTrace();
                mOnUploadListener.onFail(paths.get(position));
            }

            //paths.remove(0);11.14上传图片，不移除
            if(paths == null ||paths.size() == position){
                mOnUploadListener.complete();
                return;
            }
            byte[] imag = ImageUtil.fixPic(paths.get(position));
            if(imag == null){
                mOnUploadListener.complete();
//                ToastUtil.showToast(R.string.error_pic_not_exist);
            }else{
                getUploadManager().put(imag, null, UserManager.getInstance().getQiNiuToken(),this,null);
            }

        }
    }

    /**
     * 获取拼接后的链接
     * @param key
     * @return
     */
    public static String getUrl(String key){
        return UploadFileManager.main_domin + "/" + key;
    }
}
