package com.mouth.pad.api


import com.mouth.pad.net.HttpsCerUtils
import com.mouth.pad.utils.Logger
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


interface ApiService {

    companion object {
        const val URL_LOGOUT = "bjtt-subway-member/api/member/logout"

        fun get(): ApiService = mApiService
        private const val DEFAULT_TIME_OUT = 60L //超时时间 5s
        private val mOkHttpClient by lazy {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
//                .addInterceptor(addHeaderInterceptor())
                .addInterceptor(addLogInterceptor())
//                    .addInterceptor(GlobalResponseInterceptor())
            HttpsCerUtils.setTrustAllCertificate(builder)
            builder.build()
        }

        private val mGsonConverterFactory by lazy { GsonConverterFactory.create() }

        private val mApiService by lazy {
            Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl("BuildConfig.API_HOST")
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


//
//    /**
//     * 运营动态和回音台
//     */
//    @GET("bjtt-subway-app/api/article/home/listMC")
//    fun getListMC(@Query("columnType") columnType: String?): Call<Result<MutableList<HomeDynamicAndEchoBean>>>
//
//    /**
//     * 获取运营动态/回音台列表 分页
//     */
//    @GET("bjtt-subway-app/api/article/home/listMC")
//    fun getDynamicEchoList(
//        @Query("columnType") columnType: String?,
//        @Query("start") pageIndex: Int,
//        @Query("limit") limit: Int
//    ): Call<Result<MutableList<HomeDynamicAndEchoBean>>>
//
//    /**
//     * 上报文案已读
//     */
//    @POST("bjtt-subway-member/api/status/memberBusinessContentState")
//    fun memberBusinessContentState(@Body params: MutableMap<String, Any?>): Call<Result<String>>
//
//    /**
//     * 健康宝文案查询接口
//     */
//    @GET("bjtt-subway-member/api/status/memberBusinessContent")
//    fun memberBusinessContent(): Call<Result<CodeHealthBean>>
//
//    /**
//     * 健康码状态
//     */
//    @POST("bjtt-subway-member/api/QRCode/healthCodeVerify")
//    fun healthCodeVerify(): Call<Result<HealthInfoStatus>>
}


