package com.mouth.pad.net

import android.widget.Toast
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mouth.pad.MyApplication
import com.mouth.pad.utils.Logger
import com.mouth.pad.utils.SpUtil
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.nio.charset.Charset

/**
 *  @Description: 全局响应码拦截器
 *  @Author: Huntero
 *  2021/3/15 18:51
 */
class GlobalResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
//        val isLogout = req.url().toString().contains(ApiService.URL_LOGOUT)
        val resp = chain.proceed(req)
        Logger.i("GlobalResponseInterceptor > url=${req.url()}")
//        if (isLogout) {
//            return resp
//        }
        try {
            val data = resp.body()?.let { respBody ->
                val source = respBody.source()
                source.buffer()?.clone()?.readString(Charset.forName("UTF-8"))
            }
//            data?.let {
//                try {
//                    val json = JSONObject(it)
//                    val code = json.optInt("code")
//                    if (code != null && (code == 904 || code == 903 || code == 902)) {
//                        // 已被踢掉
//                         Toast.makeText(MyApplication.appContext, "设备登录异常，请重新登录", Toast.LENGTH_LONG).show()
//                        //
//                        SpUtil.clearAll()
//                        //通知其他页面登录信息修改
//                        LiveEventBus.get("RefreshUserInfo").post(RefreshUserInfoEvent())
//                        LoginActivity.loginOut(MyApplication.appContext)
//                    }
//                } catch (e: Exception) {
//                }
//            }
        } catch (e: Exception) {
        }

        return resp
    }
}