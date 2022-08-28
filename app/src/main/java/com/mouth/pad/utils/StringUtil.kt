package com.bmncc.pis.ylct.utils

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.TextAppearanceSpan
import com.mouth.pad.MyApplication.Companion.appContext
import com.mouth.pad.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.experimental.or


/**
 *

 * @ClassName:      StringUtil
 * @Description:     字符串工具类
 * @Author:         JiangMin
 * @CreateDate:     2020/12/17 14:17
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/12/17 14:17
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object StringUtil {

    /**
     * 获取本地文件
     */
    fun getJson(fileName: String?, context: Context): String? {
        //将json数据变成字符串
        val stringBuilder = StringBuilder()
        try {
            //获取assets资源管理器
            val assetManager: AssetManager = context.assets
            //通过管理器打开文件并读取
            val bf = BufferedReader(InputStreamReader(
                    assetManager.open(fileName!!)))
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }


    /**
     * 获取颜色
     */
    fun getColor(color: String?, default: String?): Int {
        return if (color.isNullOrEmpty() || color.indexOf("#") != 0) Color.parseColor(default) else Color.parseColor(color)
    }


    /**
     * 合并多个byte[]内容
     */
    public fun byteCopy(srcArrays: List<ByteArray>): ByteArray? {
        var len = 0
        for (srcArray in srcArrays) {
            len += srcArray.size
        }
        val destArray = ByteArray(len)
        var destLen = 0
        for (srcArray in srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.size)
            destLen += srcArray.size
        }
        return destArray
    }


    /**
     * 合并两个byte[]内容
     */
    fun byteCopy(bt1: ByteArray, bt2: ByteArray): ByteArray {
        val bt3 = ByteArray(bt1.size + bt2.size)
        System.arraycopy(bt1, 0, bt3, 0, bt1.size)
        System.arraycopy(bt2, 0, bt3, bt1.size, bt2.size)
        return bt3
    }


    /**
     * 返回的UTC时间戳
     */
    fun getUTCTimeStr(): Int {
        val cal: Calendar = Calendar.getInstance()
        val tz: TimeZone = TimeZone.getTimeZone("GMT")
        cal.timeZone = tz
        return (cal.timeInMillis / 1000).toInt() //
    }


    fun bytes2Int(bytes: ByteArray): Int {
        return ByteBuffer.wrap(bytes).int
    }

    /**
     * int转byte数组
     */
    fun intToByteHH(n: Int): ByteArray {
        val b = ByteArray(4)
        b[3] = (n and 0xff).toByte()
        b[2] = (n shr 8 and 0xff).toByte()
        b[1] = (n shr 16 and 0xff).toByte()
        b[0] = (n shr 24 and 0xff).toByte()
        return b
    }

    fun bytesToHexString(src: ByteArray?): String? {
        val stringBuilder = StringBuilder("")
        if (src == null || src.isEmpty()) {
            return null
        }
        for (element in src) {
            val v = element.toInt() and 0xFF
            val hv = Integer.toHexString(v)
            if (hv.length < 2) {
                stringBuilder.append(0)
            }
            stringBuilder.append(hv)
        }
        return stringBuilder.toString()
    }


    fun hexToBytes(hex: String): ByteArray? {
        var hex = hex
        hex = if (hex.length % 2 != 0) "0$hex" else hex
        val b = ByteArray(hex.length / 2)
        for (i in b.indices) {
            val index = i * 2
            val v = hex.substring(index, index + 2).toInt(16)
            b[i] = v.toByte()
        }
        return b
    }


    /**
     * 分转元
     */
    fun fenToYuan(amount: String?): String? {
        var newAmount = StringBuffer()
        if (amount.isNullOrEmpty()) {
            return ""
        } else {
            when (amount.length) {
                1 -> {
                    newAmount.append("0.0").append(amount)
                }
                2 -> {
                    newAmount.append("0.").append(amount)
                }
                else -> {
                    newAmount.append(amount.substring(0, amount.length - 2)).append(".").append(amount.substring(amount.length - 2))
                }
            }
        }

        return newAmount.toString()
    }


    fun byteToShort(bt: ByteArray): Short {
        return (((bt[0].toInt() shl 8) and 0xFF00).toShort() or bt[1].toShort())
    }

    fun getNumFromStr(item: String): String {
        val p: Pattern = Pattern.compile("\\d+")
        val m: Matcher = p.matcher(item)
        return if (m.find()) {
            //获取线路中的数字
            val group = m.group()
            //判断如果是单个数字和两个数字的线路
            if (group.length == 1) {
                "0$group"
            } else {
                group
            }
        } else {
            "01"
        }
    }


    //提取字符串中为字母的字符
    fun returnResultMultiple(str: String): String? {
        var string = ""
        if (str == "") {
            return ""
        }
        for (element in str) {
            if (element in 'A'..'Z' || element in '1'..'9') {
                string += element
            }
        }
        return string
    }

    fun encodeHeadInfo(headInfo: String): String {
        val stringBuffer = StringBuffer()
        var i = 0
        val length = headInfo.length
        while (i < length) {
            val c = headInfo[i]
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", c.toInt()))
            } else {
                stringBuffer.append(c)
            }
            i++
        }
        return stringBuffer.toString()
    }

    /**
     * 使用正则表达式来判断字符串中是否包含字母
     * @param str 待检验的字符串
     * @return 返回是否包含
     * true: 包含字母 ;false 不包含字母
     */
    fun judgeContainsStr(str: String?): Boolean {
        val regex = ".*[a-zA-Z]+.*"
        val m = Pattern.compile(regex).matcher(str)
        return m.matches()
    }
}