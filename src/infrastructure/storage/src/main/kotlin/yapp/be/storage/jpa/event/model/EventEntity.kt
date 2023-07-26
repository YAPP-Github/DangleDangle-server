package yapp.be.storage.jpa.event.model

import jakarta.persistence.*
import yapp.be.model.enums.event.EventType

@Entity
@Table(name = "event")
class EventEntity(
    @Id
    @Column(name = "recordId", nullable = false)
    val recordId: String,
    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var eventType: EventType = EventType.ADD,
    @Column(name = "json", columnDefinition = "TEXT", nullable = false)
    var json: String,
)
