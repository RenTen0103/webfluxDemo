package com.example.webfluxdemo.service

import com.example.webfluxdemo.entity.dao.Account

interface AccountService {
    suspend fun getUserList(): List<Account>

}