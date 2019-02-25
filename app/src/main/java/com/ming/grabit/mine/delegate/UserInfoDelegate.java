package com.ming.grabit.mine.delegate;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.android.base.base.BaseUIActivity;
import com.android.base.base.delegate.MvpDelegate;
import com.android.base.mvp.view.IView;
import com.android.base.permission.iface.CheckPermListener;
import com.android.base.util.DateUtil;
import com.android.base.util.ToastUtil;
import com.android.base.util.image.GlideUtil;
import com.ming.grabit.R;
import com.ming.grabit.common.selector.MultiImageSelector;
import com.ming.grabit.common.selector.view.CircleImageView;
import com.ming.grabit.db.UserManager;
import com.ming.grabit.event.UpdateUserInfoEvent;
import com.ming.grabit.mine.entity.UserEntity;
import com.ming.grabit.mine.presenter.MinePresenter;
import com.ming.grabit.util.DialogUtil;
import com.ming.grabit.util.UploadFileManager;
import com.ming.grabit.view.widget.CustomDatePicker;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.taobao.uikit.feature.view.TEditText;
import com.taobao.uikit.feature.view.TRecyclerView;
import com.taobao.uikit.feature.view.TTextView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：yangqiyun
 * 时间：2017/9/12
 * 1120389276@qq.com
 * 描述：
 */

public class UserInfoDelegate extends MvpDelegate<IView, MinePresenter> implements View.OnClickListener{

    @Bind(R.id.timg_mine_avatar)
    CircleImageView timgMineAvatar;
    @Bind(R.id.ll_avatar)
    LinearLayout llAvatar;
    @Bind(R.id.tet_name)
    TEditText tetName;
    @Bind(R.id.ttv_gender)
    TTextView ttvGender;
    @Bind(R.id.ttv_date_birth)
    TTextView ttvDateBirth;
    private boolean isEdit = false;

    private static final int TYPE_REQUEST_USER_INFO_CODE = 0x01;
    private static final int TYPE_UPDATE_USER_INFO_CODE = 0x02;
    private final int REQUEST_SELECTE_IMAGE = 0;
    private final int PERMISSION_REQUEST_ALBUM_CODE = 2;

    private final String DATE_PATTERN = "yyyy-MM-dd";

    //原有图片
    private ArrayList<String> mSelectPath = new ArrayList<>();

    @Override
    public void onFail(String code, int type) {

    }

    @Override
    public void onComplete(int type) {
        dismissLoadDialog();
    }

    @Override
    public void onSuccess(Object data, int type) {

        switch (type){
            case TYPE_REQUEST_USER_INFO_CODE:
                //获取用户信息
                initData();
                break;
            case TYPE_UPDATE_USER_INFO_CODE:
                //更新用户信息成功
                ToastUtil.shortShow(getString(R.string.text_update_info_successful));
                initBtnStatus();
                initData();
                EventBus.getDefault().post(new UpdateUserInfoEvent());
                break;
        }

    }

    @NonNull
    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initWidget() {

        initContentView();

        initData();

        getUserInfo();
    }

    private void initData(){
        try{
            UserEntity user = UserManager.getInstance().getUser();
            if(user == null){
                return;
            }
            GlideUtil.defualtLoad(this.getActivity(),UserManager.getInstance().getUser().getAvatar(),
                    R.mipmap.icon_mine_header,timgMineAvatar);
            tetName.setText(user.getName());
            if(user.isMan()){
                ttvGender.setText(getString(R.string.text_user_info_man));
                ttvGender.setTag(1);
            }else if(user.isWomen()){
                ttvGender.setText(getString(R.string.text_user_info_women));
                ttvGender.setTag(2);
            }else{
                ttvGender.setTag(0);
            }
            ttvDateBirth.setText(DateUtil.ymdSdfFormatByh(user.getBirthday() * 1000));
        }catch (Exception e){

        }
    }

    /**
     * 编辑及保存状态切换
     */
    public void onClickEditorSave() {
        if(isEdit){
            //保存
            updateUserInfo();
            return;
        }
        initBtnStatus();

    }

    private void initBtnStatus(){
        isEdit = !isEdit;
        initHeaderRightContent();

        initContentView();
    }

    private void initContentView(){
//        llAvatar.setEnabled(isEdit);
        tetName.setEnabled(isEdit);
        ttvGender.setEnabled(isEdit);
        ttvDateBirth.setEnabled(isEdit);
        if(isEdit){
            ttvGender.setCompoundDrawablesWithIntrinsicBounds(
                    null,null,getActivity().getResources().getDrawable(R.mipmap.icon_gray_go),null);
            ttvDateBirth.setCompoundDrawablesWithIntrinsicBounds(
                    null,null,getActivity().getResources().getDrawable(R.mipmap.icon_gray_go),null);
        }else{
            ttvGender.setCompoundDrawablesWithIntrinsicBounds(
                    null,null,null,null);
            ttvDateBirth.setCompoundDrawablesWithIntrinsicBounds(
                    null,null,null,null);
        }
    }

