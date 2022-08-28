package com.bmncc.pis.ylct.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 *

 * @ClassName:      KeyboardUtils
 * @Description:     java类作用描述
 * @Author:         Fuduo
 * @CreateDate:     2021/3/1 19:55
 * @UpdateUser:     更新者
 * @UpdateDate:     2021/3/1 19:55
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object KeyboardUtils {
     fun showKeyboard(view: View) {
        val imm: InputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        view.requestFocus()
        imm.showSoftInput(view, 0)
    }

     fun hideKeyboard(view: View) {
        val imm: InputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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
}