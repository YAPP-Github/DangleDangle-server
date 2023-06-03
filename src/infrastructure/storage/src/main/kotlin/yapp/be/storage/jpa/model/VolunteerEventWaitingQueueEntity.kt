package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "volunteer_event_waiting_queue_entity")
class VolunteerEventWaitingQueueEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val userIdentifier: String,
    @Column
    val volunteerEventId: String,
)
