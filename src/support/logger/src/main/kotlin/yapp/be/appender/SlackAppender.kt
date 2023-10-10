package yapp.be.appender

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.UnsynchronizedAppenderBase
import io.github.oshai.kotlinlogging.KotlinLogging
import yapp.be.client.SlackLogDeliveryClient

class SlackAppender : UnsynchronizedAppenderBase<ILoggingEvent>() {

    lateinit var token: String
    lateinit var botName: String
    lateinit var botIcon: String
    lateinit var channelId: String

    private val client = SlackLogDeliveryClient()
    private val logger = KotlinLogging.logger { }
    override fun append(eventObject: ILoggingEvent) {
        client.send(
            token = token,
            botIcon = botIcon,
            botName = botName,
            channel = channelId,
            text = eventObject.formattedMessage
        ).subscribe()
    }
}
