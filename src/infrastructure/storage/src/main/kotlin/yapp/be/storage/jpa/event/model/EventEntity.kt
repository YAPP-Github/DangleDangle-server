package yapp.be.storage.jpa.event.model

import jakarta.persistence.*
import yapp.be.model.Event
import yapp.be.model.enums.event.EventStatus
import yapp.be.model.enums.event.EventType

@Entity
@Table(
    name = "event",
    indexes = [
        Index(name = "IDX_EVENT_TYPE", columnList = "type")
    ]
)
class EventEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "json")
    var json: String,
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var eventType: EventType,
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var eventStatus: EventStatus,
) {
    fun update(event: Event) {
        this.json = event.json
        this.eventType = event.eventType
        this.eventStatus = event.eventStatus
    }
}
