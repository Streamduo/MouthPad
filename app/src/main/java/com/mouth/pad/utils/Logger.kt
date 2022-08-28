/*
 * © 2019 OneSpot Technology Investment Ltd. All rights reserved.
 */

package com.mouth.pad.utils

import android.util.Log
import com.mouth.pad.BuildConfig
import java.lang.reflect.Modifier
import java.util.Locale
import org.json.JSONObject

object Logger {

    private const val ERROR_MESSAGE = "An exception occurs"

    private const val TAG = "sub_way"
    private var sEnable = BuildConfig.DEBUG

    private val fileLineMethod: String
        get() {
            if (BuildConfig.DEBUG) {
                val element = Exception().stackTrace[2]
                return "[" + element.fileName + "|" + element.methodName + "|" + element.lineNumber + "|" + Thread.currentThread().name + "] "
            }
            return ""
        }

    fun v(message: Any) {
        if (sEnable) {
            Log.v(TAG, fileLineMethod + message)
        }
    }

    fun v(e: Throwable) {
        if (sEnable) {
            Log.v(TAG, fileLineMethod + ERROR_MESSAGE, e)
        }
    }

    fun v(message: Any, e: Throwable) {
        if (sEnable) {
            Log.v(TAG, fileLineMethod + message, e)
        }
    }

    fun i(message: Any) {
        if (sEnable) {
            Log.i(TAG, fileLineMethod + message)
        }
    }

    fun i(e: Throwable) {
        if (sEnable) {
            Log.i(TAG, fileLineMethod + ERROR_MESSAGE, e)
        }
    }

    fun i(message: Any, e: Throwable) {
        if (sEnable) {
            Log.i(TAG, fileLineMethod + message, e)
        }
    }

    fun d(message: Any) {
        if (sEnable) {
            Log.d(TAG, fileLineMethod + message)
        }
    }

    fun d(e: Throwable) {
        if (sEnable) {
            Log.d(TAG, fileLineMethod + ERROR_MESSAGE, e)
        }
    }

    fun d(message: Any, e: Throwable) {
        if (sEnable) {
            Log.d(TAG, fileLineMethod + message, e)
        }
    }

    fun w(message: Any) {
        if (sEnable) {
            Log.w(TAG, fileLineMethod + message)
        }
    }

    fun w(e: Throwable) {
        if (sEnable) {
            Log.w(TAG, fileLineMethod + ERROR_MESSAGE, e)
        }
    }

    fun w(message: Any, e: Throwable) {
        if (sEnable) {
            Log.w(TAG, fileLineMethod + message, e)
        }
    }

    fun e(message: Any) {
        if (sEnable) {
            Log.e(TAG, fileLineMethod + message)
        }
    }

    fun e(e: Throwable) {
        if (sEnable) {
            Log.e(TAG, fileLineMethod + ERROR_MESSAGE, e)
        }
    }

    fun e(message: Any, e: Throwable) {
        if (sEnable) {
            Log.e(TAG, fileLineMethod + message, e)
        }
    }

    private fun buildMessage(message: Any): String {
        val trace = Throwable().fillInStackTrace().stackTrace
        var caller = "<unknown>"
        for (i in 2 until trace.size) {
            val clazz = trace[i].javaClass
            if (clazz != Logger::class.java) {
                var callingClass = trace[i].className
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1)
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1)
                caller = callingClass + "." + trace[i].methodName
                break
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().id, caller, message.toString())
    }

    private fun getFileLineMethod(t: Throwable): String {
        if (BuildConfig.DEBUG) {
            val element = t.stackTrace[2]
            return "[" + element.fileName + "|" + element.methodName + "|" + element.lineNumber + "|" + Thread.currentThread().name + "] "
        }
        return ""
    }

    fun toString(any: Any): String {
        try {
            val jsonObject = JSONObject()
            val buffer = StringBuilder()
            var cls: Class<*>? = any.javaClass
            buffer.append(cls?.simpleName).append(":")
            while (cls != null && cls != Any::class.java) {
                getField(any, cls, jsonObject)
                cls = cls.superclass
            }
            buffer.append(jsonObject.toString())
            return buffer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getField(any: Any, cls: Class<*>, jsonObject: JSONObject) {
        try {
            val fields = cls.declaredFields
            for (field in fields) {
                val mod = field.modifiers
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod) || Modifier.isTransient(mod)) {
                    continue
                }
                field.isAccessible = true
                jsonObject.put(field.name, field.get(any))
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}
