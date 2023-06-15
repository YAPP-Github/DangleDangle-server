package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.SignUpWithAdditionalInfoRequestDto
import yapp.be.apiapplication.shelter.service.model.SignUpWithAdditionalInfoResponseDto
import yapp.be.apiapplication.shelter.service.model.SignUpWithEssentialInfoRequestDto
import yapp.be.apiapplication.shelter.service.model.SignUpWithEssentialInfoResponseDto
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.domain.port.inbound.CreateShelterUseCase
import yapp.be.domain.port.inbound.GetShelterUseCase
import yapp.be.domain.port.inbound.GetShelterUserUseCase
import yapp.be.domain.port.inbound.ShelterUserSignUpUseCase
import yapp.be.exceptions.CustomException

@Service
class ShelterSignUpApplicationService(
    private val getShelterUseCase: GetShelterUseCase,
    private val getShelterUserUseCase: GetShelterUserUseCase,
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
    fun signUpWithAdditionalInfo(reqDto: SignUpWithAdditionalInfoRequestDto): SignUpWithAdditionalInfoResponseDto {
        val shelter = getShelterUseCase.getShelterById(reqDto.shelterId)
        val shelterUser = getShelterUserUseCase.getShelterUserById(reqDto.shelterUserId)

        if (shelterUser.shelterId != shelter.id) {
            throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "접근 권한이 없습니다.")
        }

        shelterUserSignUpUseCase.signUpWithAdditionalInfo(
            shelterId = reqDto.shelterId,
            shelterUserId = reqDto.shelterUserId,
            outLinks = reqDto.outLinks,
            parkingInfo = reqDto.parkingInfo,
            donation = reqDto.donation,
            notice = reqDto.notice
        )

        return SignUpWithAdditionalInfoResponseDto(
            shelterId = shelter.id,
            shelterUserId = shelterUser.id
        )
    }
}
