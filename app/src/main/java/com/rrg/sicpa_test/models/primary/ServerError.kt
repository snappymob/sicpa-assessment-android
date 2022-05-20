package com.rrg.sicpa_test.models.primary

data class ServerError(
    val fault: FaultString
) {
    data class FaultString(
        val faultString: String,
        val detail: ErrorCode
    ) {
        data class ErrorCode(
            val errorCode: String
        )
    }
}
