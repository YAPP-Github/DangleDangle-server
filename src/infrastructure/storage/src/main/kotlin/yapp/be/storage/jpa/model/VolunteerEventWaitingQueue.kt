package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "volunteer_waiting_queue")
class VolunteerEventWaitingQueue (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val userIdentifier: String,
    @Column
    val volunteerEventIdentifier: String,
)
