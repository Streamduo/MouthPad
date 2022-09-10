package com.mouth.pad.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LoginUserBean(
    //昵称
    var nickname: String? = "",
    var password: String? = "",
    var phoneNum: String? = "",
): Parcelable