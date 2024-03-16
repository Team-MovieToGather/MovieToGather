//package org.spartaa3.movietogather.infra.map.controller
//
//import org.spartaa3.movietogather.infra.map.service.KakaoMapService
//import org.spartaa3.movietogather.infra.map.service.TMapService
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.RestController
//
//@RequestMapping("/maps")
//@RestController
//class MapController(
//    private val kakaoMapService: KakaoMapService,
//    private val tMapService: TMapService,
//
//    ) {
//    @GetMapping("/coordinates")
//    fun getCoordinates(
//        @RequestParam(name = "keyword") keyword: String
//    ): Map<String, String> {
//        return kotlin.runCatching { kakaoMapService.searchCoordinate(keyword) }
//            .getOrElse {
//                tMapService.searchCoordinate(keyword).ifEmpty { throw Exception("No result") }
//            }
//    }
//
//    @GetMapping("/address")
//    fun getAddress(
//        @RequestParam(name = "keyword") keyword: String
//    ): Any {
//        return kotlin.runCatching { kakaoMapService.searchAddress(keyword) }
//            .getOrElse {
//                tMapService.searchAddress(keyword).ifEmpty { throw Exception("No result") }
//            }
//    }
//}