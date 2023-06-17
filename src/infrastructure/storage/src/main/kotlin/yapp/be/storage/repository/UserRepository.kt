package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.model.User
import yapp.be.domain.port.outbound.UserQueryHandler
import yapp.be.domain.port.outbound.model.CreateUserRequest
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
                oAuthAccessToken = command.oAuthAccessToken,
                shelterId = "",
            )
        )
        return User(
            id = user.id,
            email = user.email,
            nickname = user.nickname,
            phone = user.phone,
            oAuthAccessToken = user.oAuthAccessToken,
            shelterId = null,
        )
    }

    override fun findByEmail(email: String): User? {
        return jpaRepository.findByEmail(email)?.let {
            User(
                id = it.id,
                email = it.email,
                nickname = it.nickname,
                phone = it.phone,
                oAuthAccessToken = it.oAuthAccessToken,
                shelterId = null,
            )
        }
    }
}
