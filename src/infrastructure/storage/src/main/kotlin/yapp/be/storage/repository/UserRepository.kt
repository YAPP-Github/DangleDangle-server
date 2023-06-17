package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.model.User
import yapp.be.domain.port.outbound.UserQueryHandler
import yapp.be.domain.port.outbound.model.CreateUserRequest
import yapp.be.domain.port.outbound.model.ModifyUserTokenRequest
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.user.model.UserEntity
import yapp.be.storage.jpa.user.repository.UserJpaRepository

@Component
class UserRepository(
    private val jpaRepository: UserJpaRepository
) : UserQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }

    override fun save(command: CreateUserRequest): User? {
        val user = jpaRepository.save(
            UserEntity(
                email = command.email,
                nickname = command.nickname,
                phone = command.phone,
            )
        )
        return User(
            id = user.id,
            email = user.email,
            nickname = user.nickname,
            phone = user.phone,
            oAuthAccessToken = user.oAuthAccessToken,
            oAuthRefreshToken = user.oAuthRefreshToken,
        )
    }

    override fun findByEmail(email: String): User {
        val userEntity = jpaRepository.findByEmail(email) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.")
        return User(
            id = userEntity.id,
            email = userEntity.email,
            nickname = userEntity.nickname,
            phone = userEntity.phone,
            oAuthAccessToken = userEntity.oAuthAccessToken,
            oAuthRefreshToken = userEntity.oAuthRefreshToken,
        )
    }

    override fun findById(id: Long): User {
        val userEntity = jpaRepository.findById(id).orElseThrow { throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.") }
        return User(
            id = userEntity.id,
            email = userEntity.email,
            nickname = userEntity.nickname,
            phone = userEntity.phone,
            oAuthAccessToken = userEntity.oAuthAccessToken,
            oAuthRefreshToken = userEntity.oAuthRefreshToken,
        )
    }

    override fun isExistByEmail(email: String): Boolean {
        return jpaRepository.findByEmail(email) != null
    }

    override fun isExistByNickname(nickname: String): Boolean {
        return jpaRepository.findByNickname(nickname) != null
    }

    override fun saveToken(command: ModifyUserTokenRequest): User {
        val user = jpaRepository.save(
            UserEntity(
                id = command.userId.toLong(),
                oAuthAccessToken = command.oAuth2AccessToken,
                oAuthRefreshToken = command.oAuth2RefreshToken,
                email = command.email,
                nickname = command.nickname,
                phone = command.phone,
            )
        )
        return User(
            id = user.id,
            email = user.email,
            nickname = user.nickname,
            phone = user.phone,
            oAuthAccessToken = user.oAuthAccessToken,
            oAuthRefreshToken = user.oAuthRefreshToken,
        )
    }
}
