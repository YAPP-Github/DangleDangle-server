package yapp.be.storage.jpa.volunteerevent.model

import jakarta.persistence.*

@Entity
@Table(name = "volunteer_event_activity_type_mapping")
class VolunteerEventActivityTypeMappingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "volunteer_event_activity_type_id")
    val volunteerEventActivityTypeId: Long,
    @Column(name = "volunteer_event_id")
    val volunteerEventId: Long,
)
