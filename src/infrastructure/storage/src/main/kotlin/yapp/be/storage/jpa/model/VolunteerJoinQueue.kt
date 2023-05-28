package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "volunteer_join_queue")
class VolunteerJoinQueue (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val volunteerIdentifier: String,
    @Column
    val volunteerEventIdentifier: String,
)