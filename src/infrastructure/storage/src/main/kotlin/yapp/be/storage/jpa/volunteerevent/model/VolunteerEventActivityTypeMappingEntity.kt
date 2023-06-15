package yapp.be.storage.jpa.volunteerevent.model

import jakarta.persistence.*

@Entity
@Table(
    name = "volunteer_event_activity_type_mapping",
    indexes = [
        Index(name = "IDX_VOLUNTEER_EVENT_ID", columnList = "volunteer_event_id")
    ]
)
class VolunteerEventActivityTypeMappingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "volunteer_event_activity_type_id")
    val volunteerEventActivityTypeId: Long,
    @Column(name = "volunteer_event_id")
    val volunteerEventId: Long,
)
