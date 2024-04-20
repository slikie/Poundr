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
            401 -> GrindrUnknownException()
            else -> e
        }
    }
}

class GrindrNoInternetException : Exception("No internet connection")
class GrindrUnknownException : Exception("Unknown error")