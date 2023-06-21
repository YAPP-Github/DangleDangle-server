package yapp.be.domain.port.outbound

import yapp.be.domain.model.User

interface UserQueryHandler {
    fun countAll(): Int
    fun findByEmail(email: String): User
    fun findById(id: Long): User
    fun isExistByEmail(email: String): Boolean
    fun isExistByNickname(nickname: String): Boolean
    fun save(user: User): User
    fun saveToken(user: User): User
}
