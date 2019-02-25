package com.ming.grabit.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ImageUtil {

    /**
     * 极低质量图片
     */
    private static final int IMAGE_QUALITY_EXTRA_LOW = 1;
    /**
     * 极低质量后缀
     */
    private static final String EXTRA_LQ_EXT = "q50.jpg";

    /**
     * 低质量图片
     */
    private static final int IMAGE_QUALITY_LOW = 2;
    /**
     * 低质量后缀
     */
    private static final String LQ_EXT = "q75.jpg";

    /**
     * 高质量图片
     */
    private static final int IMAGE_QUALITY_HIGH = 3;
    /**
     * 高质量后缀
     */
    private static final String HQ_EXT = "q90.jpg";

    /**
     * cdn图片url host pattern
     */
    private static final Pattern CDN_HOST_PATTERN = Pattern.compile(
            "^((http|https|Http|Https):\\/\\/)?" + "([" + "a-zA-Z0-9" + "\\-]{0,64}\\.)*"
                    + "(taobaocdn\\.com|alicdn\\.com|wimg\\.taobao\\.com)");

    /**
     * cdn图片url tail pattern
     */
    private static final Pattern CDN_TAIL_PATTERN =
            Pattern.compile("_" + "(([0-9]{1,10}x[0-9]{1,10}(?:xz|xc)?)|sum|m|b)?" + "((Q|q)[0-9]{1,2})?" + "\\.jpg$");

    /**
     * cdn图片url webp pattern
     */
    private static final Pattern CDN_TAIL_WEBP_PATTERN = Pattern.compile("_\\.webp$");

    private static final String[] EXCLUDE_CDN_KEYWORDS = {"avatar", ".gif"};

    /**
     * cdn支持的宽高相等的尺寸
     */
    private static final int[] CDN_SQUARE_SIZE =
            {20, 24, 30, 40, 48, 60, 64, 70, 72, 80, 90, 100, 110, 120, 128, 130, 160, 170, 180, 210, 220, 230, 240, 250, 270, 300, 310,
                    320, 350, 360, 400, 460, 480, 540, 560, 580, 600, 640, 670, 720, 760, 960};
    /**
     * cdn支持长度自适应图片，对应的宽度尺寸
     */
    private static final int[] CDN_FIX_WIDTH_SIZE = {110, 150, 170, 220, 240, 290, 450, 580, 620, 790};

    private static final String WEBP_SWITCH_KEY = "webp";

    private static final String WEBP_EXT = "_.webp";

    /**
     * 是否使用新的getao cdn域名的ab测试开关key
     */
    private static final String NEW_CDN_HOST_SWITCH_KEY = "use_new_cdn_host";
    private static final String NEW_CDN_HOST = "http://getao.alicdn.com";

    /**
     * webp动态开关
     */
    private static boolean webpDynamicSwitch;

    private static HashMap<String, String> sSizeCache = new HashMap<String, String>();

//  /**
//   * 重写cdn图片url，拼接cdn后缀
//   * 1. 判断是否是cdn图片
//   * 1. 改写host为getao.alicdn.com
//   * 2. 如果url最后已经有cdn后缀参数，需要去除
//   *
//   * @param originalUrl       原始图片url
//   * @param displayWidth      image view的显示宽度
//   * @param displayHeight     image view的显示高度
//   * @param requestOriginSize 是否使用原始图片大小，不进行cdn尺寸缩放
//   * @return 改写优化过的图片url
//   */
//  public static String rewriteCDNUrl(Context context,String originalUrl, int displayWidth, int displayHeight, boolean requestOriginSize) {
//    if (originalUrl == null) {
//      return null;
//    }
//
//    originalUrl = originalUrl.trim();
//
//    Matcher matcher = CDN_HOST_PATTERN.matcher(originalUrl);
//
//    // 不是cdn图片，返回原始url
//    if (!matcher.find()) {
//      return originalUrl;
//    }
//
//    // 包含特定关键词的，返回原始url
//    for (String exludeKeyword : EXCLUDE_CDN_KEYWORDS) {
//      if (originalUrl.contains(exludeKeyword)) {
//        return originalUrl;
//      }
//    }
//
////    // 替换host
////    if (ABTestConfig.getInstance().getBoolean(NEW_CDN_HOST_SWITCH_KEY, false)) {
////      originalUrl = matcher.replaceFirst(NEW_CDN_HOST);
////    }
//
//    // 去掉已有webp后缀
//    Matcher webpMatcher = CDN_TAIL_WEBP_PATTERN.matcher(originalUrl);
//    if (webpMatcher.find()) {
//      originalUrl = webpMatcher.replaceFirst("");
//    }
//
//    // 去掉已有cdn后缀
//    Matcher tailMatcher = CDN_TAIL_PATTERN.matcher(originalUrl);
//
//    if (tailMatcher.find() && !"_.jpg".equals(tailMatcher.group())) {
//      originalUrl = tailMatcher.replaceFirst("");
//    }
//
//    // 拼接cdn后缀返回
//    CDNSuffixParams suffixParams = new CDNSuffixParams();
//
//    suffixParams.displayWidth = displayWidth;
//    suffixParams.displayHeight = displayHeight;
//    suffixParams.requestOriginSize = requestOriginSize;
//    suffixParams.quality =
//        shouldLoadLowImageQuality() ? (NetWorkUtil.is2G(context) ? IMAGE_QUALITY_EXTRA_LOW : IMAGE_QUALITY_LOW) : IMAGE_QUALITY_HIGH;
//    suffixParams.useWebp = needWebp();
//
//    return originalUrl + getImageUrlCDNSuffix(suffixParams);
//
//  }

    /**
     * 根据image view 大小，匹配最接近的cdn尺寸后缀大小
     * 根据wifi还是移动网络，确定默认的图片质量，wifi高质量，移动网络低质量，用户如果设置图片质量高低，以用户设置为准
     * 根据质量高低设置q参数
     * webp参数，低质量 使用webp 高质量，wifi达到一定速度 用jpg，否则用webp
     * 根据显示要求的宽度和高度反馈cdn后缀，分原图，方图和长图三种情况，原图不加尺寸缩放
     * 方图是长宽相同的情况，长图是宽固定，高自适应的情况，两种情况对应的cdn尺寸是不同的，具体见
     * http://baike.corp.taobao.com/index.php/CS_RD/tfs/http_server
     *
     * @param suffixParams cdn后缀参数
     * @return cdn后缀
     */
    private static String getImageUrlCDNSuffix(int ScreenWidth, CDNSuffixParams suffixParams) {

        String cacheKey = suffixParams.cacheKey();
        String imageUrlSuffix = sSizeCache.get(cacheKey);

        if (imageUrlSuffix != null) {
            return imageUrlSuffix;
        }

        // 开始拼接cdn后缀
        StringBuilder suffixBuilder = new StringBuilder("_");

        // 如果不是强制使用原图尺寸，加上尺寸后缀
        if (!suffixParams.requestOriginSize) {
            int cdnWidth;
            int cdnHeight;

            // 高度自适应的图
            if (suffixParams.displayHeight <= 0) {
                cdnWidth = suffixParams.displayWidth;
                // 默认屏幕宽度
                if (cdnWidth <= 0) {
                    cdnWidth = ScreenWidth;
                }

                cdnWidth = findBestCDNSize(CDN_FIX_WIDTH_SIZE, cdnWidth, suffixParams.quality == IMAGE_QUALITY_HIGH);
                cdnHeight = 10000;

            }
            // 长宽相同的图
            else {
                int maxDisplaySize = Math.max(suffixParams.displayWidth, suffixParams.displayHeight);
                int cdnSize = findBestCDNSize(CDN_SQUARE_SIZE, maxDisplaySize, suffixParams.quality == IMAGE_QUALITY_HIGH);
                cdnWidth = cdnHeight = cdnSize;
            }

            if (cdnWidth > 0 && cdnHeight > 0) {
                suffixBuilder.append(cdnWidth).append("x").append(cdnHeight);
            }
        }

        // 拼接质量q参数
        switch (suffixParams.quality) {
            case IMAGE_QUALITY_EXTRA_LOW:
                suffixBuilder.append(EXTRA_LQ_EXT);
                break;
            case IMAGE_QUALITY_LOW:
                suffixBuilder.append(LQ_EXT);
                break;

            case IMAGE_QUALITY_HIGH:
                suffixBuilder.append(HQ_EXT);
                break;
        }

        // 综合判断需要使用webp
        if (suffixParams.useWebp) {
            suffixBuilder.append(WEBP_EXT);

        }

        imageUrlSuffix = suffixBuilder.toString();

        // 存入缓存
        sSizeCache.put(cacheKey, imageUrlSuffix);

        return imageUrlSuffix;
    }

//  /**
//   * 综合判断是否使用webp
//   */
//  private static boolean needWebp() {
////    boolean webpConfigSwitch = ABTestConfig.getInstance().getBoolean(WEBP_SWITCH_KEY, false);
////
////    // webp配置中心开关关闭，使用jpg
////    if (!webpConfigSwitch) {
////      return false;
////    }
//
//    // 系统版本低于4.0，使用jpg
//    if (Build.VERSION.SDK_INT < 14) {
//      return false;
//    }
//
//    // 图片被设置为高质量，并且webp动态开关关闭（即网络环境为WIFI且速度较快），使用jpg
//    if (NetWorkUtil.isWifi(con) && !webpDynamicSwitch) {
//      return false;
//    }
//
//    return true;
//  }

    /**
     * 根据宽度和图片质量确定cdn size
     */
    private static int findBestCDNSize(int[] array, int size, boolean higher) {

        if (size >= array[array.length - 1]) {
            return array[array.length - 1];
        }

        int pos = binarySearch(array, size, higher);
        return array[pos];
    }

//  public static int getBestLongImageSize() {
//    if (isHighResolution()) {
//      return 640;
//    } else if (isLowResolution()) {
//      return 310;
//    } else {
//      return 460;
//    }
//  }
//
//  /**
//   * 确认是否进入低质量图模式
//   * 低质量模式何时开启
//   * 用户设置中：
//   * <ul>
//   * <li>智能切换模式， 非wifi环境下低质量，wifi环境下高质量
//   * <li>用户指定模式， 用户可指定加载高质量还是低质量图
//   */
//  public static boolean shouldLoadLowImageQuality() {
//    boolean isWifi = NetWorkUtil.isWifi();
//    return ((HaiApplication.autoSwitchPic && !isWifi) || (!HaiApplication.autoSwitchPic && !HaiApplication.switch2high));
//  }
//
//  public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
//    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
//    Canvas canvas = new Canvas(output);
//
//    final int color = 0xff424242;
//    final Paint paint = new Paint();
//    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//    final RectF rectF = new RectF(rect);
//    final float roundPx = bitmap.getWidth() / 2;
//
//    paint.setAntiAlias(true);
//    canvas.drawARGB(0, 0, 0, 0);
//    paint.setColor(color);
//    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//
//    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//    canvas.drawBitmap(bitmap, rect, rect, paint);
//    return output;
//  }

    private static int binarySearch(int[] srcArray, int des, boolean higher) {
        int low = 0;
        int high = srcArray.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (des == srcArray[middle]) {
                return middle;
            } else if (des < srcArray[middle]) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        if (high < 0) {
            return 0;
        }
        if (higher) {
            if (des > srcArray[high] && high + 1 <= srcArray.length - 1) {
                high = high + 1;
            }
        } else {
            if (des < srcArray[high] && high - 1 >= 0) {
                high = high - 1;
            }
        }
        return high;
    }

    public static void setWebpDynamicSwitch(boolean value) {
        webpDynamicSwitch = value;
    }

//  public static boolean isHighResolution() {
//    return HaiApplication.ScreenWidth >= 640;
//  }
//
//  public static boolean isLowResolution() {
//    return HaiApplication.ScreenWidth < 480;
//  }

    private static class CDNSuffixParams {

        /**
         * image view的显示宽度
         */
        public int displayWidth;

        /**
         * image view的显示高度
         */
        public int displayHeight;

        /**
         * 是否使用原始图片大小，不进行cdn尺寸缩放
         */
        public boolean requestOriginSize;

        /**
         * 图片质量
         */
        public int quality;

        /**
         * 是否使用webp
         */
        public boolean useWebp;

        public String cacheKey() {
            StringBuilder cacheKey = new StringBuilder();
            if (!requestOriginSize) {
                cacheKey.append(displayWidth).append("_").append(displayHeight).append("_");
            }

            return cacheKey.append(quality).append("_").append(useWebp).toString();
        }

    }


    /**
     * 图片按高宽比率压缩
     */
    public static Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 图片按高宽比率压缩
     */
    public static byte[] getImageByByte(String srcPath) {
        if(!new File(srcPath).exists()){
            return null;
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        if(bitmap == null){
            return null;
        }
        return compressImageByByte(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 图片按高宽比率压缩
     */
    public static Bitmap getImageByBitmap(String srcPath) {
        if(!new File(srcPath).exists()){
            return null;
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        if(bitmap == null){
            return null;
        }
        return bitmap;
    }

    public static  byte[] fixPic(String path){
        int r = readPictureDegree(path);
        if(r==0){
            return getImageByByte(path);
        }else{
            return compressImageByByte(rotateImage(getImageByBitmap(path),r));
        }
    }


    //通过img得到旋转rotate角度后的bitmap
    public static Bitmap rotateImage(Bitmap img,int rotate){
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        int width = img.getWidth();
        int height =img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, false);
        return img;
    }

    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 质量压缩（字节码）
     */
    public static byte[] compressImageByByte(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        image.recycle();
        return baos.toByteArray();
    }
}
