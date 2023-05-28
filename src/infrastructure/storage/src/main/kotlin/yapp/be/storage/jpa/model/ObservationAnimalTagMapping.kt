package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "observation_animal_tag_mapping")
class ObservationAnimalTagMapping (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val observationAnimalIdentifier: String,
    @Column
    val observationAnimalTagIdentifier: String,
)
