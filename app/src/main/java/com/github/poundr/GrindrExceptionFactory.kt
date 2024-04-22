package com.github.poundr

import retrofit2.HttpException
import java.net.UnknownHostException

object GrindrExceptionFactory {
    fun get(t: Throwable): Throwable {
        return when {
            t is UnknownHostException -> GrindrNoInternetException()
            t is HttpException -> getHttpException(t)
            else -> t
        }
    }

    private fun getHttpException(e: HttpException): Throwable {
        return when (e.code()) {
            400 -> IllegalArgumentException(e.message()) // TODO (When geohash is invalid, error body is "Invalid geohash")
            401 -> GrindrUnknownException()
            else -> e
        }
    }

    private fun getHttp400Exception(e: HttpException): Throwable {
        return when {
            e.message() == "Invalid geohash" -> GrindrInvalidGeohashException()
            else -> e
        }
    }
}

class GrindrNoInternetException : Exception("No internet connection")
class GrindrUnknownException : Exception("Unknown error")
class GrindrInvalidGeohashException : Exception("Invalid geohash")