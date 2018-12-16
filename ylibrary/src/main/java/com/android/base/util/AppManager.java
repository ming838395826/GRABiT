package com.android.base.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * app管理工具
 * Created by Administrator on 2016/11/2.
 */

public class AppManager {

    /**
     * 调起安装
     */
    public static void Instanll(File file, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    public static void installApkFile(File file,Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.example.yangqiyun.demo.fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
//    /**
//     * 执行app下载安装
//     * */
//    public static void doAppLoad(Activity activity,String url,String isImportant,AppVersionResultDo mAppVersionResultDo){
//        if(url == null || isImportant == null){
//            return;
//        }
//        File localFile = new File(Environment.getExternalStorageDirectory(),
//                "/ydsm/" + url.substring(url.lastIndexOf("/") + 1));
//        if (localFile.exists()){
//            installApkFile(localFile,activity);
//            return;
//        }
//
//        if("1".equals(isImportant)){
//            Bundle mBundle = new Bundle();
//            mBundle.putSerializable("info",mAppVersionResultDo);
//            PanelManager.getInstance().switchPanel(PanelFormManager.ID_COMMON_UPDATE,mBundle,null);
////            if(activity != null && !(activity instanceof ComUpdateActivity)){
////                activity.finish();
////            }
//            return;
//        }else{
//            Intent intent = new Intent(activity, AppUpdateService.class);
//            intent.putExtra("url", url);
//            activity.startService(intent);
//        }
//    }
//    /**
//     * 取消更新
//     * 根据不同的配置执行响应操作
//     * */
//    public static void doCancelUpdate(String isImportant){
//        if(isImportant != null){
//            return;
//        }
//        if(isImportant.equals("0")){
//            //不需要强制更新
//        }else{
//            //强制更新，直接退出
//            YdsmApplication.mAppHandle.doAppExit();
//        }
//    }
}
