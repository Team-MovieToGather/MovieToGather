package org.spartaa3.movietogather.global.exception.dto

import org.springframework.http.HttpStatus

enum class BaseResponseCode(val status: HttpStatus, val code: String, val message: String) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "err-001", "잘못된 요청입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "err-002", "잘못된 비밀번호입니다."),
    INVALID_REVIEW(HttpStatus.NOT_FOUND, "err-003", "리뷰를 찾을 수 없습니다.."),
    INVALID_MEETING(HttpStatus.NOT_FOUND, "err-004", "모임을 찾을 수 없습니다."),
    INVALID_COMMENT(HttpStatus.NOT_FOUND, "err-005", "댓글을 찾을 수 없습니다."),
    INVALID_TITLE(HttpStatus.NOT_FOUND, "err-006", "영화를 찾을 수 없습니다.")

}