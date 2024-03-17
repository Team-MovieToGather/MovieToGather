package org.spartaa3.movietogather.infra.map.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.spartaa3.movietogather.infra.map.dto.KakaoMapResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class KakaoMapService {
    private val restClient: RestClient = RestClient.create()
    val objectMapper =
        jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun searchCoordinate(keyword: String): Map<String, String> {
        val result = searchAddress(keyword)
        //TODO(프론트에서 클릭한 데이터를 기반으로 사용해야 하기 때문에 후에 수정이 필요)
        val x = result[0].x
        val y = result[0].y
        val coordinate = mapOf("x" to x, "y" to y)
        return coordinate
    }

    val env = dotenv {
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }

    fun searchAddress(keyword: String): List<KakaoMapResponse.Documents> {
        val dotenv = env["KAKAO_MAP_AK"]
        val result = restClient.get()
            .uri("https://dapi.kakao.com/v2/local/search/address.json?query=${keyword}")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "KakaoAK $dotenv")
            .retrieve()
            .body(String::class.java) ?: throw Exception("No result")
        val json = objectMapper.readValue(result, KakaoMapResponse::class.java)
        val addressList = json.documents
        return addressList
    }

}