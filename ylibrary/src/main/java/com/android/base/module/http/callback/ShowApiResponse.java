package com.android.base.module.http.callback;

/**
 * <Pre>
 *     易源API通用响应数据
 * </Pre>
 *
 * @author YANGQIYUN
 * @version 1.0
 *          <p/>
 *          Create by 2016/3/1 14:29
 */
public class ShowApiResponse<T> {

    /**
     * code : 000000成功,其他失败
     * server_time : 1469241169318
     * query_time : 0.184
     * message : 操作成功
     * size : 1
     * data : {"id":7,"display_name":"YK_10000000007","token":"98c46053353f570848a192aa16e4e426","avatar":"http://i.zingchat.cn/"}
     */

    private String code;
    private long ser_time;
    private double query_time;
    private String msg;
    private String status_code;
    public T data;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getSer_time() {
        return ser_time;
    }

    public void setServer_time(long server_time) {
        this.ser_time = server_time;
    }

    public double getQuery_time() {
        return query_time;
    }

    public void setQuery_time(double query_time) {
        this.query_time = query_time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
