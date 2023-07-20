package yapp.be.storage.jpa.event.model

import jakarta.persistence.*
import yapp.be.model.enums.event.EventStatus
import java.time.LocalDateTime

@Entity
@Table(name = "add_volunteer_event_event")
class AddVolunteerEventEventEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "volunteer_id", nullable = false)
    val volunteerId: Long,
    @Column(name = "shelter_id", nullable = false)
    val shelterId: Long,
    @Column(name = "volunteer_event_id", nullable = false)
    val volunteerEventId: Long,
    @Column(name = "event_status", nullable = false)
    @Enumerated(EnumType.STRING)
    var eventStatus: EventStatus = EventStatus.BEFORE_PROCESSING,
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime,
)
