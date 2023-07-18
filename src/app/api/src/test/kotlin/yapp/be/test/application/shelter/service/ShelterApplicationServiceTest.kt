package yapp.be.test.application.shelter.service

import Fixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import yapp.be.apiapplication.shelter.service.ShelterApplicationService
import yapp.be.apiapplication.shelter.service.model.BookMarkShelterRequestDto
import yapp.be.domain.port.outbound.VolunteerCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterBookMarkQueryHandler
import yapp.be.domain.port.outbound.shelter.ShelterCommandHandler
import yapp.be.config.ApiTest

@ApiTest
class ShelterApplicationServiceTest(
    private val shelterApplicationService: ShelterApplicationService,
    private val shelterBookMarkQueryHandler: ShelterBookMarkQueryHandler,
    private val volunteerCommandHandler: VolunteerCommandHandler,
    private val shelterCommandHandler: ShelterCommandHandler,
) : BehaviorSpec({

    Given("즐겨찾기를 하지 않은 경우에") {
        val volunteerId = volunteerCommandHandler.save(Fixture.createVolunteerEntity()).id
        val shelterId = shelterCommandHandler.create(Fixture.createShelterEntity()).id
        val bookmarkShelterRequestDto = BookMarkShelterRequestDto(shelterId, volunteerId)
        When("즐겨찾기를 시도하면") {
            val bookMarkResponse = shelterApplicationService.bookMarkShelter(bookmarkShelterRequestDto)
            Then("보호소를 즐겨찾기 할 수 있다.") {
                val bookMark = shelterBookMarkQueryHandler.getShelterIdAndVolunteerId(shelterId, volunteerId)

                bookMark shouldNotBe null
                bookMarkResponse.bookMarked shouldBe true
                bookMark?.shelterId shouldBe bookMarkResponse.shelterId
                bookMark?.volunteerId shouldBe bookMarkResponse.volunteerId
            }
        }
    }

    Given("이미 즐겨찾기를 한 경우에") {
        val volunteerId = volunteerCommandHandler.save(Fixture.createVolunteerEntity()).id
        val shelterId = shelterCommandHandler.create(Fixture.createShelterEntity()).id
        val bookmarkShelterRequestDto = BookMarkShelterRequestDto(shelterId, volunteerId)
        When("즐겨찾기를 시도하면") {
            val bookMarkResponse = shelterApplicationService.bookMarkShelter(bookmarkShelterRequestDto)
            val unBookMarkResponse = shelterApplicationService.bookMarkShelter(bookmarkShelterRequestDto)
            Then("보호소 즐겨찾기가 해제된다.") {
                val bookMark = shelterBookMarkQueryHandler.getShelterIdAndVolunteerId(shelterId, volunteerId)

                bookMark shouldBe null
                unBookMarkResponse.bookMarked shouldBe false
            }
        }
    }
})
