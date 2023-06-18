package yapp.be.storage.test.repository

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import yapp.be.storage.jpa.shelter.repository.ShelterJpaRepository
import yapp.be.storage.repository.ObservationAnimalRepository
import yapp.be.storage.support.container.ContainerTestBase
import yapp.be.storage.support.factory.ObservationAnimalEntityFactory
import yapp.be.storage.support.factory.ShelterEntityFactory

class ObservationAnimalRepositoryTest : ContainerTestBase() {

    @Autowired
    private lateinit var sut: ObservationAnimalRepository

    @Autowired
    private lateinit var shelterJpaRepository: ShelterJpaRepository

    @Test
    @DisplayName("ObservationAnimalEntity Create Query 테스트")
    fun createQueryTest() {
        // given
        val shelterEntity = shelterJpaRepository.save(
            ShelterEntityFactory.getShelterEntity()
        )

        val observationAnimalEntity =
            ObservationAnimalEntityFactory.getSampleObservationAnimalEntity(shelterEntity.id)
    }
}
