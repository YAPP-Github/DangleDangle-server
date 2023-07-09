package yapp.be.domain.port.inbound

import yapp.be.model.vo.Email

interface CheckVolunteerUseCase {
    fun isExistByEmail(email: Email): Boolean
    fun isExistByNickname(nickname: String): Boolean
}
