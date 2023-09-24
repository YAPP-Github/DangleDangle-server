package yapp.be.storage.jpa.volunteerActivity.model

import jakarta.persistence.*

@Entity
@Table(
    name = "volunteer_activity_waiting_queue",
    indexes = [
        Index(name = "IDX_VOLUNTEER_ACTIVITY_ID", columnList = "volunteer_activity_id"),
        Index(name = "IDX_VOLUNTEER_ACTIVITY_ID_AND_VOLUNTEER_ID", columnList = "volunteer_activity_id,volunteer_id"),
    ]
)
class VolunteerActivityWaitingQueueEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "volunteer_id", nullable = false)
    val volunteerId: Long,
    @Column(name = "volunteer_activity_id", nullable = false)
    val volunteerActivityId: Long,
)
