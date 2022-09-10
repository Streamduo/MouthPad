package com.mouth.pad.net

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import com.mouth.pad.utils.Logger


/**
 *

 * @ClassName:      NetReceiver
 * @Description:    网络状态监听广播
 * @Author:         JiangMin
 * @CreateDate:     2021/2/9 15:13
 * @UpdateUser:     更新者
 * @UpdateDate:     2021/2/9 15:13
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class NetReceiver : BroadcastReceiver() {

    /**
     * 获取连接类型
     *
     * @param type
     * @return
     */
    private fun getConnectionType(type: Int): String {
        var connType = ""
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "3G网络数据"
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络"
        }
        return connType
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        intent?.let {
            Logger.i("网络状态发生变化")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
                if (ConnectivityManager.CONNECTIVITY_ACTION == it.action) {
                    //获取联网状态的NetworkInfo对象
                    val info: NetworkInfo? = it.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO)
                    if (info != null) {
                        //如果当前的网络连接成功并且网络连接可用
                        if (NetworkInfo.State.CONNECTED == info.state && info.isAvailable) {
                            if (info.type == ConnectivityManager.TYPE_WIFI || info.type == ConnectivityManager.TYPE_MOBILE) {
                                Logger.i("Net state "+getConnectionType(info.type).toString() + "连上")
//                                LiveEventBus.get(Const.NET_STATE_CHANGE).post(NetStateData(getConnectionType(info.type), true))
                            }
                        } else {
                            Logger.i("Net state "+getConnectionType(info.type).toString() + "断开")
//                            LiveEventBus.get(Const.NET_STATE_CHANGE).post(NetStateData(getConnectionType(info.type), false))
                        }
                    }
                }
            }
        }
    }
}