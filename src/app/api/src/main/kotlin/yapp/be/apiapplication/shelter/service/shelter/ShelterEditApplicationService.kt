package yapp.be.apiapplication.shelter.service.shelter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterProfileImageRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterProfileImageResponseDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterWithAdditionalInfoRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterWithAdditionalInfoResponseDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditWithEssentialInfoRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditWithEssentialInfoResponseDto
import yapp.be.domain.port.inbound.GetShelterUseCase
import yapp.be.domain.port.inbound.GetShelterUserUseCase
import yapp.be.domain.port.inbound.EditShelterUseCase

@Service
class ShelterEditApplicationService(
    private val getShelterUseCase: GetShelterUseCase,
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val editShelterUseCase: EditShelterUseCase
) {

    @Transactional
    fun editProfileImage(reqDto: EditShelterProfileImageRequestDto): EditShelterProfileImageResponseDto {
        val shelter = editShelterUseCase.editProfileImage(
            shelterId = reqDto.shelterId,
            profileImageUrl = reqDto.profileImageUrl
        )

        return EditShelterProfileImageResponseDto(
            shelterId = shelter.id
        )
    }

    @Transactional
    fun editEssentialInfo(
        shelterId: Long,
        reqDto: EditWithEssentialInfoRequestDto
    ): EditWithEssentialInfoResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId)
        // val shelterUser = getShelterUserUseCase.getShelterUserById(reqDto.shelterUserId)

        // if (shelterUser.shelterId != shelter.id) {
        //  throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "접근 권한이 없습니다.")
        // }

        editShelterUseCase.editWithEssentialInfo(
            shelterId = shelterId,
            name = reqDto.name,
            phoneNumber = reqDto.phoneNumber,
            description = reqDto.description,
            address = reqDto.address
        )

        return EditWithEssentialInfoResponseDto(
            shelterId = shelter.id,
            shelterUserId = 0
        )
    }

    @Transactional
    fun editAdditionalInfo(
        shelterId: Long,
        reqDto: EditShelterWithAdditionalInfoRequestDto
    ): EditShelterWithAdditionalInfoResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId)
        // val shelterUser = getShelterUserUseCase.getShelterUserById(reqDto.shelterUserId)

        // if (shelterUser.shelterId != shelter.id) {
        //   throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "접근 권한이 없습니다.")
        // }

        editShelterUseCase.editWithAdditionalInfo(
            shelterId = shelterId,
            parkingInfo = reqDto.parkingInfo,
            bankAccount = reqDto.donation,
            notice = reqDto.notice
        )

        editShelterUseCase.editShelterOutLink(
            shelterId = shelterId,
            outLinks = reqDto.outLinks
        )

        return EditShelterWithAdditionalInfoResponseDto(
            shelterId = shelter.id,
            shelterUserId = 0
        )
    }
}
