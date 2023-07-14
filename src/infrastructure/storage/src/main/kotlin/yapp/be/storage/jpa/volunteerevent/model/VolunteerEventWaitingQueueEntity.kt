package yapp.be.storage.jpa.volunteerevent.model

import jakarta.persistence.*

@Entity
@Table(
    name = "volunteer_event_waiting_queue",
    indexes = [
        Index(name = "IDX_VOLUNTEER_EVENT_ID", columnList = "volunteer_event_id"),
        Index(name = "IDX_VOLUNTEER_EVENT_ID_AND_VOLUNTEER_ID", columnList = "volunteer_event_id,volunteer_id"),
    ]
)
class VolunteerEventWaitingQueueEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "volunteer_id", nullable = false)
    val volunteerId: Long,
    @Column(name = "volunteer_event_id", nullable = false)
    val volunteerEventId: Long,
)
