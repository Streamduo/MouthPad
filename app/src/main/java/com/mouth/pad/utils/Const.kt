package com.mouth.pad.utils


/**
 *

 * @ClassName:      Conts
 * @Description:     java类作用描述
 * @Author:         Fuduo
 * @CreateDate:     2020/12/24 16:43
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/12/24 16:43
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object Const {


    //密码最小位数
    const val MINIMUM_PASSWORD_SIZE = 8

    //密码最大位数
    const val MAX_PASSWORD_SIZE = 20

    //验证码最大倒计时数
    const val MAX_VERIFICATION_CODE_SIZE = 60

    //验证码循环位数
    const val CODE_SIZE = MAX_VERIFICATION_CODE_SIZE + 1

    //用户登录bean
    const val LOGIN_USER_BEAN = "loginUserBean"

}