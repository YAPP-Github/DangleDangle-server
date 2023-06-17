package yapp.be.domain.port.outbound

import yapp.be.domain.model.User
import yapp.be.domain.port.outbound.model.CreateUserRequest

interface UserQueryHandler {
    fun countAll(): Int
    fun findByEmail(email: String): User?
    fun save(command: CreateUserRequest): User?
}
