package com.mouth.pad

import androidx.lifecycle.MutableLiveData
import com.mouth.pad.api.ApiService
import com.mouth.pad.api.LiveDataCallback
import com.mouth.pad.api.Result
import com.mouth.pad.bean.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


class AllNetViewModel {

    //添加订单
    fun insertOrder(orderInfo: String): MutableLiveData<com.mouth.pad.api.Result<String>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<String>>()
        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), orderInfo)
        ApiService.get().insertOrder(requestBody).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //删除订单
    fun deleteOrder(orderId: String?): MutableLiveData<com.mouth.pad.api.Result<String>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<String>>()
        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "[$orderId]")
        ApiService.get().deleteOrder(requestBody).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //查询全部订单
    fun getAllOrder(): MutableLiveData<com.mouth.pad.api.Result<MutableList<TOrderQueryListBean>>> {
        val liveDatas =
            MutableLiveData<com.mouth.pad.api.Result<MutableList<TOrderQueryListBean>>>()
        ApiService.get().getAllOrder().enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //查询全部订单（分页）
    fun getAllPageOrder(
        buyer: String,
        deptCode: String,
        orderDate: String,
        isApproval: String,
        pageNum: Int,
        pageSize: Int
    ): MutableLiveData<com.mouth.pad.api.Result<TorderPageListBean>> {
        val liveDatas =
            MutableLiveData<com.mouth.pad.api.Result<TorderPageListBean>>()
        ApiService.get().getAllPageOrder(
            buyer, deptCode, orderDate, isApproval, pageNum, pageSize).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //根据材料编号查询-材料基本信息
    fun selectByMaterialCode(materialCode: String?): MutableLiveData<com.mouth.pad.api.Result<TMaterial>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<TMaterial>>()
        ApiService.get().selectByMaterialCode(materialCode).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //审核订单
    fun approvalOrder(
        id: String?,
        reviewer: String?
    ): MutableLiveData<com.mouth.pad.api.Result<String>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<String>>()
        val params = hashMapOf<String, Any?>()
        params["id"] = id
        params["reviewer"] = reviewer
        ApiService.get().approvalOrder(params).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //添加入库
    fun insertStorehouse(orderInfo: String): MutableLiveData<com.mouth.pad.api.Result<String>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<String>>()
        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), orderInfo)
        ApiService.get().insertStorehouse(requestBody).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //删除入库信息
    fun deleteStorehouse(orderId: String?): MutableLiveData<com.mouth.pad.api.Result<String>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<String>>()
        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "[$orderId]")
        ApiService.get().deleteStorehouse(requestBody).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //查询全部入库信息
    fun getAllStorehouse(): MutableLiveData<com.mouth.pad.api.Result<MutableList<TStoreHouseQueryListBean>>> {
        val liveDatas =
            MutableLiveData<com.mouth.pad.api.Result<MutableList<TStoreHouseQueryListBean>>>()
        ApiService.get().getAllStorehouse().enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //查询全部入库信息（分页）
    fun getAllPageStorehouse(
        applicant: String,
        businessType: String,
        deptCode: String,
        warehouseCode: String,
        isApproval: String,
        pageNum: Int,
        pageSize: Int
    ): MutableLiveData<com.mouth.pad.api.Result<TStoreHousePageListBean>> {
        val liveDatas =
            MutableLiveData<com.mouth.pad.api.Result<TStoreHousePageListBean>>()
        ApiService.get().getAllPageStorehouse(
            applicant, businessType, deptCode,
            warehouseCode, isApproval, pageNum, pageSize
        ).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //审核入库
    fun approvalStorehouse(
        id: String?,
        reviewer: String?
    ): MutableLiveData<com.mouth.pad.api.Result<String>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<String>>()
        val params = hashMapOf<String, Any?>()
        params["id"] = id
        params["reviewer"] = reviewer
        ApiService.get().approvalStorehouse(params).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //新增-物资请领信息
    fun insertTMaterialRequisition(orderInfo: String): MutableLiveData<com.mouth.pad.api.Result<String>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<String>>()
        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), orderInfo)
        ApiService.get().insertTMaterialRequisition(requestBody)
            .enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //查询全部物资请领信息
    fun getAllConsume(): MutableLiveData<com.mouth.pad.api.Result<MutableList<TMaterialRequisitionQueryListBean>>> {
        val liveDatas =
            MutableLiveData<com.mouth.pad.api.Result<MutableList<TMaterialRequisitionQueryListBean>>>()
        ApiService.get().getAllConsume().enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //查询全部物资请领信息（分页）
    fun getAllPageConsume(
        applicant: String,
        consumeDate: String,
        deptCode: String,
        warehouseCode: String,
        isApproval: String,
        isConsume: String,
        pageNum: Int,
        pageSize: Int
    ): MutableLiveData<com.mouth.pad.api.Result<TMaterialPageListBean>> {
        val liveDatas =
            MutableLiveData<com.mouth.pad.api.Result<TMaterialPageListBean>>()
        ApiService.get().getAllPageConsume(
            applicant, consumeDate, deptCode,
            warehouseCode, isApproval, isConsume, pageNum, pageSize
        ).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //删除入库信息
    fun deleteConsume(orderId: String?): MutableLiveData<com.mouth.pad.api.Result<String>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<String>>()
        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "[$orderId]")
        ApiService.get().deleteConsume(requestBody).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //审核入库
    fun approvalConsume(
        id: String?,
        reviewer: String?
    ): MutableLiveData<com.mouth.pad.api.Result<String>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<String>>()
        val params = hashMapOf<String, Any?>()
        params["id"] = id
        params["reviewer"] = reviewer
        ApiService.get().approvalConsume(params).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

}