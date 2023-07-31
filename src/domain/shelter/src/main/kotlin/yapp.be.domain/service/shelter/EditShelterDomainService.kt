package yapp.be.domain.service.shelter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.model.vo.Address
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.domain.port.inbound.shelter.EditShelterUseCase
import yapp.be.domain.port.outbound.shelter.ShelterCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterOutLinkCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterOutLinkQueryHandler
import yapp.be.domain.port.outbound.shelter.ShelterQueryHandler

@Service
class EditShelterDomainService(
    private val shelterQueryHandler: ShelterQueryHandler,
    private val shelterCommandHandler: ShelterCommandHandler,
    private val shelterOutLinkQueryHandler: ShelterOutLinkQueryHandler,
    private val shelterOutLinkCommandHandler: ShelterOutLinkCommandHandler
) : EditShelterUseCase {
    @Transactional
    override fun editAlarmEnabled(shelterId: Long, alarmEnabled: Boolean): Shelter {
        val shelter = shelterQueryHandler.findById(shelterId)
        val updatedShelter = Shelter(
            id = shelter.id,
            name = shelter.name,
            description = shelter.description,
            phoneNumber = shelter.phoneNumber,
            address = shelter.address,
            notice = shelter.notice,
            profileImageUrl = shelter.profileImageUrl,
            bankAccount = shelter.bankAccount,
            parkingInfo = shelter.parkingInfo,
            alarmEnabled = alarmEnabled
        )

        return shelterCommandHandler.update(updatedShelter)
    }

    @Transactional
    override fun editProfileImage(shelterId: Long, profileImageUrl: String): Shelter {
        val shelter = shelterQueryHandler.findById(shelterId)
        val updatedShelter = Shelter(
            id = shelter.id,
            name = shelter.name,
            description = shelter.description,
            phoneNumber = shelter.phoneNumber,
            address = shelter.address,
            notice = shelter.notice,
            profileImageUrl = profileImageUrl,
            bankAccount = shelter.bankAccount,
            parkingInfo = shelter.parkingInfo,
            alarmEnabled = shelter.alarmEnabled
        )

        return shelterCommandHandler.update(updatedShelter)
    }

    @Transactional
    override fun editWithEssentialInfo(
        shelterId: Long,
        name: String,
        phoneNumber: String,
        description: String,
        address: Address
    ): Shelter {
        val shelter = shelterQueryHandler.findById(shelterId)
        val updatedShelter = Shelter(
            id = shelter.id,
            name = name,
            description = description,
            phoneNumber = phoneNumber,
            address = address,
            notice = shelter.notice,
            profileImageUrl = shelter.profileImageUrl,
            bankAccount = shelter.bankAccount,
            parkingInfo = shelter.parkingInfo,
            alarmEnabled = shelter.alarmEnabled
        )

        return shelterCommandHandler.update(updatedShelter)
    }

    @Transactional
    override fun editWithAdditionalInfo(
        shelterId: Long,
        parkingInfo: ShelterParkingInfo?,
        bankAccount: BankAccount?,
        notice: String?
    ): Shelter {
        val shelter = shelterQueryHandler.findById(shelterId)
        val updatedShelter = Shelter(
            id = shelter.id,
            name = shelter.name,
            description = shelter.description,
            phoneNumber = shelter.phoneNumber,
            address = shelter.address,
            notice = notice,
            profileImageUrl = shelter.profileImageUrl,
            bankAccount = bankAccount,
            parkingInfo = parkingInfo,
            alarmEnabled = shelter.alarmEnabled
        )

        return shelterCommandHandler.update(updatedShelter)
    }

    @Transactional
    override fun editShelterOutLink(shelterId: Long, outLinks: List<ShelterOutLink>): List<ShelterOutLink> {
        val outLinkMap = shelterOutLinkQueryHandler.findAllByShelterId(shelterId).groupBy { it.type }
        val updatedOutLinks = outLinks.map {
            val id = outLinkMap[it.type]?.firstOrNull()?.id ?: 0
            ShelterOutLink(
                id = id,
                url = it.url,
                type = it.type,
                shelterId = it.shelterId
            )
        }
        return shelterOutLinkCommandHandler.upsertAll(
            shelterId = shelterId,
            outLinks = updatedOutLinks
        )
    }
}