    private void initHeaderRightContent(){
        BaseUIActivity act = getActivity();
        if(act == null){
            return;
        }
        if (!isEdit) {
            act.setHeaderTitleRightContent(act.getString(R.string.text_user_info_title_edit));
        }else{
            act.setHeaderTitleRightContent(act.getString(R.string.text_user_info_title_save));
        }
    }

    @OnClick({R.id.ll_avatar,R.id.ttv_gender,R.id.ttv_date_birth})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_avatar:
                //头像
                if(isEdit){
                    doSelectPic();
                }
                break;
            case R.id.ttv_gender:
                //性别
                showSelectGenderDialog();
                break;
            case R.id.ttv_date_birth:
                //出生日期
                initDatePicker().show("2016-03-11");
                break;
        }
    }

    /**
     * 选择性别
     */
    private Dialog mDialog;
    private void showSelectGenderDialog() {
        if(mDialog == null){
            String [] genders = {getString(R.string.text_user_info_man),getString(R.string.text_user_info_women)};
            mDialog = DialogUtil.getListDialog(getActivity(), getString(R.string.text_user_info_gender_hint), null,
                    genders, new TRecyclerView.OnItemClickListener() {
                        @Override
                        public void onItemClick(TRecyclerView parent, View view, int position, long id) {
                            if(mDialog != null){
                                mDialog.dismiss();
                            }
                            if(position == 0){
                                ttvGender.setText(getString(R.string.text_user_info_man));
                                ttvGender.setTag(1);
                            }else{
                                ttvGender.setText(getString(R.string.text_user_info_women));
                                ttvGender.setTag(2);
                            }
                        }
                    });
        }
        mDialog.show();
    }

    private CustomDatePicker mCustomDatePicker;
    private CustomDatePicker initDatePicker() {
        if (mCustomDatePicker != null){
            return mCustomDatePicker;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());

        mCustomDatePicker = new CustomDatePicker(this.getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                ttvDateBirth.setText(time.split(" ")[0]);
            }
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        mCustomDatePicker.showSpecificTime(false); // 不显示时和分
        mCustomDatePicker.setIsLoop(false); // 不允许循环滚动

        return mCustomDatePicker;
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo(){
//        Map<String,Object> params = new HashMap<>();
        getPrensenter().getUserInfo(null,TYPE_REQUEST_USER_INFO_CODE);
    }

    /**
     * 更新用户头像
     * @param path
     */
    public void updateAvatar(String path){
        if(path == null){
            return;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("avatar",path);
        showLoadDialog();
        getPrensenter().updateUserInfo(params,TYPE_UPDATE_USER_INFO_CODE);
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo(){

        Map<String,Object> params = new HashMap<>();
        long date = DateUtil.formatToLong(ttvDateBirth.getText().toString().trim(),DATE_PATTERN);

        if("0".equals(ttvGender.getTag().toString())){
            ToastUtil.shortShow(getString(R.string.text_user_info_gender_hint));
            return;
        }
        if(date == 0){
            ToastUtil.shortShow(getString(R.string.text_user_info_date_birth_hint));
            return;
        }
        params.put("birthday",date/1000);
        params.put("name",tetName.getText().toString().trim());
        params.put("gender",ttvGender.getTag());

        showLoadDialog();
        getPrensenter().updateUserInfo(params,TYPE_UPDATE_USER_INFO_CODE);
    }

    /**
     * 进入图片选择器
     */
    private void doSelectPic() {
        checkPermission(new CheckPermListener() {
            @Override
            public void superPermission(int requestCode) {
                MultiImageSelector selector = MultiImageSelector.create(getActivity());
                selector.showCamera(true);
                selector.isNeedCrop(true);
                selector.origin(mSelectPath);
                selector.single();
                selector.start(getActivity(), REQUEST_SELECTE_IMAGE);
            }
        }, PERMISSION_REQUEST_ALBUM_CODE, R.string.permission_to_album, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case Activity.RESULT_OK:
                switch (requestCode){
                    case REQUEST_SELECTE_IMAGE:
                        mSelectPath = data.getStringArrayListExtra("select_result");
                        showLoadDialog();
                        UploadFileManager.doUploadFile(mSelectPath.get(0), new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                try {
                                    dismissLoadDialog();
                                    updateAvatar(UploadFileManager.getUrl(response.get("key").toString()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
