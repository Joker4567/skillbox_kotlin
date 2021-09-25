package com.prognozrnm.utils.platform

import com.google.gson.Gson
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

class ErrorHandler(
    private val networkHandler: NetworkHandler,
    private val gson: Gson
) {

    fun proceedException(exception: Throwable): Failure {
        when {
            withoutNetworkConnection() -> {
                return Failure.NetworkConnection
            }

            exception is HttpException -> {
                try {
                    val error = gson.fromJson(
                        exception.response()?.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    return when (error.error) {
                        "UnknownError" -> Failure.UnknownError
                        else -> Failure.CommonError
                    }
                } catch (e: Exception) {
                    Failure.ServerError
                }
            }

            exception is SocketTimeoutException -> {
                return Failure.ServerError
            }

            exception is CancellationException -> {
                return Failure.CommonError
            }

            exception is KotlinNullPointerException -> {
                return Failure.CommonError
            }

            else -> Failure.CommonError
        }

        return Failure.CommonError
    }

    fun withoutNetworkConnection() = !networkHandler.isConnected
}