package com.github.poundr.utils

import org.json.JSONObject
import java.util.Date
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object Jwt {
    @OptIn(ExperimentalEncodingApi::class)
    fun isExpired(token: String, serverDate: Date): Boolean {
        val data = token.split(".")[1]
        val decoded = String(Base64.decode(data))
        val json = JSONObject(decoded)
        val exp = json.getLong("exp")
        return exp * 1000 < serverDate.time
    }
}