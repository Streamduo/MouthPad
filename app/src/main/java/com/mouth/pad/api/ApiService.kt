package com.mouth.pad.api


import com.mouth.pad.bean.*
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
import java.util.concurrent.TimeUnit
import okhttp3.RequestBody


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
     * 查询全部订单（分页）
     */
    @GET("/tOrder/page")
    fun getAllPageOrder(
        @Query("buyer") buyer: String,
        @Query("deptCode") deptCode: String,
        @Query("orderDate") orderDate: String,
        @Query("isApproval") isApproval: String,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<TorderPageListBean>>

    /**
     * 删除订单
     */
    @POST("/tOrder/deleteBatchIds")
    fun deleteOrder(@Body params: RequestBody): Call<Result<String>>

    /**
     * 审核订单
     */
    @POST("/tOrder/approval")
    fun approvalOrder(@Body params: MutableMap<String, Any?>): Call<Result<String>>

    /**
     * 新增-入库信息
     */
    @POST("/tReceipt/insert")
    fun insertStorehouse(@Body params: RequestBody): Call<Result<String>>

    /**
     * 查询全部入库信息
     */
    @GET("/tReceipt/getAll")
    fun getAllStorehouse(): Call<Result<MutableList<TStoreHouseQueryListBean>>>

    /**
     * 查询全部入库信息（分页）
     */
    @GET("/tReceipt/page")
    fun getAllPageStorehouse(
        @Query("applicant") applicant: String,
        @Query("businessType") businessType: String,
        @Query("deptCode") deptCode: String,
        @Query("warehouseCode") warehouseCode: String,
        @Query("isApproval") isApproval: String,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<TStoreHousePageListBean>>

    /**
     * 查询材料库存信息（分页）
     */
    @GET("/tReceiptStore/page")
    fun getAllStuffPageStorehouse(
        @Query("materialCode") materialCode: String?,
        @Query("warehouseCode") warehouseCode: String,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<InventoryQueryPageListBean>>

    /**
     * 删除入库信息
     */
    @POST("/tReceipt/deleteBatchIds")
    fun deleteStorehouse(@Body params: RequestBody): Call<Result<String>>

    /**
     * 审核入库
     */
    @POST("/tReceipt/approval")
    fun approvalStorehouse(@Body params: MutableMap<String, Any?>): Call<Result<String>>

    /**
     * 新增-物资请领信息
     */
    @POST("/tConsume/insert")
    fun insertTMaterialRequisition(@Body params: RequestBody): Call<Result<String>>


    /**
     * 查询全部物资请领信息
     */
    @GET("/tConsume/getAll")
    fun getAllConsume(): Call<Result<MutableList<TMaterialRequisitionQueryListBean>>>

    /**
     * 查询全部物资请领信息（分页）
     */
    @GET("/tConsume/page")
    fun getAllPageConsume(
        @Query("applicant") applicant: String,
        @Query("consumeDate") consumeDate: String,
        @Query("deptCode") deptCode: String,
        @Query("warehouseCode") warehouseCode: String,
        @Query("isApproval") isApproval: String,
        @Query("isConsume") isConsume: String,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<TMaterialPageListBean>>

    /**
     * 消耗查询（分页）
     */
    @GET("/tConsumeList/page")
    fun getAllConsumptionQuery(
        @Query("deptCode") deptCode: String,
        @Query("materialCode") materialCode: String?,
        @Query("warehouseCode") warehouseCode: String,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<ConsumptionQueryPageListBean>>

    /**
     * 删除物资请领信息
     */
    @POST("/tConsume/deleteBatchIds")
    fun deleteConsume(@Body params: RequestBody): Call<Result<String>>

    /**
     * 审核物资请领信息
     */
    @POST("/tConsume/approval")
    fun approvalConsume(@Body params: MutableMap<String, Any?>): Call<Result<String>>

    /**
     * 登录
     */
    @POST("/sysUser/login")
    fun login(@Body params: MutableMap<String, Any?>): Call<Result<LoginUserBean>>

}


