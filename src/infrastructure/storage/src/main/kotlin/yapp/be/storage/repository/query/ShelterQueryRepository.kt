package yapp.be.storage.repository.query

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterBookMark
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.domain.model.dto.ShelterDto
import yapp.be.domain.port.outbound.shelter.ShelterBookMarkQueryHandler
import yapp.be.domain.port.outbound.shelter.ShelterQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.common.model.toDomainModel
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.repository.ShelterBookMarkJpaRepository
import yapp.be.storage.jpa.shelter.repository.ShelterJpaRepository
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@Component
@Transactional(readOnly = true)
class ShelterQueryRepository(
    private val shelterJpaRepository: ShelterJpaRepository,
    private val shelterBookMarkJpaRepository: ShelterBookMarkJpaRepository
) : ShelterQueryHandler, ShelterBookMarkQueryHandler {

    override fun findById(id: Long): Shelter {
        val shelterEntity = shelterJpaRepository.findByIdOrNull(id) ?: throw CustomException(
            type = StorageExceptionType.ENTITY_NOT_FOUND,
            message = "보호소를 찾을 수 없습니다."
        )
        return shelterEntity.toDomainModel()
    }

    override fun findInfoByIdAndVolunteerId(id: Long, volunteerId: Long): ShelterDto {
        val shelterWithBookMarkInfo = shelterJpaRepository
            .findWithBookMarkByIdAndVolunteerId(
                id = id,
                volunteerId = volunteerId
            ) ?: throw CustomException(
            type = StorageExceptionType.ENTITY_NOT_FOUND,
            message = "보호소를 찾을 수 없습니다."
        )

        return ShelterDto(
            id = shelterWithBookMarkInfo.id,
            name = shelterWithBookMarkInfo.name,
            description = shelterWithBookMarkInfo.description,
            phoneNumber = shelterWithBookMarkInfo.phoneNum,
            notice = shelterWithBookMarkInfo.notice,
            profileImageUrl = shelterWithBookMarkInfo.profileImageUrl,
            bankAccount = run {
                if (shelterWithBookMarkInfo.bankName.isNullOrEmpty() || shelterWithBookMarkInfo.bankAccountNum.isNullOrEmpty()) null
                else BankAccount(name = shelterWithBookMarkInfo.bankName, accountNumber = shelterWithBookMarkInfo.bankAccountNum)
            },
            address = shelterWithBookMarkInfo.address.toDomainModel(),
            parkingInfo = run {
                if (shelterWithBookMarkInfo.parkingEnabled == null || shelterWithBookMarkInfo.parkingNotice.isNullOrEmpty()) null
                else ShelterParkingInfo(parkingEnabled = shelterWithBookMarkInfo.parkingEnabled, parkingNotice = shelterWithBookMarkInfo.parkingNotice)
            },
            bookMarked = shelterWithBookMarkInfo.bookMarked

        )
    }

    override fun findByIdAndPhoneNumber(id: Long, phoneNumber: String): Shelter {
        val shelterEntity = shelterJpaRepository.findByIdAndPhoneNum(id, phoneNumber) ?: throw CustomException(
            type = StorageExceptionType.ENTITY_NOT_FOUND,
            message = "보호소를 찾을 수 없습니다."
        )
        return shelterEntity.toDomainModel()
    }

    override fun findInfoById(id: Long): ShelterDto {
        val shelter = shelterJpaRepository.findByIdOrNull(id)?.toDomainModel()
            ?: throw CustomException(
                type = StorageExceptionType.ENTITY_NOT_FOUND,
                message = "보호소를 찾을 수 없습니다."
            )

        return ShelterDto(
            id = shelter.id,
            name = shelter.name,
            description = shelter.description,
            phoneNumber = shelter.phoneNumber,
            notice = shelter.notice,
            profileImageUrl = shelter.profileImageUrl,
            bankAccount = shelter.bankAccount,
            address = shelter.address,
            parkingInfo = shelter.parkingInfo,
            bookMarked = false
        )
    }
    override fun existByName(name: String): Boolean {
        return shelterJpaRepository.findByName(name) != null
    }

    override fun findByLocationAndIsFavorite(latitude: Double, longitude: Double, size: Int, volunteerId: Long): List<Shelter> {
        return buildList {
            shelterJpaRepository
                .findAllBookMarkedShelterByVolunteerId(volunteerId = volunteerId)
                .filter { calculateLocation(it.address.latitude, it.address.longitude, latitude, longitude) <= size }
                .map { it.toDomainModel() }
        }
    }

    override fun findByLocation(latitude: Double, longitude: Double, size: Int): List<Shelter> {
        return buildList {
            shelterJpaRepository.findAll()
                .filter { calculateLocation(it.address.latitude, it.address.longitude, latitude, longitude) <= size }
                .map { it.toDomainModel() }
        }
    }

    override fun findByAddressAndIsFavorite(address: String, volunteerId: Long): List<Shelter> {
        return shelterJpaRepository
            .findAllBookMarkedShelterByVolunteerIdAndAddress(
                volunteerId = volunteerId,
                address = address,
            ).map { it.toDomainModel() }
    }

    override fun findByAddress(address: String): List<Shelter> {
        return shelterJpaRepository.findAllByAddress(address).map { it.toDomainModel() }
    }

    override fun getAllBookMarkedShelterByVolunteerId(volunteerId: Long): List<Shelter> {
        return shelterJpaRepository.findAllBookMarkedShelterByVolunteerId(volunteerId)
            .map { it.toDomainModel() }
    }

    override fun getShelterIdAndVolunteerId(shelterId: Long, volunteerId: Long): ShelterBookMark? {
        val shelterBookMarkEntity = shelterBookMarkJpaRepository.findByShelterIdAndVolunteerId(
            shelterId = shelterId,
            volunteerId = volunteerId
        )

        return shelterBookMarkEntity?.toDomainModel()
    }

    private fun calculateLocation(shelterLat: Double, shelterLng: Double, userLat: Double, userLng: Double): Double {
        val theta: Double = shelterLng - userLng
        var dist = sin(deg2rad(shelterLat)) * sin(deg2rad(userLat)) + cos(deg2rad(shelterLat)) * cos(deg2rad(userLat)) * cos(deg2rad(theta))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515 * 1609.344
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180 / Math.PI
    }
}
