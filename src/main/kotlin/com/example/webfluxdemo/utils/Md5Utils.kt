package com.example.webfluxdemo.utils

import java.security.MessageDigest

object Md5Utils {

    private val SALT = "#webfluxDemo#"

    fun match(raw: String, secret: String) = encrypt(raw) == secret
    fun encrypt(raw: String): String {
        return (raw.toByteArray() + SALT.toByteArray()).md5().toUHexString()
    }

}

@JvmOverloads
fun ByteArray.md5(offset: Int = 0, length: Int = size - offset): ByteArray {
    checkOffsetAndLength(offset, length)
    return MessageDigest.getInstance("MD5").apply { update(this@md5, offset, length) }.digest()
}

fun ByteArray.checkOffsetAndLength(offset: Int, length: Int) {
    require(offset >= 0) { "offset shouldn't be negative: $offset" }
    require(length >= 0) { "length shouldn't be negative: $length" }
    require(offset + length <= this.size) { "offset ($offset) + length ($length) > array.size (${this.size})" }
}

@JvmOverloads
fun ByteArray.toUHexString(
    separator: String = "",
    offset: Int = 0,
    length: Int = this.size - offset
): String {
    this.checkOffsetAndLength(offset, length)
    if (length == 0) {
        return ""
    }
    val lastIndex = offset + length
    return buildString(length * 2) {
        this@toUHexString.forEachIndexed { index, it ->
            if (index in offset until lastIndex) {
                var ret = it.toUByte().toString(16).uppercase()
                if (ret.length == 1) ret = "0$ret"
                append(ret)
                if (index < lastIndex - 1) append(separator)
            }
        }
    }
}

fun String.md5(): ByteArray = toByteArray().md5()
