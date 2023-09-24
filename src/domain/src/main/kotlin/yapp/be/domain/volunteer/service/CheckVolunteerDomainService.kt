package yapp.be.domain.volunteer.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteer.port.inbound.CheckVolunteerUseCase
import yapp.be.domain.volunteer.port.outbound.VolunteerQueryHandler
import yapp.be.model.vo.Email

@Service
class CheckVolunteerDomainService(
    private val volunteerQueryHandler: VolunteerQueryHandler
) : CheckVolunteerUseCase {
    @Transactional(readOnly = true)
    override fun isExistByEmail(email: Email): Boolean {
        return volunteerQueryHandler.isExistByEmail(email.value)
    }
    @Transactional(readOnly = true)
    override fun isExistByNickname(nickname: String): Boolean {
        return volunteerQueryHandler.isExistByNickname(nickname)
    }
}
