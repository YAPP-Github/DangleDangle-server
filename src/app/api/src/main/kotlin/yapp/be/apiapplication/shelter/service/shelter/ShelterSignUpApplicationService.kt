package yapp.be.apiapplication.shelter.service.shelter

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.shelter.model.CheckShelterUserEmailExistResponseDto
import yapp.be.apiapplication.shelter.service.shelter.model.SignUpShelterWithEssentialInfoRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.SignUpShelterWithEssentialInfoResponseDto
import yapp.be.domain.port.inbound.CreateShelterUseCase
import yapp.be.domain.port.inbound.GetShelterUserUseCase
import yapp.be.domain.port.inbound.SignUpShelterUseCase
import yapp.be.model.Email

@Service
class ShelterSignUpApplicationService(
    private val encoder: PasswordEncoder,
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val createShelterUseCase: CreateShelterUseCase,
    private val signUpShelterUseCase: SignUpShelterUseCase
) {

    @Transactional
    fun signUpWithEssentialInfo(reqDto: SignUpShelterWithEssentialInfoRequestDto): SignUpShelterWithEssentialInfoResponseDto {
        val shelter = createShelterUseCase.create(
            name = reqDto.name,
            address = reqDto.address,
            description = reqDto.description,
            phoneNumber = reqDto.phoneNumber,
        )
        val shelterUser = signUpShelterUseCase.signUpWithEssentialInfo(
            shelterId = shelter.id,
            email = reqDto.email,
            password = encoder.encode(reqDto.password),
            phoneNumber = reqDto.phoneNumber
        )

        return SignUpShelterWithEssentialInfoResponseDto(
            shelterId = shelter.id,
            shelterUserId = shelterUser.id
        )
    }

    @Transactional(readOnly = true)
    fun checkIsShelterUserEmailExist(email: Email): CheckShelterUserEmailExistResponseDto {
        val isExist = getShelterUserUseCase.checkEmailExist(email)
        return CheckShelterUserEmailExistResponseDto(isExist)
    }
}
