package dev.ygordoring.chat.thelog.business.vo

import androidx.annotation.DrawableRes
import dev.ygordoring.chat.thelog.R
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserVO(
    var id: String = "",
    val name: String,
    val email: String,
    val password: String,
    @SerializedName("profile_picture") var profilePicture: String?,
    var mine: Boolean = true
) : Serializable {
    constructor(name: String, profilePicture: String) : this(
        "",
        name,
        "",
        "",
        profilePicture,
        false
    )

    constructor(name: String, email: String, password: String) : this(
        "",
        name,
        email,
        password,
        null
    )

    @DrawableRes
    fun getAvatar(): Int {
        if (mine) {
            return R.drawable.avatar
        } else {
            return R.drawable.avatar2
        }
    }
}