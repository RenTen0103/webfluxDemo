package com.example.webfluxdemo.entity.dao

import com.example.webfluxdemo.common.Role
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(value = "accounts")
class Account(@Id var id: String, val username: String, val password: String, val role: Role)