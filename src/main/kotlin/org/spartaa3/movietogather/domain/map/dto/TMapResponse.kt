package org.spartaa3.movietogather.domain.map.dto


data class TMapResponse(
    val searchPoiInfo: SearchPoiInfo
) {
    data class SearchPoiInfo(
        val totalCount: String,
        val count: String,
        val page: String,
        val pois: Pois
    ) {
        data class Pois(
            val poi: List<Poi>
        ) {
            data class Poi(
                val frontLat: String,
                val frontLon: String,
            )

        }
    }
}
