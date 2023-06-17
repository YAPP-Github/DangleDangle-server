package yapp.be.domain.port.outbound

import yapp.be.domain.model.User
import yapp.be.domain.port.outbound.model.CreateUserRequest
import yapp.be.domain.port.outbound.model.ModifyUserTokenRequest

interface UserQueryHandler {
    fun countAll(): Int
    fun findByEmail(email: String): User
    fun findById(id: Long): User
    fun isExistByEmail(email: String): Boolean
    fun isExistByNickname(nickname: String): Boolean
    fun save(command: CreateUserRequest): User?
    fun saveToken(command: ModifyUserTokenRequest): User
}
