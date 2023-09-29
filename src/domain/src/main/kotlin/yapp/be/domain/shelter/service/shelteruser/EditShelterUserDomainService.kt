package yapp.be.domain.shelter.service.shelteruser

import org.springframework.stereotype.Service
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.outbound.shelteruser.ShelterUserCommandHandler
import yapp.be.domain.shelter.port.inbound.shelteruser.EditShelterUserUseCase

@Service
class EditShelterUserDomainService(
    private val shelterUserCommandHandler: ShelterUserCommandHandler
) : EditShelterUserUseCase {
    override fun editShelterUser(shelterUser: ShelterUser): ShelterUser {
        return shelterUserCommandHandler.update(shelterUser)
    }
}
