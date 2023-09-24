package yapp.be.storage.jpa.shelter.model.mappers

import yapp.be.domain.model.ShelterUser
import yapp.be.model.vo.Email
import yapp.be.storage.jpa.shelter.model.ShelterUserEntity

fun ShelterUser.toEntityModel(): ShelterUserEntity {
    return ShelterUserEntity(
        id = this.id,
        email = this.email.value,
        password = this.password,
        shelterId = this.shelterId,
        needToChangePassword = this.needToChangePassword

    )
}

fun ShelterUserEntity.toDomainModel(): ShelterUser {
    return ShelterUser(
        id = this.id,
        email = Email(this.email),
        password = this.password,
        shelterId = this.shelterId,
        needToChangePassword = this.needToChangePassword
    )
}
