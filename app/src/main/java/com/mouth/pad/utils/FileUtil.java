package com.mouth.pad.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.mouth.pad.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @ClassName: FileUtil
 * @Description: 文件夹工具类
 * @Author: Fuduo
 * @CreateDate: 2021/2/22 18:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/22 18:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FileUtil {


    /**
     * 获取存贮文件的文件夹路径
     *
     * @return
     */
    public static File createFolders(String path) {
        File baseDir = Environment
                .getExternalStoragePublicDirectory(path);
        if (baseDir == null)
            return Environment.getExternalStorageDirectory();
        File aviaryFolder = new File(baseDir.getPath());
        if (aviaryFolder.exists())
            return aviaryFolder;
        if (aviaryFolder.mkdirs())
            return aviaryFolder;
        return Environment.getExternalStorageDirectory();
    }


    /**
     * 获取缓存文件夹 用于清除缓存用
     *
     * @param fileName
     * @return
     */
    public static File getCacheFile(String fileName) {
        String path = MyApplication.Companion.getAppContext().getExternalFilesDir(null).getPath() + "/data";
        if (fileName != null && !fileName.isEmpty()) {
            path = path + "/" + fileName;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static String createCacheFilePath(String fileName) {
        String path = MyApplication.Companion.getAppContext().getExternalFilesDir(null).getPath();
        if (fileName != null && !fileName.isEmpty()) {
            path = path + "/" + fileName;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }


    /**
     * 获取缓存文件夹
     *
     * @return
     */
    public static File getCacheFile() {
        String path = MyApplication.Companion.getAppContext().getExternalFilesDir(null).getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 删除指定文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        File file;
        try {
            file = new File(path);
            if (file.exists()) {
                return file.delete();
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }


    /**
     * 获取文件夹大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) { // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位 * * @param size * @return
     */
    public static String getFormatSize(Long size) {
        Long kiloByte = size / 1024L;
        Long megaByte = (kiloByte / 1024L);
        return megaByte + "MB";
    }

    /**
     * 转换路径
     * 把uri转化为 File路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
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

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param filePath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        return flag;
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param filePath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(filePath);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
    }


    /**
     * Uri 转 绝对路径
     *
     * @param uri
     * @return
     */
    public static String getFilePathByUri_BELOWAPI11(Uri uri, Context context) {
        // 以 content:// 开头的，比如  content://media/external/file/960
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            String path = null;
            String[] projection = new String[]{MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null,
                    null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        return null;
    }

    /**
     * 根据文件路径拷贝文件
     *
     * @param src      源文件
     * @param destPath 目标文件路径
     * @return boolean 成功true、失败false
     */
    public static boolean copyFile(File src, String destPath, String destFileName) {
        if ((src == null) || (destPath == null)) {
            return false;
        }
        File dest = new File(destPath, destFileName);
        if (dest.exists()) {
            boolean isSuccess = dest.delete();
            if (!isSuccess) {
                return false;
            }
        }
        try {
            boolean isSuccess = dest.createNewFile();
            if (!isSuccess) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        boolean result = false;
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;
        try {
            srcChannel = new FileInputStream(src).getChannel();
            dstChannel = new FileOutputStream(dest).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), dstChannel);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (srcChannel != null) {
                    srcChannel.close();
                }
                if (dstChannel != null) {
                    dstChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取dataFile文件夹下的所有文件
     */
    public static LinkedHashMap<String, String> getAllDataFile(String collectionPath) {
        LinkedHashMap<String, String> fileList = new LinkedHashMap<>();
        File file = new File(collectionPath);
        File[] tempList = file.listFiles();
        if (tempList != null && tempList.length > 0) {
            for (File localFile : tempList) {
                if (localFile.isFile()) {
                    String fileName = localFile.getName();
                    String path = localFile.getAbsolutePath();
                    if (fileName.endsWith("png")) {
                        fileList.put(fileName, path);
                    }
                }
            }
        }
        return fileList;
    }

}
