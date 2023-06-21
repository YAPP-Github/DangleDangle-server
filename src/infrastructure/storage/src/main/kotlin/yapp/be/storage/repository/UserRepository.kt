package yapp.be.storage.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.User
import yapp.be.domain.port.outbound.UserQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.user.model.mappers.toDomainModel
import yapp.be.storage.jpa.user.model.mappers.toEntityModel
import yapp.be.storage.jpa.user.repository.UserJpaRepository

@Repository
class UserRepository(
    private val jpaRepository: UserJpaRepository
) : UserQueryHandler {
    @Transactional(readOnly = true)
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }

    override fun save(user: User): User {
        val userEntity = jpaRepository.save(
            user.toEntityModel()
        )
        return userEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): User {
        val userEntity = jpaRepository.findByEmail(email) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.")
        return userEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): User {
        val userEntity = jpaRepository.findById(id).orElseThrow { throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.") }
        return userEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun isExistByEmail(email: String): Boolean {
        return jpaRepository.findByEmail(email) != null
    }

    @Transactional(readOnly = true)
    override fun isExistByNickname(nickname: String): Boolean {
        return jpaRepository.findByNickname(nickname) != null
    }

    override fun saveToken(user: User): User {
        val userEntity = jpaRepository.findByIdOrNull(user.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "User Not Found")
        userEntity.update(user)
        jpaRepository.save(userEntity)

        return userEntity.toDomainModel()
    }
}
