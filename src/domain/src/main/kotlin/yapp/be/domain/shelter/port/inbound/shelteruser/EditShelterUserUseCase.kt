package yapp.be.domain.shelter.port.inbound.shelteruser

import yapp.be.domain.model.ShelterUser

interface EditShelterUserUseCase {
    fun editShelterUser(shelterUser: ShelterUser): ShelterUser
}
