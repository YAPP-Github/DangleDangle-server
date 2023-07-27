package yapp.be.model.vo

import yapp.be.model.enums.event.EventType
import yapp.be.model.enums.event.NotificationType
import java.io.Serializable

data class AddEventEntity(
    val volunteerEventId: String,
    val shelterId: String,
    val likedVolunteerIds: List<String>,
)

data class ParticipateEventEntity(
    val volunteerEventId: String,
    val volunteerId: String, // 참여 신청한 사람
)

data class WithdrawEventEntity(
    val volunteerEventId: String,
    val waitingVolunteerIds: List<String>, // 대기 신청한 사람
)

data class SendEvent(
    val eventType: EventType, // 템플릿 선정
    val senderId: String,
    val notificationType: NotificationType,
    val json: String,
) : Serializable {
    fun toMap(): Map<ByteArray, ByteArray> =
        mapOf(
            "eventType".toByteArray() to this.eventType.toString().toByteArray(),
            "senderId".toByteArray() to this.senderId.toByteArray(),
            "notificationType".toByteArray() to this.notificationType.toString().toByteArray(),
            "json".toByteArray() to this.json.toByteArray(),
        )
}
