package yapp.be.storage.support.factory

import yapp.be.enum.Gender
import yapp.be.storage.jpa.observationanimal.model.ObservationAnimalEntity

object ObservationAnimalEntityFactory {

    fun getSampleObservationAnimalEntity(
        shelterId: Long
    ): ObservationAnimalEntity {
        return ObservationAnimalEntity(
            name = "보호동물",
            profileImageUrl = "sampleImageUrl",
            age = 3,
            gender = Gender.MALE,
            specialNote = "보호동물 특이사항",
            shelterId = shelterId,
            breed = "보호동물 종"
        )
    }
}
