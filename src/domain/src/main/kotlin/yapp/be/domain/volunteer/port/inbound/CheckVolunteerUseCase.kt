package yapp.be.domain.volunteer.port.inbound

import yapp.be.model.vo.Email

interface CheckVolunteerUseCase {
    fun isExistByEmail(email: Email): Boolean
    fun isExistByNickname(nickname: String): Boolean
}
