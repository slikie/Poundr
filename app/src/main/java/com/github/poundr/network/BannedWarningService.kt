package com.github.poundr.network

import com.github.poundr.model.BannedWarningList
import retrofit2.http.PUT

interface BannedWarningService {
    @PUT("v1/warnings")
    suspend fun putV1Warnings(): Unit

    @PUT("v2/warnings")
    suspend fun getV2Warnings(): BannedWarningList
}