package yapp.be.model

import yapp.be.model.enums.event.EventType
import yapp.be.model.enums.event.NotificationType
import java.io.Serializable

data class SendEvent(
    val recordId: String,
    val eventType: EventType,
    val senderId: String,
    val senderName: String,
    val notificationType: NotificationType,
    val json: String,
) : Serializable {
    constructor(id: String, map: Map<String, String>) : this(
        id,
        EventType.valueOf(map["eventType"]!!),
        map["senderId"]!!,
        map["senderName"]!!,
        NotificationType.valueOf(map["notificationType"]!!),
        map["json"]!!,
    )
    fun toMap(): Map<ByteArray, ByteArray> =
        mapOf(
            "eventType".toByteArray() to this.eventType.toString().toByteArray(),
            "senderId".toByteArray() to this.senderId.toByteArray(),
            "senderName".toByteArray() to this.senderName.toByteArray(),
            "notificationType".toByteArray() to this.notificationType.toString().toByteArray(),
            "json".toByteArray() to this.json.toByteArray(),
        )
}
