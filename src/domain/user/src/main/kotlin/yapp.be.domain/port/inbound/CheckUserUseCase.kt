package yapp.be.domain.port.inbound

interface CheckUserUseCase {
    fun isExistByEmail(email: String): Boolean
    fun isExistByNickname(nickname: String): Boolean
}
