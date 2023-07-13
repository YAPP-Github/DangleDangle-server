package yapp.be.storage.jpa.observationanimal.model

import jakarta.persistence.*
import yapp.be.domain.model.ObservationAnimal
import yapp.be.model.enums.observaitonanimal.Gender
import yapp.be.storage.jpa.common.model.BaseTimeEntity

@Entity
@Table(
    name = "observation_animal",
    indexes = [
        Index(name = "IDX_SHELTER_ID", columnList = "shelter_id")
    ]
)
class ObservationAnimalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "profile_image_url")
    var profileImageUrl: String?,

    @Column(name = "age")
    var age: String?,

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    var gender: Gender?,

    @Column(name = "special_note", nullable = false)
    var specialNote: String,

    @Column(name = "breed")
    var breed: String?,

    @Column(name = "shelter_id", nullable = false)
    var shelterId: Long
) : BaseTimeEntity() {
    fun update(observationAnimal: ObservationAnimal) {
        this.name = observationAnimal.name
        this.profileImageUrl = observationAnimal.profileImageUrl
        this.age = observationAnimal.age
        this.gender = observationAnimal.gender
        this.specialNote = observationAnimal.specialNote
        this.breed = observationAnimal.breed
    }
}
