package yapp.be.storage.jpa.shelter.model.mappers

import yapp.be.domain.model.Shelter
import yapp.be.storage.jpa.shelter.model.ShelterEntity

fun Shelter.toEntityModel(): ShelterEntity {
}

fun ShelterEntity.toDomainModel(): Shelter {
}
