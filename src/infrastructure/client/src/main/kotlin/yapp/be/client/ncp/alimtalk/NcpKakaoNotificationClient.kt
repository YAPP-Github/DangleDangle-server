package yapp.be.client.ncp.alimtalk

import feign.Request
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import yapp.be.client.ncp.alimtalk.model.NcpKakaoNotifiactionClientProperties
import yapp.be.client.ncp.alimtalk.model.NcpKakaoNotificationModel
import yapp.be.domain.common.model.AlimtalkMessageTemplate
import yapp.be.domain.common.port.KakaoNotificationHandler
import yapp.be.util.HmacSha256
import java.net.URLEncoder
import java.time.Instant

@Component
class NcpKakaoNotificationClient(
    private val api: NcpKakaoNotificationApi,
    private val properties: NcpKakaoNotifiactionClientProperties,
) : KakaoNotificationHandler {

    private val logger = KotlinLogging.logger { }
    override fun request(
        variables: Map<String, Map<String, String>>,
        template: AlimtalkMessageTemplate,
    ) {
        runCatching {
            val timeStamp = Instant.now().toEpochMilli().toString()
            val serviceId = URLEncoder.encode(properties.serviceId, Charsets.UTF_8)
            val accessKey = properties.accessKey
            val signature = HmacSha256.sign(
                secretKey = properties.secretKey,
                str = "${Request.HttpMethod.POST} /alimtalk/v2/services/$serviceId/messages\n$timeStamp\n$accessKey"
            )
            val message = variables.map { (userIdentifier, variable) ->
                NcpKakaoNotificationModel.Request.Message(
                    to = userIdentifier,
                    title = template.title,
                    content = template.getMessage(variable),
                    buttons = listOf(
                        NcpKakaoNotificationModel.Request.Message.Button(
                            type = "WL",
                            name = "확인하러가기",
                            linkMobile = "https://dangle.co.kr/",
                            linkPc = "https://dangle.co.kr/",
                        )
                    )
                )
            }

            val response = api.request(
                timeStamp = timeStamp,
                subAccountAccessKey = accessKey,
                apiGatewaySignature = signature,
                serviceId = serviceId,
                request = NcpKakaoNotificationModel.Request(
                    plusFriendId = "@dangledangle",
                    templateCode = template.templateCode,
                    messages = message
                )
            )

            println(response)
        }
            .onSuccess { logger.info { "[알림톡][발송][성공] (template ${template.templateCode})" } }
            .onFailure { logger.info { "[알림톡][발송][실패] (cause=${it.stackTraceToString()}}" } }
    }
}
