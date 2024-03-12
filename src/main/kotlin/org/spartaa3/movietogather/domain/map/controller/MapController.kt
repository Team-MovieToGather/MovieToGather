package org.spartaa3.movietogather.domain.map.controller

import org.spartaa3.movietogather.domain.map.service.KakaoMapService
import org.spartaa3.movietogather.domain.map.service.TMapService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MapController(
    private val kakaoMapService: KakaoMapService,
    private val tMapService: TMapService
) {
    @GetMapping("/maps/kakao/coordinates")
    fun getCoordinates(
        @RequestParam(name = "keyword") keyword: String
    ): Map<String, String> {
        return kakaoMapService.searchCoordinate(keyword)
    }

    @GetMapping("/maps/tmap/coordinates")
    fun getCoordinatesTMap(
        @RequestParam(name = "keyword") keyword: String
    ): Map<String, String> {
        return tMapService.searchCoordinate(keyword)

    }
}