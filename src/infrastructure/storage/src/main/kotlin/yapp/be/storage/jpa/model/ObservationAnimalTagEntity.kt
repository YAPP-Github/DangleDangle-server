package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "observation_animal_tag_entity")
class ObservationAnimalTagEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val name: String,
)
