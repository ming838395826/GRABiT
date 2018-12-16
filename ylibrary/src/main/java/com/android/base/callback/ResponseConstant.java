package com.android.base.callback;

/**
 * 响应码常量
 * Created by Administrator on 2016/7/23.
 */
public class ResponseConstant {

    //请求成功
    public static final int STATE_SUCCESS = 1;
    //请求失败
    public static final int STATE_FAIL = 0;
    //信息不完整
    public static final String PARAM_NOT_COMPLETE = "99001";
    //token 无效
    public static final String TOKEN_INVALID = "99002";

    //无效的数据类型
    public static final String DATA_TYPE_INVALID = "99003";

    //无效的 client_key
    public static final String CLIENT_KEY_INVALID = "99004";
    //无效的签名
    public static final String SIGN_INVALID = "99005";
    //数据异常
    public static final String DATA_EXCEPTION = "99006";
    //操作主体不存在
    public static final String EXIST_EXCEPTION = "99007";
    //重复操作
    public static final String REPEAT_EXCEPTION = "99008";
    //前置操作未存在
    public static final String EXIST_BEFORE_EXCEPTION = "99009";
    //99010 非有效操作(该操作不存在或没对应权限)
    public static final String ERROR_NO_PERMISSIONS = "99010";
    //账号或密码错误
    public static final String LOGIN_FAILED = "10001";
    //账号未被使用
    public static final String HAVE_ACCESS_TO = "10002";
    //密码长度不对
    public static final String ERROR_STR_LENGTH = "10003";
    //账号已被使用
    public static final String CANNOT_USE = "10004";
    //手机已绑定
    public static final String PHONE_HAVE_BINDED = "10005";
    //手机格式错误
    public static final String ERROR_PHONE_STYLE = "10006";
    //验证码不正确
    public static final String ERROR_VERIFY_CODE = "10007";
    //手机绑定错误
    public static final String ERROR_BIND_PHONE = "10008";
    //原密码错误
    public static final String ERROR_ORIGINAL_PASSWORD = "10009";
    //超出短信限制条数
    public static final String BEYOND_THE_NUMBER_OF_SMS = "100010";
    //超出邮件限制条数
    public static final String BEYOND_THE_NUMBER_OF_EMAIL_MESSAGES = "100011";
    //已经提交过申请
    public static final String ERROR_HAVE_SUBMIT_APPLY = "20000";
    //已成为好友
    public static final String ERROR_IS_FRIEND = "20001";
    //不允许添加自己为好友
    public static final String ERROR_CANT_ADD_SELF = "20002";
    //已加入黑名单
    public static final String ERROR_HAVA_ADD_TO_BLACK = "20003";

    //自己不能收藏自己的足迹
    public static final String CAN_NOT_COLLECT_THEIR_OWN = "30000";
    //回复的评论不存在或已失效
    public static final String THE_COMMENT_IS_NOT_PRESENT_OR_IS_INVALID = "30001";

    //已有当前团
    public static final String ERROR_HAVA_A_TOURING_GROUP = "40000";
    //已加入群
    public static final String ERROR_HAVA_JOIN_TOURING_GROUP = "40001";

}
