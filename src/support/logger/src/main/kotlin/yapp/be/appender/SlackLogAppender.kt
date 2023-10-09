package yapp.be.appender

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.UnsynchronizedAppenderBase
import yapp.be.client.SlackLogDeliveryClient

class SlackLogAppender() : UnsynchronizedAppenderBase<ILoggingEvent>() {

    lateinit var channelId: String
    lateinit var botName: String
    lateinit var botIcon: String
    lateinit var slackWebHookUrl: String

    private val client = SlackLogDeliveryClient()
    override fun append(eventObject: ILoggingEvent?) {
        client.send(
            channelId = channelId,
            webhookUrl = slackWebHookUrl,
            botIcon = botIcon,
            botName = botName
        )
    }
}
