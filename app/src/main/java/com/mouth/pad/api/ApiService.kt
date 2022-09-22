package com.mouth.pad.api


import com.mouth.pad.bean.TMaterial
import com.mouth.pad.bean.TOrderQueryListBean
import com.mouth.pad.net.HttpsCerUtils
import com.mouth.pad.utils.Logger
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.net.URLDecoder
import java.util.concurrent.TimeUnit
import okhttp3.RequestBody
import okio.Buffer
import java.lang.Exception


interface ApiService {

    companion object {
        private const val baseUrl = "http://47.94.37.16:8099"

        fun get(): ApiService = mApiService
        private const val DEFAULT_TIME_OUT = 60L //超时时间 5s
        private val mOkHttpClient by lazy {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(addLogInterceptor())
            HttpsCerUtils.setTrustAllCertificate(builder)
            builder.build()
        }

        private val mGsonConverterFactory by lazy { GsonConverterFactory.create() }

        private val mApiService by lazy {
            Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(mGsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }


        /**
         * 设置日志拦截
         */
        private fun addLogInterceptor(): HttpLoggingInterceptor {
            var loggingInterceptor = HttpLoggingInterceptor {
                Logger.i(it)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor
        }
    }


    /**
     * 根据材料编号查询-材料基本信息
     */
    @GET("/tMaterial/selectByMaterialCode")
    fun selectByMaterialCode(@Query("materialCode") materialCode: String?): Call<Result<TMaterial>>

    /**
     * 新增订单
     */
    @POST("/tOrder/insert")
    fun insertOrder(@Body params: RequestBody): Call<Result<String>>

    /**
     * 查询全部订单
     */
    @GET("/tOrder/getAll")
    fun getAllOrder(): Call<Result<MutableList<TOrderQueryListBean>>>

    /**
     * 删除订单
     */
    @POST("/tOrder/deleteBatchIds")
    fun deleteOrder(@Body params: RequestBody): Call<Result<String>>

}


