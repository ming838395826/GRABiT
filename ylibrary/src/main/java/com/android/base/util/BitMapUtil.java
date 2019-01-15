package com.android.base.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者：yangqiyun
 * 时间：2017/7/16
 * 1120389276@qq.com
 * 描述：
 */

public class BitMapUtil {

    /**
     * 保存方法
     */
    public static void saveBitmap(Bitmap bm, String picName) {
        File PHOTO_DIR = new File(Environment.getExternalStorageDirectory()+"image");//设置保存路径
        File f = new File(PHOTO_DIR, MD5Util.GetMD5Code(picName) + ".png");
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, DensityUtil.dp2px(80), out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }

    }

    public static Bitmap getBitmap(String picName) {
        Bitmap bitmap = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/image/" + MD5Util.GetMD5Code(picName) + ".png");
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "image" + picName + ".png");
            }
        } catch (Exception e) {
        }
        return bitmap;
    }

    /**
     * 把字节数组保存为一个文件
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 生成布局图片
     * @param v
     * @return
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    public static Bitmap getViewToBitmap(View v) {
        int w = v.getMeasuredWidth();
        int h = v.getMeasuredHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        /** 如果不设置canvas画布为白色，则生成透明 */
//        c.drawColor(Color.YELLOW);

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }
}
