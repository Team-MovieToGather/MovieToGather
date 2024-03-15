package org.spartaa3.movietogather.infra.map.dto


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
                val id: String,
                val pkey: String,
                val navSeq: String,
                val collectionType: String,
                val name: String,
                val telNo: String?,
                val frontLat: String,
                val frontLon: String,
                val noorLat: String,
                val noorLon: String,
                val upperAddrName: String,
                val middleAddrName: String,
                val lowerAddrName: String,
                val detailAddrName: String?,
                val mlClass: String,
                val firstNo: String,
                val secondNo: String,
                val roadName: String?,
                val firstBuildNo: String?,
                val secondBuildNo: String?,
                val radius: String,
                val bizName: String?,
                val upperBizName: String,
                val middleBizName: String,
                val lowerBizName: String,
                val detailBizName: String,
                val rpFlag: String,
                val parkFlag: String,
                val detailInfoFlag: String,
                val desc: String?,
                val dataKind: String?,
                val zipCode: String?,
                val adminDongCode: String,
                val legalDongCode: String,
                val newAddressList: NewAddressList,
                val evChargers: EvChargers,
            ) {
                data class NewAddressList(
                    val newAddress: List<NewAddress>,
                ) {
                    data class NewAddress(
                        val centerLat: String,
                        val centerLon: String,
                        val frontLat: String,
                        val frontLon: String,
                        val roadName: String?,
                        val bldNo1: String?,
                        val bldNo2: String?,
                        val roadId: String?,
                        val fullAddressRoad: String
                    )
                }

                data class EvChargers(
                    val evCharger: List<EvCharger>
                ) {
                    data class EvCharger(
                        val operatorId: String?,
                        val stationId: String?,
                        val chargeId: String?,
                        val status: String?,
                        val type: String?,
                        val powerType: String?,
                        val operatorName: String?,
                        val chargingDateTime: String?,
                        val updateDateTime: String?,
                        val isFast: String?,
                        val isAvailable: String?
                    )
                }
            }

        }
    }
}
