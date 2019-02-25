package com.ming.grabit.util.facebook;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.android.base.util.ToastUtil;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ${zyj} on 2016/3/30.
 */
public class FaceBookLogin {

    private Activity activity;
    private CallbackManager callbackManager;
    private FacebookListener facebookListener;
    private List<String> permissions = Collections.<String>emptyList();
    private LoginManager loginManager;

    private static SimpleDateFormat ymdSdf = new SimpleDateFormat("MM/dd/yyyy", Locale.CHINA);

    public FaceBookLogin(Activity activity) {
        this.activity = activity;

        //初始化facebook登录服务
        callbackManager = CallbackManager.Factory.create();
        getLoginManager().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // login success
                AccessToken accessToken = loginResult.getAccessToken();
                getLoginInfo(accessToken);
            }

            @Override
            public void onCancel() {
                //取消登录
            }

            @Override
            public void onError(FacebookException error) {
                //登录出错
                ToastUtil.shortShow("facebook登陆失败");
            }
        });

        permissions = Arrays
                .asList("email", "user_likes", "user_status", "user_photos", "user_birthday", "public_profile", "user_friends");
    }

    /**
     * 登录
     */
    public void login() {
        getLoginManager().logInWithReadPermissions(
                activity, permissions);
    }

    /**
     * 退出
     */
    public void logout() {
        String logout = activity.getResources().getString(
                com.facebook.R.string.com_facebook_loginview_log_out_action);
        String cancel = activity.getResources().getString(
                com.facebook.R.string.com_facebook_loginview_cancel_action);
        String message;
        Profile profile = Profile.getCurrentProfile();
        if (profile != null && profile.getName() != null) {
            message = String.format(
                    activity.getResources().getString(
                            com.facebook.R.string.com_facebook_loginview_logged_in_as),
                    profile.getName());
        } else {
            message = activity.getResources().getString(
                    com.facebook.R.string.com_facebook_loginview_logged_in_using_facebook);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(true)
                .setPositiveButton(logout, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getLoginManager().logOut();
                    }
                })
                .setNegativeButton(cancel, null);
        builder.create().show();
    }

    /**
     * 获取登录信息
     *
     * @param accessToken
     */
    public void getLoginInfo(final AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object != null) {

                    FacebookUser user = new FacebookUser();
                    String id = object.optString("id");   //比如:1565455221565
                    String name = object.optString("name");  //比如：Zhang San
                    String gender = object.optString("gender");  //性别：比如 male （男）  female （女）
                    String emali = object.optString("email");  //邮箱：比如：56236545@qq.com
                    String birthday = object.optString("birthday");
                    //获取用户头像
                    JSONObject object_pic = object.optJSONObject("picture");
                    JSONObject object_data = object_pic.optJSONObject("data");

                    user.setId(id);
                    user.setToken(accessToken.getToken());
                    user.setEmali(emali);
                    user.setGender(gender);
                    user.setName(name);
                    user.setAvatar(object_data.optString("url"));
                    try {
                        Date date = ymdSdf.parse(birthday);
                        user.setBirthday(date.getTime()/1000);
                    }catch (Exception e){

                    }

                    //获取地域信息
                    String locale = object.optString("locale");   //zh_CN 代表中文简体

                    if(facebookListener != null){
                        facebookListener.facebookLoginSuccess(user);
                    }

                }else{
                    if(facebookListener != null){
                        facebookListener.facebookLoginFail();
                    }
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,gender,birthday,email,picture,locale,updated_time,timezone,age_range,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * 获取loginMananger
     *
     * @return
     */
    private LoginManager getLoginManager() {
        if (loginManager == null) {
            loginManager = LoginManager.getInstance();
        }
        return loginManager;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    /**
     * 设置登录简体器
     *
     * @param facebookListener
     */
    public void setFacebookListener(FacebookListener facebookListener) {
        this.facebookListener = facebookListener;
    }

    public interface FacebookListener {
        void facebookLoginSuccess(FacebookUser user);

        void facebookLoginFail();
    }


}
