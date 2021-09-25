package com.prognozrnm.utils.platform

/**
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {

    object NetworkConnection : Failure()
    object ServerError : Failure()
    object CommonError : Failure()
    object UnknownError : Failure()
}
