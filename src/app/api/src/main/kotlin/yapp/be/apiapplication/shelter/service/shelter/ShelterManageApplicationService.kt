package yapp.be.apiapplication.shelter.service.shelter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterProfileImageRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterProfileImageResponseDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterWithAdditionalInfoRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterWithAdditionalInfoResponseDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditWithEssentialInfoRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditWithEssentialInfoResponseDto
import yapp.be.apiapplication.shelter.service.shelter.model.GetBankAccountInfoDto
import yapp.be.apiapplication.shelter.service.shelter.model.GetOutLinkInfoDto
import yapp.be.apiapplication.shelter.service.shelter.model.GetShelterAddressInfoDto
import yapp.be.apiapplication.shelter.service.shelter.model.GetShelterParkingInfoDto
import yapp.be.apiapplication.shelter.service.shelter.model.GetShelterResponseDto
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.domain.port.inbound.GetShelterUseCase
import yapp.be.domain.port.inbound.GetShelterUserUseCase
import yapp.be.domain.port.inbound.EditShelterUseCase
import yapp.be.exceptions.CustomException

@Service
class ShelterManageApplicationService(
    private val getShelterUseCase: GetShelterUseCase,
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val editShelterUseCase: EditShelterUseCase
) {

    @Transactional(readOnly = true)
    fun getShelter(shelterId: Long): GetShelterResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId)
        val shelterOutLink = getShelterUseCase.getShelterOutLinkByShelterId(shelterId)

        return GetShelterResponseDto(
            id = shelter.id,
            name = shelter.name,
            phoneNumber = shelter.phoneNumber,
            description = shelter.description,
            address = GetShelterAddressInfoDto(
                address = shelter.address.address,
                addressDetail = shelter.address.addressDetail,
                postalCode = shelter.address.postalCode,
                longitude = shelter.address.longitude,
                latitude = shelter.address.latitude
            ),
            profileImageUrl = shelter.profileImageUrl,
            outLinks = shelterOutLink.map {
                GetOutLinkInfoDto(
                    type = it.type,
                    url = it.url
                )
            },
            parkingInfo = shelter.parkingInfo?.let {
                GetShelterParkingInfoDto(
                    parkingEnabled = it.parkingEnabled,
                    notice = it.notice
                )
            },
            bankAccount = shelter.bankAccount?.let {
                GetBankAccountInfoDto(
                    name = it.name,
                    accountNumber = it.accountNumber
                )
            },
            notice = shelter.notice

        )
    }

    @Transactional
    fun editShelterProfileImage(
        shelterUserId: Long,
        reqDto: EditShelterProfileImageRequestDto
    ): EditShelterProfileImageResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)

        if (shelterUser.shelterId != reqDto.shelterId) {
            throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "접근 권한이 없습니다.")
        }

        val shelter = editShelterUseCase.editProfileImage(
            shelterId = reqDto.shelterId,
            profileImageUrl = reqDto.profileImageUrl
        )

        return EditShelterProfileImageResponseDto(
            shelterId = shelter.id
        )
    }

    @Transactional
    fun editShelterEssentialInfo(
        shelterId: Long,
        shelterUserId: Long,
        reqDto: EditWithEssentialInfoRequestDto
    ): EditWithEssentialInfoResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId)
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)

        if (shelterUser.shelterId != shelter.id) {
            throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "접근 권한이 없습니다.")
        }

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
    fun editShelterAdditionalInfo(
        shelterId: Long,
        shelterUserId: Long,
        reqDto: EditShelterWithAdditionalInfoRequestDto
    ): EditShelterWithAdditionalInfoResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId)
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)

        if (shelterUser.shelterId != shelter.id) {
            throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "접근 권한이 없습니다.")
        }

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
