package yapp.be.storage.jpa.observationanimal.model.mappers

import yapp.be.domain.model.ObservationAnimal
import yapp.be.storage.jpa.observationanimal.model.ObservationAnimalEntity

fun ObservationAnimal.toEntityModel(): ObservationAnimalEntity {
    return ObservationAnimalEntity(
        id = this.id,
        name = this.name,
        profileImageUrl = this.profileImageUrl,
        specialNote = this.specialNote,
        shelterId = this.shelterId,
        age = this.age,
        gender = this.gender
    )
}

fun ObservationAnimalEntity.toDomainModel(): ObservationAnimal {
    return ObservationAnimal(
        id = this.id,
        name = this.name,
        profileImageUrl = this.profileImageUrl,
        specialNote = this.specialNote,
        age = this.age,
        gender = this.gender,
        shelterId = this.shelterId
    )
}
