package com.bmncc.pis.ylct.utils

import android.content.Context
import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 *

 * @ClassName:      TimeUtils
 * @Description:    时间操作类
 * @Author:         FuDuo
 * @CreateDate:     2020/12/3 14:39
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/12/3 14:39
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object TimeUtils {

    const val TIME_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
    const val TIME_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm"
    const val TIME_YYYY_MM_DD = "yyyy-MM-dd"
    const val TIME_YYYY_MM_DD_CHINESE = "yyyy年MM月dd日"
    const val TIME_YYYY_MMDD = "yyyy-MMdd"
    const val TIME_HH_MM = "HH:mm"
    const val TIME_MM_DD_HH_MM = "MM月dd日 HH:mm"
    const val TIME_HH_MM_CHINESE = "HH时mm分"
    const val TIME_YYYY_MM_DD_L = "yyyy.MM.dd"

    //根据当前时间增加乘车需要的时间
    fun getEstimate(estimate: Int): String {
        val simpleDateFormat = SimpleDateFormat("HH : mm")
        val date = Date()
        var calender = Calendar.getInstance()
        calender.time = date;
        calender.add(Calendar.MINUTE, estimate)
        var time = calender.time
        return simpleDateFormat.format(time)
    }

    /**
     *格式转换
     */
    fun formatDate(time: Long, format: String?): String? {
        val res: String
        val simpleDateFormat = SimpleDateFormat(format)
        //如果它本来就是long类型的,则不用写这一步
        val date = Date(time)
        res = simpleDateFormat.format(date)
        return res
    }

    fun getTime(date: String): String? {
        val format = SimpleDateFormat("MM-dd HH:mm")
        return format.format(date)
    }

    fun getYear(date: String): String? {
        val format = SimpleDateFormat(TIME_YYYY_MM_DD)
        return format.format(date)
    }

    fun getYear(date: Long): String? {
        val format = SimpleDateFormat(TIME_YYYY_MM_DD)
        return format.format(date)
    }

    fun getYearChinese(date: Long): String? {
        val format = SimpleDateFormat(TIME_YYYY_MM_DD_CHINESE)
        return format.format(date)
    }

    fun getYear(date: Date): String? {
        val format = SimpleDateFormat(TIME_YYYY_MM_DD_HH_MM)
        return format.format(date)
    }
    fun getYearDay(date: Date): String? {
        val format = SimpleDateFormat(TIME_YYYY_MM_DD)
        return format.format(date)
    }

    //string转date
    fun getStringToDate(date: String): Date? { //可根据需要自行截取数据显示
        val format = SimpleDateFormat(TIME_YYYY_MM_DD_HH_MM_SS)
        return try {
            format.parse(date)
        } catch (e: Exception) {
            null
        }
    }

    fun getTimeChinese(str: String): String? {
        val format = SimpleDateFormat(TIME_YYYY_MM_DD_CHINESE)
        val date = getStringToDate(str) ?: return ""
        val dateStr = format.format(date)
        return if (dateStr.isNullOrEmpty()) {
            ""
        } else {
            dateStr
        }
    }

    fun getTimeYyyyMMDD(str: String): String? {
        val format = SimpleDateFormat(TIME_YYYY_MMDD)
        val date = getStringToDate(str) ?: return ""
        val dateStr = format.format(date)
        return if (dateStr.isNullOrEmpty()) {
            ""
        } else {
            dateStr
        }
    }

    fun getTimeYyyyMM_DD(str: String): String? {
        val format = SimpleDateFormat(TIME_YYYY_MM_DD)
        val date = getStringToDate(str) ?: return ""
        val dateStr = format.format(date)
        return if (dateStr.isNullOrEmpty()) {
            ""
        } else {
            dateStr
        }
    }

    fun getTimeHhMm(str: String): String? {
        val format = SimpleDateFormat(TIME_HH_MM)
        val date = getStringToDate(str) ?: return ""
        val dateStr = format.format(date)
        return if (dateStr.isNullOrEmpty()) {
            ""
        } else {
            dateStr
        }
    }

    fun getTimeHhMm(date: Date): String? {
        val format = SimpleDateFormat(TIME_HH_MM)
        val dateStr = format.format(date)
        return if (dateStr.isNullOrEmpty()) {
            ""
        } else {
            dateStr
        }
    }

    fun getTimeYyyyMMDDHHMM(str: String): String? {
        val format = SimpleDateFormat(TIME_YYYY_MM_DD_HH_MM)
        val date = getStringToDate(str) ?: return ""
        val dateStr = format.format(date)
        return if (dateStr.isNullOrEmpty()) {
            ""
        } else {
            dateStr
        }
    }

    fun getTimeMMDDHHMM(str: String): String? {
        val format = SimpleDateFormat(TIME_MM_DD_HH_MM)
        val date = getStringToDate(str) ?: return ""
        val dateStr = format.format(date)
        return if (dateStr.isNullOrEmpty()) {
            ""
        } else {
            dateStr
        }
    }

    //获取当前系统时间月份和日
    fun getTodayMonthDay(): String {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return month.toString() + day.toString()
    }

    /**
     * 设置星期几
     */
    fun getCurrentDate(): String {
        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("GMT+8:00")
        val mYear = c.get(Calendar.YEAR).toString() // 获取当前年份
        val mMonth = (c.get(Calendar.MONTH) + 1).toString()// 获取当前月份
        val mDay = c.get(Calendar.DAY_OF_MONTH).toString()// 获取当前月份的日期号码
        var mWay = c.get(Calendar.DAY_OF_WEEK).toString()
        val hour = c.get(Calendar.HOUR_OF_DAY).toString()
        val minute = c.get(Calendar.MINUTE).toString()
        val second = c.get(Calendar.SECOND).toString()
        when (mWay) {
            "1" -> {
                mWay = "天"
            }
            "2" -> {
                mWay = "一"
            }
            "3" -> {
                mWay = "二"
            }
            "4" -> {
                mWay = "三"
            }
            "5" -> {
                mWay = "四"
            }
            "6" -> {
                mWay = "五"
            }
            "7" -> {
                mWay = "六"
            }
        }
        return mYear + "年" + mMonth + "月" + mDay + "日" + "  星期" + mWay + "  " + hour + ":" + minute + ":" + second
    }

    /**
     * 获取当前精确到秒的时间戳
     * @param date
     * @return
     */
    fun getSecondTimestampTwo(): Int {
        val date = Date()
        val timestamp = java.lang.String.valueOf(date.time / 1000)
        return Integer.valueOf(timestamp)
    }

    fun cal(second: Int): String? {
        var h = 0
        var d = 0
        val temp = second % 3600
        if (second > 3600) {
            h = second / 3600
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60
                }
            }
        } else {
            d = second / 60
        }
        return if (h > 0) {
            if (d > 0) {
                h.toString() + "小时" + d + "分"
            } else {
                h.toString() + "小时"
            }
        } else {
            d.toString() + "分"
        }

    }

    fun cal2(second: Int): String? {
        var h = 0
        var d = 0
        var s = 0
        val temp = second % 3600
        if (second > 3600) {
            h = second / 3600
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60
                    if (temp % 60 != 0) {
                        s = temp % 60
                    }
                } else {
                    s = temp
                }
            }
        } else {
            d = second / 60
            if (second % 60 != 0) {
                s = second % 60
            }
        }
        return "$h:$d:$s"
    }

    /**
     * 根据时间格式返还时间戳
     * @param yyyy-MM-dd HH:mm:ss
     */
    fun getTimeStamp(time: String): Long {
        val parse = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        return parse.time
    }

    /**
     * 根据时间格式返还时间戳
     */
    fun getStrTimeStamp(time: String, format: String): Long {
        val parse = SimpleDateFormat(format).parse(time);
        return parse.time
    }

    /**
     * 根据时间格式返还时间戳
     */
    fun getTimeStamp(time: String, dateFormat: String): String? {
        val parse = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        return formatDate(parse.time, dateFormat)
    }

    const val MIN_TO_SEC = 60
    const val HOUR_TO_SEC = MIN_TO_SEC * 60
    const val DAY_TO_SEC = HOUR_TO_SEC * 24
    const val MONTH_TO_SEC = DAY_TO_SEC * 30
    const val YEAR_TO_SEC = MONTH_TO_SEC * 365


    //比对日期的大小
    fun compareDate(date1: String?, date2: String?): Int {
        val df = SimpleDateFormat(TIME_YYYY_MM_DD)
        try {
            val dt1: Date = df.parse(date1)
            val dt2: Date = df.parse(date2)
            return when {
                dt1.time > dt2.time -> {
//                    "dt1 在dt2后"
                    1
                }
                dt1.time <= dt2.time -> {
//                    "dt1在dt2前"
                    2
                }
                else -> {
                    0
                }
            }
        } catch (exception: java.lang.Exception) {
            exception.printStackTrace()
        }
        return 0
    }

}