package org.spartaa3.movietogather.infra.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket  // websocket 활성화
class WebSockConfig(
    private val webSocketHandler: WebSocketHandler
) : WebSocketConfigurer {
    // websocket 연결
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        // endpoint 설정
        // ws://localhost:8080/ws/api/meetings/{meetingId}/chat 으로 요청이 들어오면 websocket 통신을 진행한다
        // setAllowedOrigins("*")는 모든 ip 에서 접속 가능하도록 해줌(일정버전에서는 사용불가)
        // 그땐 대신 .setAllowedOriginPatterns("*") 를 사용할 수 있다
        registry.addHandler(webSocketHandler, "/ws/api/meetings/{meetingId}/chat").setAllowedOrigins("*")
    }
}