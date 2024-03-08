package org.spartaa3.movietogather.global.exception

import org.spartaa3.movietogather.global.exception.dto.BaseResponseCode

class BaseException(val baseResponseCode: BaseResponseCode) : RuntimeException()