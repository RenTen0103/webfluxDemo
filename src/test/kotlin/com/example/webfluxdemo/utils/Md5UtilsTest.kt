package com.example.webfluxdemo.utils

import org.junit.jupiter.api.Test

object Md5UtilsTest {
    @Test
    fun md5UtilsTest() {
        val s = Md5Utils.encrypt("1234567890")
        println(Md5Utils.match("123456", "E015A07E2602F01D2A31C29C123436BD"))

    }
}