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

//        /**
//         * 设置头
//         */
//        private fun addHeaderInterceptor(): Interceptor {
//
//            return Interceptor { chain ->
//                val METHOD_GET = "GET"
//                val METHOD_POST = "POST"
//                val HEADER_KEY_USER_AGENT = "userAgentBody"
//
//                val originalRequest = chain.request()
//                val requestBuilder = originalRequest.newBuilder()
//                val url = originalRequest.url().url().toString()
//                val tm =
//                    MyApplication.appContext.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//                val loginUserBean =
//                    SpUtil.decodeParcelable(Const.LOGIN_USER_BEAN, LoginUserBean::class.java)
//                val userId = if (loginUserBean?.memberId != null) {
//                    loginUserBean.memberId
//                } else {
//                    ""
//                }
//                val token = if (loginUserBean?.token != null) {
//                    loginUserBean.token
//                } else {
//                    ""
//                }
//                val callTime = TimeUtils.getSecondTimestampTwo().toString()
//                val androidId: String = Settings.System.getString(
//                    MyApplication.appContext.contentResolver,
//                    Settings.Secure.ANDROID_ID
//                )
//                val appVersionNo = AppUtils.getVerName(MyApplication.appContext)
//                val phoneType = when (tm.networkOperatorName) {
//                    YD -> "CMCC"
//                    LT -> "CUCC"
//                    DX -> "CTCC"
//                    else -> {
//                        ""
//                    }
//                }
//
//                val headerMap = linkedMapOf<String, String>()
//                headerMap["Accept-APIVersion"] = "1.0"
//                headerMap["appVersionNo"] = appVersionNo
//                headerMap["callTime"] = callTime
//                headerMap["lastApnid"] = androidId
//                headerMap["mobileBrand"] = Build.MANUFACTURER
//                headerMap["mobileStandard"] = phoneType.toString()
//                headerMap["platformType"] = "android"
//                headerMap["platformVersion"] = Build.VERSION.SDK_INT.toString()
//                headerMap["sign"] = "p@ssw0rd"
//                if (token != null && token != "") {
//                    headerMap["token"] = token
//                } else {
//                    headerMap["token"] = ""
//                }
//
//                //判断GET POST
//                when (originalRequest.method()) {
//                    //分别将参数
//                    METHOD_GET -> {
//                        val strBuilder = StringBuilder()
//                        val httpUrl = originalRequest.url().newBuilder().build()
//                        val queryParameterNames = httpUrl.queryParameterNames()
//                        if (!queryParameterNames.isNullOrEmpty()) {
//                            val last = queryParameterNames.last()
//                            for (key in queryParameterNames) {
//                                val value = httpUrl.queryParameter(key)
//                                if (key == last) {
//                                    strBuilder.append(value)
//                                } else {
//                                    strBuilder.append("$value,")
//                                }
//                            }
//                            Logger.e("get===${strBuilder.toString()}")
//                            headerMap[HEADER_KEY_USER_AGENT] =
//                                StringUtil.encodeHeadInfo(strBuilder.toString())
//                        } else {
//                            headerMap[HEADER_KEY_USER_AGENT] = ""
//                        }
//                    }
//                    METHOD_POST -> {
//                        val strBuilder = StringBuilder()
//                        val buffer = okio.Buffer()//声明一个buffer
//                        val requestBody = originalRequest.body()
//                        requestBody?.writeTo(buffer)//使用requestBody的writeTo函数把内容写到buffer中
//                        val contentType = requestBody?.contentType()
//                        val charset: Charset = contentType?.charset(StandardCharsets.UTF_8)
//                            ?: StandardCharsets.UTF_8 //这里主要是获取字符串编码
//                        val requestContent =
//                            buffer.readString(charset)//从buffer中获取requestBody里面的内容，返回一个String
//
//                        if (requestContent.isEmpty() || url == UP_LOAD) {
//                            headerMap[HEADER_KEY_USER_AGENT] = ""
//                        } else {
//                            val bodyMap: Map<String, Any> = Gson().fromJson(
//                                requestContent,
//                                object : TypeToken<HashMap<String?, Any?>?>() {}.type
//                            )
//                            val last = bodyMap.keys.last()
//                            for (key in bodyMap.keys) {
//                                val value = bodyMap[key]
//                                if (value.toString().isNotEmpty() && value.toString() != ",") {
//                                    if (key == last) {
//                                        strBuilder.append(value)
//                                    } else {
//                                        strBuilder.append("$value,")
//                                    }
//                                }
//                            }
//                            Logger.e("post===${strBuilder.toString()}")
//                            headerMap[HEADER_KEY_USER_AGENT] =
//                                StringUtil.encodeHeadInfo(strBuilder.toString())
//                        }
//                    }
//                }
//
//                //用户ID
//                if (userId != null && userId != "") {
//                    headerMap["userId"] = userId
//                } else {
//                    headerMap["userId"] = ""
//                }
//
//                //获取最后一个key
//                val lastKey = headerMap.keys.toList().last()
//                val stringBuilder = StringBuilder()
//                for (key in headerMap.keys) {
//                    if (key == lastKey) {
//                        stringBuilder.append(key + "=" + headerMap[key])
//                    } else {
//                        stringBuilder.append(key + "=" + headerMap[key] + "&")
//                    }
//                    //过滤sign
//                    headerMap[key]?.let {
//                        if (key != "sign") {
//                            requestBuilder.header(key, it)
//                        }
//                    }
//                }
//                val signToString = stringBuilder.toString()
//                val mobilePrivateKey = EncryptionTool.getKey(0, MyApplication.appContext)
//                val decrypt3DES = mobilePrivateKey?.let {
//                    EncryptionTool.decrypt3DES(
//                        it,
//                        MyApplication.sha1.toString()
//                    )
//                }
//                val sign = RsaSha256SignUtils.sha256RsaSignString(
//                    signToString,
//                    decrypt3DES
//                )
//                requestBuilder.header("sign", sign)  // 校验码
//                    .method(originalRequest.method(), originalRequest.body())
//                val request = requestBuilder.build()
//                chain.proceed(request)
//            }
//        }

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


