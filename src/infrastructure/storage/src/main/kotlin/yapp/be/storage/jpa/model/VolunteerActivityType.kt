package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "volunteer_activity_type")
class VolunteerActivityType (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val identifier: String,
    @Column
    val name: String,
)
