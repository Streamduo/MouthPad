package com.mouth.pad.base

import android.Manifest.permission.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.bmncc.pis.ylct.utils.*
import com.mouth.pad.R
import com.mouth.pad.utils.ActivityManager
import com.mouth.pad.utils.AppUtils
import com.mouth.pad.utils.Logger
import com.mouth.pad.utils.StatusBarUtil
import java.util.*


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (isValidity() && getActivityName(componentName.shortClassName) == "LoginActivity") {
//            finish()
//            return
//        }
//        onCreateTime = System.currentTimeMillis()
//        ActivityManager.get().addActivity(this)
//        setStatusBar()
//        setContentView(layoutId())
//        if (isNeedLogin()) {
//            needLogin(this)
//            if (!isValidity()) {
//                return
//            }
//        }
        initData()
        initView()
        start()
        Logger.e("lastActName=" + getActivityName(ActivityManager.get().getLastActivityName()) +
                "currentActivity=" + getActivityName(componentName.shortClassName))
    }

    override fun onStop() {
        super.onStop()
    }

    /**
     *  加载布局
     */
    abstract fun layoutId(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 开始请求
     */
    abstract fun start()

    fun getActivityName(activityName: String): String {
        val split = activityName.split(".")
        return if (!split.isNullOrEmpty()) {
            split.last()
        } else {
            activityName.replace(".", "")
        }
    }


    /**
     * 打开软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.get().removeActivity(this);
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun showToast(s: String?) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(s: String?) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    protected fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isUseFullScreenMode()) {
                StatusBarUtil.transparencyBar(this)
            } else {
                StatusBarUtil.setStatusBarColor(this, getStatusBarColor())
            }
            if (isUseBlackFontWithStatusBar()) {
                StatusBarUtil.setLightStatusBar(this, true, isUseFullScreenMode())
            }
        }
    }

    /**
     * 是否设置成透明状态栏，即就是全屏模式
     */
    protected open fun isUseFullScreenMode(): Boolean {
        return false
    }

    /**
     * 更改状态栏颜色，只有非全屏模式下有效
     */
    protected open fun getStatusBarColor(): Int {
        return R.color.color_white
    }

    /**
     * 是否改变状态栏文字颜色为黑色，默认为黑色
     */
    protected open fun isUseBlackFontWithStatusBar(): Boolean {
        return true
    }

    protected open fun isNeedLogin(): Boolean {
        return false
    }

}
