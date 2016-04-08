package com.ratio.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Mesogene on 2015/5/24.
 */
public class FilePathConstants {
    private static final String SD_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private static final String ACCESSORY_DOWNLOAD_PATH = SD_FILE_PATH + File.separator + "codoonsports" + File.separator + "accessory";
    private static final String Photo_IMG_PATH;
    private static final String SOFRWARE_DOWNLOAD_PATH;

    static {
        StringBuilder localStringBuilder1 = new StringBuilder();
        Photo_IMG_PATH = SD_FILE_PATH + File.separator + "codoonsports" + File.separator + "photos";
        StringBuilder localStringBuilder2 = new StringBuilder();
        SOFRWARE_DOWNLOAD_PATH = SD_FILE_PATH + File.separator + "codoonsports" + File.separator + "apk";
        StringBuilder localStringBuilder3 = new StringBuilder();
    }

    public static String getAccessoryDownLoadPath() {
        File localFile = new File(ACCESSORY_DOWNLOAD_PATH);
        if (!localFile.exists())
            localFile.mkdirs();
        return localFile.getAbsolutePath();
    }

    public static String getAdvPhotosPath(Context paramContext) {
        File localFile1 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder localStringBuilder1 = new StringBuilder();
            localFile1 = new File(Photo_IMG_PATH + File.separator + "adv");
        }
        File localFile2;
        for (Object localObject = localFile1; ; localObject = localFile2) {
            if (!((File) localObject).exists())
                ((File) localObject).mkdirs();
            String str = paramContext.getApplicationContext().getFilesDir().getAbsolutePath();
            StringBuilder localStringBuilder2 = new StringBuilder();
            localFile2 = new File(str + File.separator + "photos" + File.separator + "adv");
            return ((File) localObject).getAbsolutePath();

        }
    }

    public static String getAvatarPhotosPath(Context paramContext) {
        File localFile1 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder localStringBuilder1 = new StringBuilder();
            localFile1 = new File(Photo_IMG_PATH + File.separator + "avater");
        }
        File localFile2;
        for (Object localObject = localFile1; ; localObject = localFile2) {
            if (!((File) localObject).exists())
                ((File) localObject).mkdirs();

            String str = paramContext.getApplicationContext().getFilesDir().getAbsolutePath();
            StringBuilder localStringBuilder2 = new StringBuilder();
            localFile2 = new File(str + File.separator + "photos" + File.separator + "avater");
            return ((File) localObject).getAbsolutePath();
        }

    }

    public static String getFeedPhotosPath(Context paramContext) {
        File localFile1 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder localStringBuilder1 = new StringBuilder();
            localFile1 = new File(Photo_IMG_PATH + File.separator + "avater");
        }
        File localFile2;
        for (Object localObject = localFile1; ; localObject = localFile2) {
            if (!((File) localObject).exists())
                ((File) localObject).mkdirs();
            String str = paramContext.getApplicationContext().getFilesDir().getAbsolutePath();
            StringBuilder localStringBuilder2 = new StringBuilder();
            localFile2 = new File(str + File.separator + "photos" + File.separator + "avater");
            return ((File) localObject).getAbsolutePath();

        }
    }

    public static String getHeadPhotosPath(Context paramContext) {
        File localFile1 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder localStringBuilder1 = new StringBuilder();
            localFile1 = new File(Photo_IMG_PATH + File.separator + "avater");
        }
        File localFile2;
        for (Object localObject = localFile1; ; localObject = localFile2) {
            if (!((File) localObject).exists())
                ((File) localObject).mkdirs();
            String str = paramContext.getApplicationContext().getFilesDir().getAbsolutePath();
            StringBuilder localStringBuilder2 = new StringBuilder();
            localFile2 = new File(str + File.separator + "photos" + File.separator + "avater");
            return ((File) localObject).getAbsolutePath();

        }
    }

    public static String getSharePhotosPath(Context paramContext) {
        File localFile1 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder localStringBuilder1 = new StringBuilder();
            localFile1 = new File(Photo_IMG_PATH + File.separator + "share");
        }
        File localFile2;
        for (Object localObject = localFile1; ; localObject = localFile2) {
            if (!((File) localObject).exists())
                ((File) localObject).mkdirs();
            String str = paramContext.getApplicationContext().getFilesDir().getAbsolutePath();
            StringBuilder localStringBuilder2 = new StringBuilder();
            localFile2 = new File(str + File.separator + "photos" + File.separator + "share");
            return ((File) localObject).getAbsolutePath();

        }
    }

    public static String getSoftwareDownloadPath() {
        File localFile = new File(SOFRWARE_DOWNLOAD_PATH);
        if (!localFile.exists())
            localFile.mkdirs();
        return localFile.getAbsolutePath();
    }

    public static String getZipPhotosPath(Context paramContext) {
        File localFile1 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder localStringBuilder1 = new StringBuilder();
            localFile1 = new File(Photo_IMG_PATH + File.separator + "zip");
        }
        File localFile2;
        for (Object localObject = localFile1; ; localObject = localFile2) {
            if (!((File) localObject).exists())
                ((File) localObject).mkdirs();
            String str = paramContext.getApplicationContext().getFilesDir().getAbsolutePath();
            StringBuilder localStringBuilder2 = new StringBuilder();
            localFile2 = new File(str + File.separator + "photos" + File.separator + "zip");
            return ((File) localObject).getAbsolutePath();

        }
    }
}
