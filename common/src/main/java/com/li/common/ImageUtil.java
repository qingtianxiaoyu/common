package com.li.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by li on 2016/3/7.
 */
public class ImageUtil {
    /**
     * return cacheFilePath;
     */

    public static String CompressImageByQuality(Context context, Bitmap bitmap, long maxBytes) {

        int options = 100;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
        options = 20;
//        Logger.d(byteArrayOutputStream.toByteArray().length);
        while (byteArrayOutputStream.toByteArray().length > maxBytes) {
//            Logger.d(byteArrayOutputStream.toByteArray().length);
            byteArrayOutputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
            bitmap.recycle();
            bitmap = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
            options = options - 20;
        }
        String cacheFilePath = getImageCachePath(context);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(cacheFilePath);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.recycle();
        return cacheFilePath;


    }

    public static String compressImageBySize(Context context,String filePath, int height, int width, long maxBytes) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int mHeight = options.outHeight;
        int mWidth = options.outWidth;
        double percent = Math.max(mHeight, mWidth) / height;
        if (percent < 1) {
            percent = 1;
        }
        options.inJustDecodeBounds = false;
        //inSampleSize必须为2的n次方，如果不是，就会选择最接近的那个数
        String percentToBinary = Integer.toBinaryString((int) Math.ceil(percent));
        if (!(percent == Math.pow(2, percentToBinary.length() - 1))) {

            percent = Math.pow(2, percentToBinary.length());
        }
        options.inSampleSize = (int) percent;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        String cacheFilePath = CompressImageByQuality(context,bitmap, maxBytes);
        bitmap.recycle();
        return cacheFilePath;

    }

    private static String getImageCachePath(Context context) {

        String imageCachePath = getCachePath(context) + File.separator + "image";
        File imageCacheFile = new File(imageCachePath);
        if (!imageCacheFile.exists()) {
            imageCacheFile.mkdirs();
        }
        UUID uuid = UUID.randomUUID();
        return imageCachePath + File.separator + uuid.toString() + ".jpg";

    }

    //获取应用的缓存路径
    public static String getCachePath(Context context) {
        String cachePath;
        cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ? context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();

        return cachePath;

    }

}
