package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.BookMarkShelterRequestDto
import yapp.be.apiapplication.shelter.service.model.BookMarkShelterResponseDto
import yapp.be.apiapplication.shelter.service.model.GetBankAccountInfoDto
import yapp.be.apiapplication.shelter.service.model.GetOutLinkInfoDto
import yapp.be.apiapplication.shelter.service.model.GetShelterAddressInfoDto
import yapp.be.apiapplication.shelter.service.model.GetShelterParkingInfoDto
import yapp.be.apiapplication.shelter.service.model.GetShelterResponseDto
import yapp.be.domain.port.inbound.shelter.AddShelterBookMarkUseCase
import yapp.be.domain.port.inbound.shelter.GetShelterUseCase
import yapp.be.domain.port.inbound.shelteruser.GetShelterUserUseCase

@Service
class ShelterApplicationService(
    private val getShelterUseCase: GetShelterUseCase,
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val addShelterBookMarkUseCase: AddShelterBookMarkUseCase,
) {
    @Transactional(readOnly = true)
    fun getShelter(shelterId: Long): GetShelterResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId)
        val shelterUser = getShelterUserUseCase.getShelterUserByShelterId(shelter.id)
        val shelterOutLink = getShelterUseCase.getShelterOutLinkByShelterId(shelterUser.shelterId)

        return GetShelterResponseDto(
            id = shelter.id,
            email = shelterUser.email.value,
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
                    outLinkType = it.type,
                    url = it.url
                )
            },
            parkingInfo = shelter.parkingInfo?.let {
                GetShelterParkingInfoDto(
                    parkingEnabled = it.parkingEnabled,
                    parkingNotice = it.parkingNotice
                )
            },
            bankAccount = shelter.bankAccount?.let {
                GetBankAccountInfoDto(
                    bankName = it.name,
                    accountNumber = it.accountNumber
                )
            },
            notice = shelter.notice
        )
    }
    fun bookMarkShelter(reqDto: BookMarkShelterRequestDto): BookMarkShelterResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId = reqDto.shelterId)
        val shelterBookMark = addShelterBookMarkUseCase.doBookMark(
            shelterId = shelter.id,
            volunteerId = reqDto.volunteerId
        )

        return BookMarkShelterResponseDto(
            shelterId = shelter.id,
            volunteerId = reqDto.volunteerId,
            bookMarked = shelterBookMark != null
        )
    }
}
