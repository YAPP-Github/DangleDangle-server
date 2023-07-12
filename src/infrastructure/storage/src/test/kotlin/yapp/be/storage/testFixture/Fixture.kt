package yapp.be.storage.testFixture

import yapp.be.model.enums.observaitonanimal.Gender
import yapp.be.model.enums.volunteerevent.*
import yapp.be.storage.jpa.common.model.Address
import yapp.be.storage.jpa.observationanimal.model.ObservationAnimalEntity
import yapp.be.storage.jpa.shelter.model.ShelterEntity
import yapp.be.storage.jpa.shelter.model.ShelterUserEntity
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
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
    ): VolunteerEntity {
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
        )
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
    ): VolunteerEventEntity {
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
        )
    }

    fun createShelterUserEntity(
        id: Long = 0,
        email: String = UUID.randomUUID().toString().substring(0, 5) + "@naver.com",
        password: String = "P@ssw0rd",
        shelterId: Long = 0,
    ): ShelterUserEntity {
        return ShelterUserEntity(
            id,
            email,
            password,
            shelterId,
        )
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
    ): ShelterEntity {
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
        )
    }

    fun createObservationAnimalEntity(
        id: Long = 0,
        name: String = UUID.randomUUID().toString(),
        profileImageUrl: String = UUID.randomUUID().toString(),
        age: String = UUID.randomUUID().toString(),
        gender: Gender? = Gender.FEMALE,
        specialNote: String = UUID.randomUUID().toString(),
        breed: String? = UUID.randomUUID().toString(),
        shelterId: Long = 0,
    ): ObservationAnimalEntity {
        return ObservationAnimalEntity(
            id,
            name,
            profileImageUrl,
            age,
            gender,
            specialNote,
            breed,
            shelterId,
        )
    }
}

