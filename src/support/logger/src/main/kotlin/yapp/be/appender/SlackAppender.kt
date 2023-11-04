package yapp.be.appender

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.LayoutBase
import ch.qos.logback.core.UnsynchronizedAppenderBase
import yapp.be.client.SlackLogDeliveryClient
import yapp.be.client.SlackLogDeliveryResponse

class SlackAppender : UnsynchronizedAppenderBase<ILoggingEvent>() {

    lateinit var token: String
    lateinit var botName: String
    lateinit var botIcon: String
    lateinit var channelId: String
    lateinit var dashboardUrl: String

    private val client = SlackLogDeliveryClient()
    private val layout = object : LayoutBase<ILoggingEvent>() {
        override fun doLayout(event: ILoggingEvent): String {
            return """
                ${event.loggerName}:
                ${event.formattedMessage.replace("\n","\n\t")}
            """.trimIndent()
        }
    }
    override fun append(eventObject: ILoggingEvent) {
        val traceId = eventObject.mdcPropertyMap["traceId"] ?: "TraceId Not Found"
        client.sendAsMessage(
            title = ":rotating_light::rotating_light::rotating_light: [Error] :rotating_light::rotating_light::rotating_light:",
            token = token,
            botIcon = botIcon,
            botName = botName,
            channel = channelId,
            text = layout.doLayout(eventObject),
            traceId = traceId,
            footer = dashboardUrl
        ).subscribe {
            println(it)
            when (it) {
                is SlackLogDeliveryResponse.Success -> {
                    val requestUrl = buildString {
                        append(eventObject.mdcPropertyMap["requestMethod"].orEmpty())
                        append("${eventObject.mdcPropertyMap["requestUrl"]}${eventObject.mdcPropertyMap["requestParameter"]?.let { parameters -> "?$parameters" }.orEmpty()}")
                    }
                    val requestHeader = eventObject.mdcPropertyMap["requestHeader"].orEmpty()
                    val requestBody = eventObject.mdcPropertyMap["requestBody"].orEmpty()

                    val accessLogs = buildMap {
                        put("REQUEST_URL", requestUrl)
                        put("REQUEST_HEADER", requestHeader)
                        put("REQUEST_BODY", requestBody)
                    }
                    client.sendAsThread(
                        ts = it.ts,
                        token = token,
                        botIcon = botIcon,
                        botName = botName,
                        channel = channelId,
                        titleAndText = accessLogs,
                        traceId = traceId
                    )
                }

                else -> {
                    /**
                     * Fail
                     */
                }
            }
        }
    }
}
