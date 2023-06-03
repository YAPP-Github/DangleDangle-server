package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "observation_animal_tag_mapping_entity")
class ObservationAnimalTagMappingEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val observationAnimalId: String,
    @Column
    val observationAnimalTagId: String,
)
