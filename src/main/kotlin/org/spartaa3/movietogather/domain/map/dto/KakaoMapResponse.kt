package org.spartaa3.movietogather.domain.map.dto

data class KakaoMapResponse(
    val documents: List<Documents>,
) {
    data class Documents(
        val x: String,
        val y: String
    )

}
