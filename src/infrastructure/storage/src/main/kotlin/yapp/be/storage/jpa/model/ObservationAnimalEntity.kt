package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "observation_animal_entity")
class ObservationAnimalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val name: String,
    @Column
    val imageUrl: String,
    @Column
    val specialNote: String,
)
