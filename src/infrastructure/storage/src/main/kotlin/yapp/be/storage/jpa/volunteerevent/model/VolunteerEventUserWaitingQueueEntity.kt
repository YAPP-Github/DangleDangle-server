package yapp.be.storage.jpa.volunteerevent.model

import jakarta.persistence.*
import yapp.be.enums.volunteerevent.UserEventWaitingStatus

@Entity
@Table(
    name = "volunteer_event_user_waiting_queue",
    indexes = [
        Index(name = "IDX_VOLUNTEER_EVENT_ID", columnList = "volunteer_event_id"),
        Index(name = "IDX_VOLUNTEER_EVENT_ID_AND_VOLUNTEER_ID", columnList = "volunteer_event_id,volunteer_id"),
    ]
)
class VolunteerEventUserWaitingQueueEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "volunteer_id")
    val volunteerId: Long,
    @Column(name = "volunteer_event_id")
    val volunteerEventId: Long,
    @Enumerated(EnumType.STRING)
    @Column(name = "waiting_status")
    val waitingStatus: UserEventWaitingStatus
)
