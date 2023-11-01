package yapp.be.client.ncp.alimtalk.model

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "ncp-kakao-notification-client")
data class NcpKakaoNotifiactionClientProperties(
    val accessKey: String,
    val secretKey: String,
    val serviceId: String,
)
