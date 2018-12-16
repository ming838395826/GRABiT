package com.android.spin.common.selector;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.base.base.delegate.AppDelegate;
import com.android.spin.R;
import com.taobao.uikit.feature.view.TTextView;

import java.io.File;
import java.util.ArrayList;

/**
 * 作者：yangqiyun
 * 时间：2017/9/18
 * 1120389276@qq.com
 * 描述：
 */

public class MultiImageSelectorDelegate extends AppDelegate implements MultiImageSelectorFragment.Callback{

    // Single choice
    public static final int MODE_SINGLE = 0;
    // Multi choice
    public static final int MODE_MULTI = 1;

    private final int CROP_RESULT_CODE = 3;

    /** Max image size，int，{@link #DEFAULT_IMAGE_SIZE} by default */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /** Select mode，{@link #MODE_MULTI} by default */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /** Whether show camera，true by default */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /** Result data set，ArrayList&lt;String&gt;*/
    public static final String EXTRA_RESULT = "select_result";
    /** Original data set */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
    // Default image size
    private static final int DEFAULT_IMAGE_SIZE = 9;

    private ArrayList<String> resultList = new ArrayList<>();
    private TextView mSubmitButton;
    private int mDefaultCount = DEFAULT_IMAGE_SIZE;

    private boolean isSingle = true;
    private boolean isNeedCrop = true;
    private Bundle savedInstanceState;

    private MultiImageSelectorFragment mMultiImageSelectorFragment;

    @Override
    public int getRootLayoutId() {
        return R.layout.mis_activity_default;
    }

    @Override
    public void initWidget() {

    }

    private TextView getHeaderRightView(){
        return getBaseHeaderTitleBarView().getHeaderRightView();
    }

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.create(inflater, container, savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    public void initHeaderView(){
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState){
        final Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, DEFAULT_IMAGE_SIZE);
        final int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        final boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        if(mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }
////        多张上传
        mSubmitButton = getHeaderRightView();
        if(mode == MODE_MULTI){
            updateDoneText(resultList);
            mSubmitButton.setVisibility(View.VISIBLE);
            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(resultList != null && resultList.size() >0){
                        // Notify success
                        Intent data = new Intent();
                        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                        setResult(Activity.RESULT_OK, data);
                    }else{
                        setResult(Activity.RESULT_CANCELED);
                    }
                    finish();
                }
            });
        }else{
            mSubmitButton.setVisibility(View.GONE);
        }

        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
            bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
            bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
            bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);

            mMultiImageSelectorFragment = (MultiImageSelectorFragment) Fragment.instantiate(this.getActivity(), MultiImageSelectorFragment.class.getName(), bundle);
            ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                    .add(R.id.image_grid, mMultiImageSelectorFragment)
                    .commit();
        }

        this.isNeedCrop = getIntent().getBooleanExtra("isNeedCrop",true);
    }

    /**
     * 裁剪
     * */
    public void setIsNeedCrop(boolean isNeedCrop){
        this.isNeedCrop = getIntent().getBooleanExtra("isNeedCrop",true);
    }
    public void setIsSingle(boolean isSingle){
        this.isSingle = isSingle;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Update done button by select image data
     * @param resultList selected image data
     */
    private void updateDoneText(ArrayList<String> resultList){
        int size = 0;
        if(resultList == null || resultList.size()<=0){
            mSubmitButton.setText(R.string.mis_action_done);
            mSubmitButton.setEnabled(false);
        }else{
            size = resultList.size();
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setText(getActivity().getString(R.string.mis_action_button_string,
                getString(R.string.mis_action_done), size, mDefaultCount));
    }
    /**
     * 单选
     * */
    @Override
    public void onSingleImageSelected(String path) {

        if(isNeedCrop){
            // 裁剪图片的Activity
            startCropImageActivity(path);
            return;
        }
        resultList.add(path);
        Intent data = new Intent();
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    private void startCropImageActivity(String path) {
        ClipImageActivity.startActivity(this.getActivity(), path, CROP_RESULT_CODE);
    }

    @Override
    public void onImageSelected(String path) {
        if(!resultList.contains(path)) {
            resultList.add(path);
        }
        updateDoneText(resultList);
    }

    @Override
    public void onImageUnselected(String path) {
        if(resultList.contains(path)){
            resultList.remove(path);
        }
        updateDoneText(resultList);
    }
    /**
     * 拍照
     * */
    @Override
    public void onCameraShot(File imageFile) {
        if(imageFile != null) {
            // notify system the image has change
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));

            resultList.add(imageFile.getAbsolutePath());
            if(isNeedCrop){
                // 裁剪图片的Activity
                startCropImageActivity(imageFile.getAbsolutePath());
                overridePendingTransition(R.anim.app_slide_right_in, R.anim.app_slide_left_out);
                return;
            }
            Intent data = new Intent();
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onClickCamera() {
        mMultiImageSelectorFragment.showCameraAction();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CROP_RESULT_CODE:
                String path = data.getStringExtra(ClipImageActivity.RESULT_PATH);
//                Bitmap photo = BitmapFactory.decodeFile(path);
                resultList.add(path);
                Intent intent = new Intent();
                intent.putStringArrayListExtra(EXTRA_RESULT, resultList);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
