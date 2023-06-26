package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.CheckVolunteerUseCase
import yapp.be.domain.port.outbound.VolunteerQueryHandler

@Service
class CheckVolunteerDomainService(
    private val volunteerQueryHandler: VolunteerQueryHandler
) : CheckVolunteerUseCase {
    @Transactional(readOnly = true)
    override fun isExistByEmail(email: String): Boolean {
        return volunteerQueryHandler.isExistByEmail(email)
    }
    @Transactional(readOnly = true)
    override fun isExistByNickname(nickname: String): Boolean {
        return volunteerQueryHandler.isExistByNickname(nickname)
    }
}
