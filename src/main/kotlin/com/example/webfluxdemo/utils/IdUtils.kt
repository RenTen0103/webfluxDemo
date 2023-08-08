package com.example.webfluxdemo.utils

import com.github.yitter.contract.IdGeneratorOptions
import com.github.yitter.idgen.YitIdHelper

object IdUtils {
    init {
        YitIdHelper.setIdGenerator(IdGeneratorOptions())
    }

    fun nextID(): String {
        return YitIdHelper.nextId().toString()
    }
}