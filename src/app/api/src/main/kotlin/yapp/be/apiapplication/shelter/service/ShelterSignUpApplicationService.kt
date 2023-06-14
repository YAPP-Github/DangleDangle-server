package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.SignUpWithAdditionalInfoRequestDto
import yapp.be.apiapplication.shelter.service.model.SignUpWithAdditionalInfoResponseDto
import yapp.be.apiapplication.shelter.service.model.SignUpWithEssentialInfoRequestDto
import yapp.be.apiapplication.shelter.service.model.SignUpWithEssentialInfoResponseDto
import yapp.be.domain.port.inbound.CreateShelterUseCase
import yapp.be.domain.port.inbound.ShelterUserSignUpUseCase

@Service
class ShelterSignUpApplicationService(
    private val createShelterUseCase: CreateShelterUseCase,
    private val shelterUserSignUpUseCase: ShelterUserSignUpUseCase
) {

    @Transactional
    fun signUpWithEssentialInfo(reqDto: SignUpWithEssentialInfoRequestDto): SignUpWithEssentialInfoResponseDto {
        val shelter = createShelterUseCase.create(
            name = reqDto.name,
            address = reqDto.address,
            description = reqDto.description,
            phoneNumber = reqDto.phoneNumber,
        )
        val shelterUser = shelterUserSignUpUseCase.signUpWithEssentialInfo(
            shelterId = shelter.id,
            email = reqDto.email,
            password = reqDto.password,
            phoneNumber = reqDto.phoneNumber
        )

        return SignUpWithEssentialInfoResponseDto(
            shelterId = shelter.id,
            shelterUserId = shelterUser.id
        )
    }

    @Transactional
    fun signUpWithAdditionalInfo(userId: Long, reqDto: SignUpWithAdditionalInfoRequestDto): SignUpWithAdditionalInfoResponseDto {
        shelterUserSignUpUseCase.signUpWithEssentialInfo()
    }
}
