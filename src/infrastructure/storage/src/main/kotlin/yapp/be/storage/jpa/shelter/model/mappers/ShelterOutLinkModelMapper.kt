package yapp.be.storage.jpa.shelter.model.mappers

import yapp.be.domain.model.ShelterOutLink
import yapp.be.storage.jpa.shelter.model.ShelterOutlinkEntity

fun ShelterOutLink.toEntityModel(): ShelterOutlinkEntity {
    return ShelterOutlinkEntity(
        id = this.id,
        url = this.url,
        outLinkType = this.type,
        shelterId = this.shelterId
    )
}

fun ShelterOutlinkEntity.toDomainModel(): ShelterOutLink {
    return ShelterOutLink(
        id = this.id,
        url = this.url,
        type = this.outLinkType,
        shelterId = this.shelterId
    )
}
