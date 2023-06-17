package yapp.be.domain.port.inbound

interface CheckUserUseCase {
    fun isExist(email: String): Boolean
}
