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
            if (body != null) {
                if (interceptor != null) {
                    liveData?.postValue(interceptor.invoke(body))
                } else {
                    liveData?.postValue(body)
                }
            }
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