package com.mouth.pad

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher

import com.bmncc.pis.ylct.utils.*
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.LoginUserBean
import com.mouth.pad.utils.Const
import com.mouth.pad.utils.SpUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import kotlin.random.Random

//登录
class LoginActivity : BaseActivity() {

    var isClose: Boolean = true

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {

    }

    override fun initView() {
        iv_back.setOnSingleClickListener {
            onBackPressed()
        }


        iv_eyes.setOnSingleClickListener {
            val passWord = ed_password.text.toString().trim()
            if (passWord.isNotEmpty()) {
                if (isClose) {
                    isClose = false
                    iv_eyes.setImageResource(R.mipmap.icon_open_eyes)
                    ed_password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                } else {
                    isClose = true
                    iv_eyes.setImageResource(R.mipmap.icon_close_eyes)
                    ed_password.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                ed_password.setSelection(passWord.length)
            }
        }

        ed_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str: String = s.toString().trim()
                str.apply {
                    //判断输入的内容是否为空
                    if (isEmpty()) {
                        te_login.isEnabled = false
                        return
                    }
                    //判断是否符合手机号格式
                    if (!InspectionFormatUtils.regexPhone(this)) {
                        te_login.isEnabled = false
                        return
                    }
                }


                val passWord = ed_password.text.toString().trim()
                passWord.apply {
                    //判断密码是否为空
                    if (isEmpty()) {
                        te_login.isEnabled = false
                        return
                    }
                    //判断位数是否合格
                    if (length < Const.MINIMUM_PASSWORD_SIZE || length > Const.MAX_PASSWORD_SIZE) {
                        te_login.isEnabled = false
                        return
                    }
                }
                te_login.isEnabled = true
            }
        })

        ed_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val phoneNum = ed_phone.text.toString().trim()
                phoneNum.apply {
                    //判断输入的内容是否为空
                    if (isEmpty()) {
                        te_login.isEnabled = false
                        return
                    }
                    //判断是否符合手机号格式
                    if (!InspectionFormatUtils.regexPhone(this)) {
                        te_login.isEnabled = false
                        return
                    }
                }

                val str: String = s.toString().trim()
                str.apply {
                    //判断密码是否为空
                    if (isEmpty()) {
                        te_login.isEnabled = false
                        return
                    }
                    //判断位数是否合格
                    if (length < Const.MINIMUM_PASSWORD_SIZE || length > Const.MAX_PASSWORD_SIZE) {
                        te_login.isEnabled = false
                        return
                    }
                }
                //判断输入的内容是否为空
                te_login.isEnabled = true
            }
        })


        te_login.setOnSingleClickListener {
            KeyboardUtils.hideKeyboard(te_login)
            val phoneNum = ed_phone.text.toString().trim()
            var passWord = ed_password.text.toString().trim()
            phoneNum.apply {
                //判断输入的内容是否为空
                if (isEmpty()) {
                    showToast(getString(R.string.phone_null))
                    return@setOnSingleClickListener
                }
                //判断是否符合手机号格式
                if (!InspectionFormatUtils.regexPhone(this)) {
                    showToast(getString(R.string.phone_fail))
                    return@setOnSingleClickListener
                }
            }

            passWord.apply {
                //判断密码是否为空
                if (isEmpty()) {
                    showToast(getString(R.string.password_null))
                    return@setOnSingleClickListener
                }
            }

            te_login.isEnabled = false
            passwordLogin(phoneNum, passWord)
        }
    }


    //密码登录
    private fun passwordLogin(mobile: String, userPwd: String) {
        val nickNum = Random(10).nextInt(10)
        val loginUserBean = LoginUserBean("Admin$nickNum", userPwd, mobile)
        //保存登录信息
        SpUtil.encode(Const.LOGIN_USER_BEAN, loginUserBean)
        showLongToast("登录成功")
        finish()
    }

    override fun start() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun isUseBlackFontWithStatusBar(): Boolean {
        return false
    }

    override fun getStatusBarColor(): Int {
        return R.color.color_1876FF
    }


    companion object {
        //判断是否需要登陆
        fun needLogin(context: Context) {
            if (!isValidity()) {
                toLogin(context)
            }
        }

        //判断用户是否有效
        fun isValidity(): Boolean {
            val loginUserBean =
                SpUtil.decodeParcelable(Const.LOGIN_USER_BEAN, LoginUserBean::class.java)
            return loginUserBean?.phoneNum != null
        }

        fun toLogin(context: Context?) {
            context?.let { con ->
                con.startActivity(Intent(con, LoginActivity::class.java).apply {
                })
                (con as Activity).finish()
            }
        }
    }
}