package yapp.be.storage.jpa.volunteer.model.mappers

import yapp.be.domain.model.Volunteer
import yapp.be.model.vo.Email
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity

fun Volunteer.toEntityModel(): VolunteerEntity {
    return VolunteerEntity(
        id = this.id,
        email = this.email.value,
        nickname = this.nickname,
        role = this.role,
        phone = this.phone,
        oAuthType = this.oAuthType,
        oAuthIdentifier = this.oAuthIdentifier,
        oAuthAccessToken = this.oAuthAccessToken,
        oAuthRefreshToken = this.oAuthRefreshToken,
        deleted = this.isDeleted,
    )
}

fun VolunteerEntity.toDomainModel(): Volunteer {
    return Volunteer(
        id = this.id,
        email = Email(this.email),
        nickname = this.nickname,
        role = this.role,
        phone = this.phone,
        oAuthType = this.oAuthType,
        oAuthIdentifier = this.oAuthIdentifier,
        oAuthAccessToken = this.oAuthAccessToken,
        oAuthRefreshToken = this.oAuthRefreshToken,
        isDeleted = this.deleted
    )
}
