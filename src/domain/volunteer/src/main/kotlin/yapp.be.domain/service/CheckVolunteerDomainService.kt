package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.CheckVolunteerUseCase
import yapp.be.domain.port.outbound.VolunteerQueryHandler

@Service
class CheckVolunteerDomainService(
    private val volunteerQueryHandler: VolunteerQueryHandler
) : CheckVolunteerUseCase {
    override fun isExistByEmail(email: String): Boolean {
        return volunteerQueryHandler.isExistByEmail(email)
    }

    override fun isExistByNickname(nickname: String): Boolean {
        return volunteerQueryHandler.isExistByNickname(nickname)
    }
}
