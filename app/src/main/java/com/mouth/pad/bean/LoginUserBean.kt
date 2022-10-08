package com.mouth.pad.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LoginUserBean(
    var id: String? = "",
    var userId: String? = "",
    //昵称
    var userName: String? = "",
    var password: String? = "",
    var empCode: String? = "",
): Parcelable