package com.mouth.pad

import androidx.lifecycle.MutableLiveData
import com.mouth.pad.api.ApiService
import com.mouth.pad.api.LiveDataCallback
import com.mouth.pad.bean.TMaterial
import com.mouth.pad.bean.TOrderQueryListBean
import okhttp3.MediaType
import okhttp3.RequestBody




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
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<MutableList<TOrderQueryListBean>>>()
        ApiService.get().getAllOrder().enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }

    //根据材料编号查询-材料基本信息
    fun selectByMaterialCode(materialCode: String?): MutableLiveData<com.mouth.pad.api.Result<TMaterial>> {
        val liveDatas = MutableLiveData<com.mouth.pad.api.Result<TMaterial>>()
        ApiService.get().selectByMaterialCode(materialCode).enqueue(LiveDataCallback(liveDatas))
        return liveDatas
    }
}