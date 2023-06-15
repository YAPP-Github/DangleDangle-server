package yapp.be.storage.jpa.observationanimal.model

import jakarta.persistence.*

@Entity
@Table(
    name = "observation_animal_tag_mapping",
    indexes = [
        Index(name = "IDX_OBSERVATION_ANIMAL_ID", columnList = "observation_animal_id")
    ]
)
class ObservationAnimalTagMappingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "observation_animal_id")
    val observationAnimalId: Long,
    @Column(name = "observation_animal_tag_id")
    val observationAnimalTagId: Long,
)
