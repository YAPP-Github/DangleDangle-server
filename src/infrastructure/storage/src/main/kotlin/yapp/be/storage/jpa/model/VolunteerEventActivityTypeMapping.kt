package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "volunteer_event_activity_type_mapping")
class VolunteerEventActivityTypeMapping (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val volunteerEventActivityTypeIdentifier: String,
    @Column
    val volunteerEventIdentifier: String,
)
