package com.github.poundr.utils

import org.json.JSONObject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object Jwt {
    @OptIn(ExperimentalEncodingApi::class)
    fun isExpired(token: String): Boolean {
        val data = token.split(".")[1]
        val decoded = String(Base64.decode(data))
        val json = JSONObject(decoded)
        val exp = json.getLong("exp")
        val now = System.currentTimeMillis() / 1000
        return now > exp
    }
}