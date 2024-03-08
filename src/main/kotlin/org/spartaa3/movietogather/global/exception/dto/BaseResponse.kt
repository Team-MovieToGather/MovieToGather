package org.spartaa3.movietogather.global.exception.dto

import org.springframework.http.HttpStatus

data class BaseResponse(
    val status: HttpStatus,
    val code: String,
    val message: String
)