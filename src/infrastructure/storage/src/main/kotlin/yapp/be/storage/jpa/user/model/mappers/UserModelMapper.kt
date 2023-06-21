package yapp.be.storage.jpa.user.model.mappers

import yapp.be.domain.model.User
import yapp.be.model.Email
import yapp.be.storage.jpa.user.model.UserEntity

fun User.toEntityModel(): UserEntity {
    return UserEntity(
        id = this.id,
        email = this.email.value,
        nickname = this.nickname,
        role = this.role,
        phone = this.phone,
        oAuthType = this.oAuthType,
        oAuthAccessToken = this.oAuthAccessToken,
        oAuthRefreshToken = this.oAuthRefreshToken,
        deleted = this.isDeleted,
    )
}

fun UserEntity.toDomainModel(): User {
    return User(
        id = this.id,
        email = Email(this.email),
        nickname = this.nickname,
        role = this.role,
        phone = this.phone,
        oAuthType = this.oAuthType,
        oAuthAccessToken = this.oAuthAccessToken,
        oAuthRefreshToken = this.oAuthRefreshToken,
        isDeleted = this.deleted
    )
}
