package com.mouth.pad.api

/**
 *

 * @ClassName:      Result
 * @Description:     java类作用描述
 * @Author:         JiangMin
 * @CreateDate:     2020/12/9 18:33
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/12/9 18:33
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */

class Result<T>(
    var code: Int = CODE_CREATED,
    var msg: String? = null,
    var data: T?
) {
    var httpCode = 200
    var throwable: Throwable? = null
    fun isOk() = code == 200

    override fun toString(): String {
        return "retCode=$code httpCode=$httpCode message=$msg throwable=${throwable?.message}"
    }

    companion object {
        const val CODE_CREATED = -201
        const val CODE_UNAUTHORIZED = -401

        fun <T> throwable(throwable: Throwable? = null, message: String): Result<T> {
            val result = Result<T>(CODE_CREATED, null, null)
            result.httpCode = CODE_CREATED
            result.throwable = throwable
            result.msg = message
            return result
        }

        fun <T> throwable(httpCode: Int, message: String): Result<T> {
            val result = Result<T>(CODE_UNAUTHORIZED, null, null)
            result.httpCode = httpCode
            result.msg = message
            return result
        }
    }
}
