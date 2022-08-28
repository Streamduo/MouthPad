package com.mouth.pad.utils;

/**
 * @ClassName: ByteUtils
 * @Description: java类作用描述
 * @Author: JiangMin
 * @CreateDate: 2021/1/30 15:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/30 15:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ByteUtils {

    /**
     * byte转Short
     * @param bytes
     * @return
     */
    public static int byte2ToShort(byte[] bytes) {
        int high = bytes[0];
        int low = bytes[1];
        return (high << 8 & 0xFF00) | (low & 0xFF);
    }

    /**
     * short转byte
     * @param s
     * @return
     */
    public static byte[] shortToByte2(int s) {
        byte[] targets = new byte[2];
        targets[0] = (byte) (s >> 8 & 0xFF);
        targets[1] = (byte) (s & 0xFF);
        return targets;
    }

}
