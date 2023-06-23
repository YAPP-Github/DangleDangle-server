package yapp.be.domain.port.inbound

interface CheckVolunteerUseCase {
    fun isExistByEmail(email: String): Boolean
    fun isExistByNickname(nickname: String): Boolean
}
