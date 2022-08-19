package dev.ygordoring.chat.thelog.utils

import android.util.Patterns
import java.math.BigInteger
import java.security.MessageDigest

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty()
            && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.toMd5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}