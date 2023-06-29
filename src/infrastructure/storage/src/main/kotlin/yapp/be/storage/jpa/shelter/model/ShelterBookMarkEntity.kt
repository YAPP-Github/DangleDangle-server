package yapp.be.storage.jpa.shelter.model

import jakarta.persistence.*

@Entity
@Table(
    name = "shelter_bookmark",
    indexes = [
        Index(name = "IDX_SHELTER_BOOKMARK", columnList = "shelter_id,volunteer_id")
    ]
)
class ShelterBookMarkEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "shelter_id")
    val shelterId: Long,

    @Column(name = "volunteer_id")
    val volunteerId: Long
)
