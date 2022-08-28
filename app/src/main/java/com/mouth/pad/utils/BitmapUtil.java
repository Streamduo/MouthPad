package com.mouth.pad.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName: BitmapUtil
 * @Description: Bitmap工具类
 * @Author: Fuduo
 * @CreateDate: 2021/2/22 15:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/22 15:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BitmapUtil {

    /**
     * 保存Bitmap图片到指定文件
     *
     * @param bm
     */
    public static boolean saveBitmap(Bitmap bm, String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
