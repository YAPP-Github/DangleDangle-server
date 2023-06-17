package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.inbound.GetShelterUserUseCase
import yapp.be.domain.port.outbound.ShelterUserQueryHandler
import yapp.be.model.Email

@Service
class GetShelterUserDomainService(
    private val shelterUserQueryHandler: ShelterUserQueryHandler
) : GetShelterUserUseCase {
    override fun getShelterUserById(shelterUserId: Long): ShelterUser {
        return shelterUserQueryHandler.findById(shelterUserId)
    }

    override fun checkEmailExist(email: Email): Boolean {
        return shelterUserQueryHandler.existByEmail(email)
    }
}
