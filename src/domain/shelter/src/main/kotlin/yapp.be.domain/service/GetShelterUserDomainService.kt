package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.inbound.GetShelterUserUseCase

@Service
class GetShelterUserDomainService : GetShelterUserUseCase {
    override fun getShelterUserById(shelterUserId: Long): ShelterUser {
        TODO("Not yet implemented")
    }
}
