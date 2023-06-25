package yapp.be.storage.jpa.volunteerevent.model

import jakarta.persistence.*

@Entity
@Table(
    name = "volunteer_event_user_mapping",
    indexes = [
        Index(name = "IDX_VOLUNTEER_ID", columnList = "volunteer_id"),
        Index(name = "IDX_VOLUNTEER_EVENT_ID", columnList = "volunteer_event_id")
    ]
)
class VolunteerEventUserMappingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "volunteer_id")
    val volunteerId: Long,
    @Column(name = "volunteer_event_id")
    val volunteerEventId: Long,
)
