package com.mouth.pad.utils

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import java.util.*

/**
 *

 * @ClassName:      SPUtils
 * @Description:     MMKV工具类
 * @Author:         JiangMin
 * @CreateDate:     2020/12/9 16:49
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/12/9 16:49
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object SpUtil {
    var mmkv: MMKV? = null

    init {
        mmkv = MMKV.mmkvWithID("user", MMKV.SINGLE_PROCESS_MODE);
    }

    fun encode(key: String, value: Any?) {
        when (value) {
            is String -> mmkv?.encode(key, value)
            is Float -> mmkv?.encode(key, value)
            is Boolean -> mmkv?.encode(key, value)
            is Int -> mmkv?.encode(key, value)
            is Long -> mmkv?.encode(key, value)
            is Double -> mmkv?.encode(key, value)
            is ByteArray -> mmkv?.encode(key, value)
            is Short -> mmkv?.encode(key, value.toInt())
            is Nothing -> return
        }
    }

    fun <T : Parcelable> encode(key: String, t: T?) {
        if (t == null) {
            return
        }
        mmkv?.encode(key, t)
    }

    fun encode(key: String, sets: Set<String>?) {
        if (sets == null) {
            return
        }
        mmkv?.encode(key, sets)
    }

    fun decodeInt(key: String): Int? {
        return mmkv?.decodeInt(key, 0)
    }

    fun decodeDouble(key: String): Double? {
        return mmkv?.decodeDouble(key, 0.00)
    }

    fun decodeLong(key: String): Long? {
        return mmkv?.decodeLong(key, 0L)
    }

    fun decodeBoolean(key: String): Boolean? {
        return mmkv?.decodeBool(key, false)
    }

    fun decodeFloat(key: String): Float? {
        return mmkv?.decodeFloat(key, 0F)
    }

    fun decodeByteArray(key: String): ByteArray? {
        return mmkv?.decodeBytes(key)
    }

    fun decodeString(key: String): String? {
        return mmkv?.decodeString(key, "")
    }

    fun <T : Parcelable> decodeParcelable(key: String, tClass: Class<T>): T? {
        return mmkv?.decodeParcelable(key, tClass)
    }


    fun decodeStringSet(key: String): Set<String>? {
        return mmkv?.decodeStringSet(key, Collections.emptySet())
    }

    fun removeKey(key: String) {
        mmkv?.removeValueForKey(key)
    }

    fun clearAll() {
        val keys = mmkv?.allKeys()
        keys?.let {
            for (key in it) {
                mmkv?.remove(key)
            }
        }
    }

    fun containsKey(key: String): Boolean? {
        return mmkv?.containsKey(key)
    }

}
