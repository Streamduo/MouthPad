package com.mouth.pad.api

import androidx.lifecycle.MutableLiveData
import com.mouth.pad.MyApplication.Companion.appContext
import com.mouth.pad.R
import com.mouth.pad.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LiveDataCallback<T>(
    private val liveData: MutableLiveData<Result<T>>?,
    private val interceptor: ((result: Result<T>?) -> Result<T>?)? = null
) : Callback<Result<T>> {
    override fun onFailure(call: Call<Result<T>>, e: Throwable) {
        Logger.d("onFailure e=$e")
        liveData?.postValue(Result.throwable(e, appContext.getString(R.string.net_error)))
    }

    override fun onResponse(call: Call<Result<T>>, response: Response<Result<T>>) {
        Logger.d("response1= $response")
        if (response.isSuccessful) {
            val body = response.body()
//            when (body?.code) {
//                //验签失败
//                CODE_SIGN_ERROR -> {
//                    liveData?.postValue(
//                        Result.throwable(
//                            553,
//                            appContext.getString(R.string.sign_error)
//                        )
//                    )
//                }
//                //拦截TOKEN异常 换手机登陆的情况
//                CODE_TOKEN_GET_ERR,
//                CODE_OTHER_LOGIN_IN,
//                CODE_TOKEN_ERR -> {
//                    liveData?.postValue(
//                        Result.throwable(
//                            response.code(),
//                            appContext.getString(R.string.token_error)
//                        )
//                    )
//                    loginOut()
//                    appContext.startActivity(Intent(appContext, LoginActivity::class.java).apply {
//                        //如果是一个singletask activity 则需要添加FLAG_ACTIVITY_CLEAR_TOP ，否则启动可能失败
//                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        addCategory("android.intent.category.LAUNCHER")
//                        action = "android.intent.action.MAIN"
//                    })
//                    Toast.makeText(appContext, appContext.getString(R.string.server_code_err, body.code), Toast.LENGTH_LONG).show()
//                }
//                else -> {
                    if (body != null) {
                        if (interceptor != null) {
                            liveData?.postValue(interceptor.invoke(body))
                        } else {
                            liveData?.postValue(body)
                        }
                    }
//                }
//            }
        } else {
            liveData?.postValue(
                Result.throwable(
                    response.code(),
                    appContext.getString(R.string.server_error)
                )
            )
        }
    }
}