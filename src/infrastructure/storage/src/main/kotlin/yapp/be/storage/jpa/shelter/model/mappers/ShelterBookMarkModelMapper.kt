package yapp.be.storage.jpa.shelter.model.mappers

import yapp.be.domain.model.ShelterBookMark
import yapp.be.storage.jpa.shelter.model.ShelterBookMarkEntity

fun ShelterBookMark.toEntityModel(): ShelterBookMarkEntity {
    return ShelterBookMarkEntity(
        id = this.id,
        shelterId = this.shelterId,
        volunteerId = this.volunteerId
    )
}

fun ShelterBookMarkEntity.toDomainModel(): ShelterBookMark {
    return ShelterBookMark(
        id = this.id,
        shelterId = this.shelterId,
        volunteerId = this.volunteerId
    )
}
