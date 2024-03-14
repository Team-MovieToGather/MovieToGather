package org.spartaa3.movietogather.infra.map.dto

data class KakaoMapResponse(
    val documents: List<Documents>,
) {
    data class Documents(
        val address: Address,
        val roadAddress: RoadAddress?,
        val address_name: String,
        val address_type: String,
        val x: String,
        val y: String
    ) {
        data class Address(
            val address_name: String,
            val b_code: String,
            val h_code: String,
            val main_address_no: String,
            val mountain_yn: String,
            val region_1depth_name: String,
            val region_2depth_name: String,
            val region_3depth_h_name: String,
            val region_3depth_name: String,
            val sub_address_no: String,
            val x: String,
            val y: String

        )

        data class RoadAddress(
            val address_name: String,
            val building_name: String,
            val main_building_no: String,
            val region_1depth_name: String,
            val region_2depth_name: String,
            val region_3depth_name: String,
            val road_name: String,
            val sub_building_no: String,
            val underground_yn: String,
            val x: String,
            val y: String,
            val zone_no: String
        )
    }

}
