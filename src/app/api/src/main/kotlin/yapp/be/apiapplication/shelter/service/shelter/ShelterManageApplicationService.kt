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
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.port.inbound.GetShelterUseCase
import yapp.be.domain.port.inbound.GetShelterUserUseCase
import yapp.be.domain.port.inbound.EditShelterUseCase

@Service
class ShelterManageApplicationService(
    private val getShelterUseCase: GetShelterUseCase,
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val editShelterUseCase: EditShelterUseCase
) {

    @Transactional(readOnly = true)
    fun getShelter(shelterUserId: Long): GetShelterResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)
        val shelter = getShelterUseCase.getShelterById(shelterUser.shelterId)
        val shelterOutLink = getShelterUseCase.getShelterOutLinkByShelterId(shelterUser.shelterId)

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
        val shelter = editShelterUseCase.editProfileImage(
            shelterId = shelterUser.shelterId,
            profileImageUrl = reqDto.profileImageUrl
        )

        return EditShelterProfileImageResponseDto(
            shelterId = shelter.id
        )
    }

    @Transactional
    fun editShelterEssentialInfo(
        shelterUserId: Long,
        reqDto: EditWithEssentialInfoRequestDto
    ): EditWithEssentialInfoResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)

        editShelterUseCase.editWithEssentialInfo(
            shelterId = shelterUser.shelterId,
            name = reqDto.name,
            phoneNumber = reqDto.phoneNumber,
            description = reqDto.description,
            address = reqDto.address
        )

        return EditWithEssentialInfoResponseDto(
            shelterId = shelterUser.shelterId,
            shelterUserId = shelterUser.id
        )
    }

    @Transactional
    fun editShelterAdditionalInfo(
        shelterUserId: Long,
        reqDto: EditShelterWithAdditionalInfoRequestDto
    ): EditShelterWithAdditionalInfoResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)

        editShelterUseCase.editWithAdditionalInfo(
            shelterId = shelterUser.shelterId,
            parkingInfo = reqDto.parkingInfo,
            bankAccount = reqDto.bankAccount,
            notice = reqDto.notice
        )

        editShelterUseCase.editShelterOutLink(
            shelterId = shelterUser.shelterId,
            outLinks = reqDto.outLinks.map {
                ShelterOutLink(
                    type = it.first,
                    url = it.second,
                    shelterId = shelterUser.shelterId
                )
            }
        )

        return EditShelterWithAdditionalInfoResponseDto(
            shelterId = shelterUser.shelterId,
            shelterUserId = shelterUser.id
        )
    }
}
