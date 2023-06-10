package yapp.be.storage.jpa.observationanimal.model

import jakarta.persistence.*

@Entity
@Table(name = "observation_animal_tag")
class ObservationAnimalTagEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name", unique = true)
    val name: String,
)
