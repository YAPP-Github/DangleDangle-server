package yapp.be.storage.jpa.volunteerevent.model

import jakarta.persistence.*

@Entity
@Table(
    name = "volunteer_event_join_queue",
    indexes = [
        Index(name = "IDX_USER_ID", columnList = "volunteer_id"),
        Index(name = "IDX_VOLUNTEER_EVENT_ID", columnList = "volunteer_event_id")
    ]
)
class VolunteerEventJoinQueueEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "volunteer_id", nullable = false)
    val volunteerId: Long,
    @Column(name = "volunteer_event_id", nullable = false)
    val volunteerEventId: Long,
)
