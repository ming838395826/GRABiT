package com.ming.grabit.common.selector;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ming.grabit.R;
import com.ming.grabit.common.selector.view.ClipViewLayout;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 裁剪图片的Activity
 *
 * @author xiechengfa2000@163.com
 * @ClassName: CropImageActivity
 * @Description:
 * @date 2015-5-8 下午3:39:22
 */
public class ClipImageActivity extends AppCompatActivity implements OnClickListener {
    public static final String RESULT_PATH = "crop_image";
    private static final String KEY = "path";
    public static final String TMP_PATH = "clip_temp.jpg";

    private ClipViewLayout clipViewLayout;
    private ImageView back;
    private TextView btnCancel;
    private TextView btnOk;

    public static void startActivity(Activity activity, String path, int code) {
        Intent intent = new Intent(activity, ClipImageActivity.class);
        intent.putExtra(KEY, path);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_crop_image_layout);

        clipViewLayout = (ClipViewLayout) findViewById(R.id.clipViewLayout);
        String path = getIntent().getStringExtra(KEY);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        Uri destinationUri = Uri.fromFile(new File(path));
        clipViewLayout.setImageSrc(destinationUri);
        // 有的系统返回的图片是旋转了，有的没有旋转，所以处理
//		int degreee = readBitmapDegree(path);
//		Bitmap bitmap = createBitmap(path);
//		if (bitmap != null) {
//			if (degreee == 0) {
//				mClipImageLayout.setImageBitmap(bitmap);
//			} else {
//				mClipImageLayout.setImageBitmap(rotateBitmap(degreee, bitmap));
//			}
//		} else {
//			finish();
//		}
        findViewById(R.id.bt_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.bt_ok) {
            generateUriAndReturn();
        }
        finish();
    }

    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        zoomedCropBitmap = clipViewLayout.clip();

        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            String cropImagePath = getRealFilePathFromUri(getApplicationContext(), mSaveUri);
            Intent intent = new Intent();
            intent.putExtra(RESULT_PATH, cropImagePath);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * Try to return the absolute file path from the given Uri  兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
