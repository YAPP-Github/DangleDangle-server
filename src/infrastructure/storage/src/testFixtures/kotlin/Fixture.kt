import yapp.be.domain.model.*
import yapp.be.model.enums.observaitonanimal.Gender
import yapp.be.model.enums.volunteerevent.*
import yapp.be.storage.jpa.common.model.Address
import yapp.be.storage.jpa.observationanimal.model.ObservationAnimalEntity
import yapp.be.storage.jpa.observationanimal.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.model.ShelterBookMarkEntity
import yapp.be.storage.jpa.shelter.model.ShelterEntity
import yapp.be.storage.jpa.shelter.model.ShelterOutlinkEntity
import yapp.be.storage.jpa.shelter.model.ShelterUserEntity
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity
import yapp.be.storage.jpa.volunteer.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.model.mappers.toDomainModel
import java.time.LocalDateTime
import java.util.*

object Fixture {
    fun createVolunteerEntity(
        id: Long = 0,
        email: String = UUID.randomUUID().toString().substring(0, 5) + "@naver.com",
        nickName: String = UUID.randomUUID().toString(),
        role: Role = Role.VOLUNTEER,
        phone: String = "010-1234-5678",
        oAuthType: OAuthType = OAuthType.KAKAO,
        oAuthIdentifier: String = UUID.randomUUID().toString(),
        oAuthAccessToken: String? = UUID.randomUUID().toString(),
        oAuthRefreshToken: String? = UUID.randomUUID().toString(),
        deleted: Boolean = false,
    ): Volunteer {
        return VolunteerEntity(
            id,
            email,
            nickName,
            role,
            phone,
            oAuthType,
            oAuthIdentifier,
            oAuthAccessToken,
            oAuthRefreshToken,
            deleted,
        ).toDomainModel()
    }

    fun createVolunteerEventEntity(
        id: Long = 0,
        shelterId: Long = 0,
        title: String = UUID.randomUUID().toString(),
        recruitNum: Int = 1000,
        description: String? = UUID.randomUUID().toString(),
        ageLimit: AgeLimit = AgeLimit.ADULT,
        startAt: LocalDateTime = LocalDateTime.now().minusMinutes(30),
        endAt: LocalDateTime = LocalDateTime.now().plusMinutes(30),
        status: VolunteerEventStatus = VolunteerEventStatus.IN_PROGRESS,
        category: VolunteerEventCategory = VolunteerEventCategory.WALKING,
    ): VolunteerEvent {
        return VolunteerEventEntity(
            id,
            shelterId,
            title,
            recruitNum,
            description,
            ageLimit,
            startAt,
            endAt,
            status,
            category,
        ).toDomainModel()
    }

    fun createShelterUserEntity(
        id: Long = 0,
        email: String = UUID.randomUUID().toString().substring(0, 5) + "@naver.com",
        password: String = "P@ssw0rd",
        shelterId: Long = 0,
    ): ShelterUser {
        return ShelterUserEntity(
            id,
            email,
            password,
            shelterId,
        ).toDomainModel()
    }

    fun createShelterEntity(
        id: Long = 0,
        name: String = UUID.randomUUID().toString(),
        description: String = UUID.randomUUID().toString(),
        address: Address = Address(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            0.0,
            0.0,
        ),
        phoneNum: String = UUID.randomUUID().toString(),
        bankName: String? = UUID.randomUUID().toString(),
        bankAccountNum: String? = UUID.randomUUID().toString(),
        notice: String? = UUID.randomUUID().toString(),
        parkingEnabled: Boolean? = true,
        parkingNotice: String? = UUID.randomUUID().toString(),
        profileImageUrl: String? = UUID.randomUUID().toString(),
    ): Shelter {
        return ShelterEntity(
            id,
            name,
            description,
            address,
            phoneNum,
            bankName,
            bankAccountNum,
            notice,
            parkingEnabled,
            parkingNotice,
            profileImageUrl,
        ).toDomainModel()
    }

    fun createObservationAnimal(
        id: Long = 0,
        name: String = UUID.randomUUID().toString(),
        profileImageUrl: String = UUID.randomUUID().toString(),
        age: String = UUID.randomUUID().toString(),
        gender: Gender? = Gender.FEMALE,
        specialNote: String = UUID.randomUUID().toString(),
        breed: String? = UUID.randomUUID().toString(),
        shelterId: Long = 0,
    ): ObservationAnimal {
        return ObservationAnimalEntity(
            id,
            name,
            profileImageUrl,
            age,
            gender,
            specialNote,
            breed,
            shelterId,
        ).toDomainModel()
    }

    fun createShelterBookmark(
        id: Long = 0,
        shelterId: Long = 0,
        volunteerId: Long = 0,
    ): ShelterBookMark {
        return ShelterBookMarkEntity(
            id,
            shelterId,
            volunteerId,
        ).toDomainModel()
    }

    fun createShelterOutLink(
        id: Long = 0,
        url: String = UUID.randomUUID().toString(),
        outLinkType: OutLinkType = OutLinkType.INSTAGRAM,
        shelterId: Long = 0,
    ): ShelterOutLink {
        return ShelterOutlinkEntity(
            id,
            url,
            outLinkType,
            shelterId,
        ).toDomainModel()
    }
}
