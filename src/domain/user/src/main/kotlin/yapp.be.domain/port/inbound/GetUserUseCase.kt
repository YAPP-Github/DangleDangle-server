package yapp.be.domain.port.inbound

import yapp.be.domain.model.User

interface GetUserUseCase {
    fun getByEmail(email: String): User
}
