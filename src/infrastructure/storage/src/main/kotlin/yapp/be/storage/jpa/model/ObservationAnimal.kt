package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "observation_animal")
class ObservationAnimal (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val identifier: String,
    @Column
    val name: String,
    @Column
    val image: String,
    @Column
    val specialNote: String,
)
