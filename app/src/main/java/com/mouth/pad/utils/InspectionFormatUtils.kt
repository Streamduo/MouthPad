package com.bmncc.pis.ylct.utils

import java.util.*
import java.util.regex.Pattern

/**
 *

 * @ClassName:      InspectionFormatUtils
 * @Description:     java类作用描述
 * @Author:         Fuduo
 * @CreateDate:     2020/12/23 16:09
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/12/23 16:09
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object InspectionFormatUtils {

    /**
     * 功能：判断昵称是否符合规则
     *
     * @param str
     * @return
     */
    fun isNickname(str: String?): Boolean {
        val pattern = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z0-9]{1,16}+\$")
        val isNickname = pattern.matcher(str)
        return isNickname.matches()
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    fun isNumeric(str: String?): Boolean {
        val pattern = Pattern.compile("[0-9]*")
        val isNum = pattern.matcher(str)
        return isNum.matches()
    }

    /**
     * 功能：判断字符串是否是纯汉字
     *
     * @param name
     * @return
     */
    fun checkName(name: String): Boolean {
        var n = 0
        for (element in name) {
            n = element.toInt()
            if (n !in 19968..40868) {
                return false
            }
        }
        return true
    }

    /**
     * 功能：判断字符串是否为邮箱
     *
     * @param str
     * @return
     */
    fun isEmail(str: String?): Boolean {
        val pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
        val isEmail = pattern.matcher(str)
        return isEmail.matches()
    }



    /**
     * 功能：判断密码是否为合格
     *
     * @param str
     * @return
     */
    fun isPasswordOk(str: String?): Boolean {
        val pattern = Pattern.compile("^(?=.*?[0-9])(?=.*?[a-z])(?=.*?[A-Z])[0-9A-Za-z]{8,20}\$")
        val isNum = pattern.matcher(str)
        return isNum.matches()
    }

    /**
     * 功能：判断字符串是否为手机号
     *
     * @param phone
     * @return
     */
    //支持13、14、15、16、17、18、19开头后面任意搭9位
    fun regexPhone(phone: String): Boolean {
//        val compile = Pattern.compile("^(13|14|15|16|17|18|19)\\d{9}$")
//        val matcher = compile.matcher(phone)
//        return matcher.matches()
        return true
    }

    /**
     * 身份证份证id正则校验
     */
    fun isIDNumber(IDNumber: String?): Boolean {
        if (IDNumber == null || "" == IDNumber) {
            return false
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        val regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" + "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)"
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        val matches = IDNumber.matches(regularExpression.toRegex())

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length == 18) {
                try {
                    val charArray = IDNumber.toCharArray()
                    //前十七位加权因子
                    val idCardWi = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
                    //这是除以11后，可能产生的11位余数对应的验证码
                    val idCardY = arrayOf("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2")
                    var sum = 0
                    for (i in idCardWi.indices) {
                        val current = Integer.parseInt(charArray[i].toString())
                        val count = current * idCardWi[i]
                        sum += count
                    }
                    val idCardLast = charArray[17]
                    val idCardMod = sum % 11
                    return idCardY[idCardMod].toUpperCase() == idCardLast.toString().toUpperCase()

                } catch (e: Exception) {
                    e.printStackTrace()
//                    println("异常:$IDNumber")
                    return false
                }

            }

        }
        return matches
    }

    /**
     * 根据身份证号计算年龄
     */
    fun idCardToAge(idCard: String): Int {
        val selectYear = Integer.valueOf(idCard.substring(6, 10))         //出生的年份
        val selectMonth = Integer.valueOf(idCard.substring(10, 12))       //出生的月份
        val selectDay = Integer.valueOf(idCard.substring(12, 14))         //出生的日期
        val cal = Calendar.getInstance()
        val yearMinus = cal.get(Calendar.YEAR) - selectYear

        val monthMinus = cal.get(Calendar.MONTH) + 1 - selectMonth
        val dayMinus = cal.get(Calendar.DATE) - selectDay
        var age = yearMinus//只精确到年份

        //如果要精确到月份的话则只需要代码中注释的部分打开
        /*  if (yearMinus < 0) {
              age = 0
          } else if (yearMinus == 0) {
              age = 0
          } else if (yearMinus > 0) {
              if (monthMinus == 0) {
                  if (dayMinus < 0) {
                      age = age - 1
                  }
              } else if (monthMinus > 0) {
                  age = age + 1
              }
          }*/
        return age
    }

}