package yapp.be.application.shelter.service

import Fixture
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import yapp.be.apiapplication.shelter.service.ShelterApplicationService
import yapp.be.apiapplication.shelter.service.model.BookMarkShelterRequestDto
import yapp.be.domain.port.outbound.VolunteerCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterBookMarkQueryHandler
import yapp.be.domain.port.outbound.shelter.ShelterCommandHandler
import kotlin.properties.Delegates

@SpringBootTest
class ShelterApplicationServiceTest(
    private val shelterApplicationService: ShelterApplicationService,
    private val shelterBookMarkQueryHandler: ShelterBookMarkQueryHandler,
    private val volunteerCommandHandler: VolunteerCommandHandler,
    private val shelterCommandHandler: ShelterCommandHandler,
) : StringSpec({
    var shelterId by Delegates.notNull<Long>()
    var volunteerId by Delegates.notNull<Long>()
    lateinit var bookmarkShelterRequestDto: BookMarkShelterRequestDto

    beforeEach {
        volunteerId = volunteerCommandHandler.save(Fixture.createVolunteerEntity()).id
        shelterId = shelterCommandHandler.create(Fixture.createShelterEntity()).id
        bookmarkShelterRequestDto = BookMarkShelterRequestDto(shelterId, volunteerId)
    }

    "보호소를 즐겨찾기할 수 있다." {
        // when
        val responseDto = shelterApplicationService.bookMarkShelter(bookmarkShelterRequestDto)

        // then
        val bookMark = shelterBookMarkQueryHandler.getShelterIdAndVolunteerId(shelterId, volunteerId)

        bookMark shouldNotBe null
        bookMark?.shelterId shouldBe responseDto.shelterId
        bookMark?.volunteerId shouldBe responseDto.volunteerId
    }
})
