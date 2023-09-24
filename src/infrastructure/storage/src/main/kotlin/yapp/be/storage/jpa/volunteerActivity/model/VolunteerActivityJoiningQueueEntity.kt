package yapp.be.storage.jpa.volunteerActivity.model

import jakarta.persistence.*

@Entity
@Table(
    name = "volunteer_activity_joining_queue",
    indexes = [
        Index(name = "IDX_VOLUNTEER_ID", columnList = "volunteer_id"),
        Index(name = "IDX_VOLUNTEER_ACTIVITY_ID", columnList = "volunteer_activity_id")
    ]
)
class VolunteerActivityJoiningQueueEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "volunteer_id", nullable = false)
    val volunteerId: Long,
    @Column(name = "volunteer_activity_id", nullable = false)
    val volunteerActivityId: Long,
)
