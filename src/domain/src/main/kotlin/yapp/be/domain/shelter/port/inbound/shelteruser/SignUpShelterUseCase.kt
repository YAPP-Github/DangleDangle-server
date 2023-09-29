package yapp.be.domain.shelter.port.inbound.shelteruser

import yapp.be.domain.model.ShelterUser
import yapp.be.model.vo.Email

interface SignUpShelterUseCase {
    fun signUpWithEssentialInfo(
        shelterId: Long,
        email: Email,
        password: String,
        phoneNumber: String,
    ): ShelterUser
}
