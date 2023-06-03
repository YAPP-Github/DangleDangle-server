package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "volunteer_event_mapping_entity")
class VolunteerEventMappingEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val userIdentifier: String,
    @Column
    val volunteerEventId: String,
)
