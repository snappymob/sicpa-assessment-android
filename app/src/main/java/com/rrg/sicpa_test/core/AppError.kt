package com.rrg.sicpa_test.core

import java.lang.RuntimeException

class AppError(
    val code: Code,
    val serverErrorMessage: String? = null, // This is the custom server error messages.
    underlyingError: Throwable? = null
) : RuntimeException() {
    enum class Code {
        InvalidData,
        DataSerialization,
        Unauthorised,
        Network,
        BadRequest,
        ServerError,
    }
}