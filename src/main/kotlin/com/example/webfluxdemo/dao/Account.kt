package com.example.webfluxdemo.dao

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(value = "accounts")
class Account(@Id var id: String, val username: String, val password: String)