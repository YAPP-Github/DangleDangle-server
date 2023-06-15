package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.EditProfileImageRequestDto
import yapp.be.apiapplication.shelter.service.model.EditProfileImageResponseDto
import yapp.be.apiapplication.shelter.service.model.EditWithAdditionalInfoRequestDto
import yapp.be.apiapplication.shelter.service.model.EditWithAdditionalInfoResponseDto
import yapp.be.apiapplication.shelter.service.model.EditWithEssentialInfoRequestDto
import yapp.be.apiapplication.shelter.service.model.EditWithEssentialInfoResponseDto
import yapp.be.domain.port.inbound.GetShelterUseCase
import yapp.be.domain.port.inbound.GetShelterUserUseCase
import yapp.be.domain.port.inbound.ShelterEditUseCase

@Service
class ShelterEditApplicationService(
    private val getShelterUseCase: GetShelterUseCase,
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val shelterEditUseCase: ShelterEditUseCase
) {

    @Transactional
    fun editProfileImage(reqDto: EditProfileImageRequestDto): EditProfileImageResponseDto {
        val shelter = shelterEditUseCase.editProfileImage(
            shelterId = reqDto.shelterId,
            profileImageUrl = reqDto.profileImageUrl
        )

        return EditProfileImageResponseDto(
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

        shelterEditUseCase.editWithEssentialInfo(
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
        reqDto: EditWithAdditionalInfoRequestDto
    ): EditWithAdditionalInfoResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId)
        // val shelterUser = getShelterUserUseCase.getShelterUserById(reqDto.shelterUserId)

        // if (shelterUser.shelterId != shelter.id) {
        //   throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "접근 권한이 없습니다.")
        // }

        shelterEditUseCase.editWithAdditionalInfo(
            shelterId = shelterId,
            parkingInfo = reqDto.parkingInfo,
            bankAccount = reqDto.donation,
            notice = reqDto.notice
        )

        shelterEditUseCase.editShelterOutLink(
            shelterId = shelterId,
            outLinks = reqDto.outLinks
        )

        return EditWithAdditionalInfoResponseDto(
            shelterId = shelter.id,
            shelterUserId = 0
        )
    }
}
