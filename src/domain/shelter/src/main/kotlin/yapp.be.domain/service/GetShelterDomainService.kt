package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.Shelter
import yapp.be.domain.port.inbound.GetShelterUseCase

@Service
class GetShelterDomainService : GetShelterUseCase {
    override fun getShelterById(shelterId: Long): Shelter {
        TODO("Not yet implemented")
    }
}
