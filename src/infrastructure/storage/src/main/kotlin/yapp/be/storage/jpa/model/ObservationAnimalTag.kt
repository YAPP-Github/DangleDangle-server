package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "observation_animal_tag")
class ObservationAnimalTag (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val name: String,
)
