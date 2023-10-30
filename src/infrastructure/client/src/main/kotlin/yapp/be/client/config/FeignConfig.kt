package yapp.be.client.config

import feign.codec.ErrorDecoder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import yapp.be.client.config.decoder.CustomErrorDecoder
import yapp.be.client.ncp.alimtalk.model.NcpKakaoNotifiactionClientProperties

@Configuration
@EnableFeignClients(basePackages = ["yapp.be.client"])
@EnableConfigurationProperties(value = [NcpKakaoNotifiactionClientProperties::class])
class FeignConfig {
    @Bean
    fun errorDecoder(): ErrorDecoder? {
        return CustomErrorDecoder()
    }
}
