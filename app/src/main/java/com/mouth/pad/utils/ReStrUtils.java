package com.mouth.pad.utils;

/**
 * @ClassName: ReStrUtils
 * @Description: java类作用描述
 * @Author: Fuduo
 * @CreateDate: 2021/2/4 10:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/4 10:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ReStrUtils {

//    /**
//     * 身份证号加*号
//     */
//    public static String reNo(String realNo) {
//        String newNo = "";
//        if (realNo.length() == 15) {
//            newNo = realNo.replaceAll("(\\d{3})\\d{10}(\\d{2})", "$1**********$2");
//        }
//        if (realNo.length() == 18) {
//            newNo = realNo.replaceAll("(\\d{3})\\d{13}(\\d{2})", "$1*************$2");
//        }
//        return newNo;
//    }
//
//    /**
//     * 名字加*号
//     *
//     * @param realname
//     * @return
//     */
//    public static String reName(String realname) {
//        char[] r = realname.toCharArray();
//        String name = "";
//        if (r.length == 1) {
//            name = realname;
//            return name;
//        }
//
//        if (r.length >= 10) {
//            String str = "*********";
//            name = realname.replaceFirst(realname.substring(0, r.length - 1), str);
//            return name;
//        }
//
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < r.length - 1; i++) {
//            stringBuilder.append("*");
//        }
//        name = realname.replaceFirst(realname.substring(0, r.length - 1), stringBuilder.toString());
//        return name;
//    }

    /**
     * 手机号加*号
     *
     * @param realPhone
     * @return
     */
    public static String rePhone(String realPhone) {
        String phoneNumber;
        if (realPhone == null) {
            return "";
        }
        if (realPhone.length() == 11) {
            phoneNumber = realPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        } else {
            phoneNumber = realPhone;
        }
        return phoneNumber;
    }

}
