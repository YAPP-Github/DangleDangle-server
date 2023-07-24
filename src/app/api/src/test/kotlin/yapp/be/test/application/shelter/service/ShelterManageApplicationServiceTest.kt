package yapp.be.test.application.shelter.service

import Fixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import yapp.be.apiapplication.shelter.service.ShelterManageApplicationService
import yapp.be.apiapplication.shelter.service.model.EditShelterProfileImageRequestDto
import yapp.be.apiapplication.shelter.service.model.EditShelterWithAdditionalInfoRequestDto
import yapp.be.apiapplication.shelter.service.model.EditWithEssentialInfoRequestDto
import yapp.be.config.ApiTest
import yapp.be.domain.port.outbound.shelter.ShelterCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterOutLinkCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterOutLinkQueryHandler
import yapp.be.domain.port.outbound.shelter.ShelterQueryHandler
import yapp.be.domain.port.outbound.shelteruser.ShelterUserCommandHandler
import yapp.be.model.enums.volunteerevent.OutLinkType
import yapp.be.model.vo.Address
import java.util.*

@ApiTest
class ShelterManageApplicationServiceTest(
    private val shelterManageApplicationService: ShelterManageApplicationService,
    private val shelterUserCommandHandler: ShelterUserCommandHandler,
    private val shelterCommandHandler: ShelterCommandHandler,
    private val shelterQueryHandler: ShelterQueryHandler,
    private val shelterOutLinkCommandHandler: ShelterOutLinkCommandHandler,
    private val shelterOutLinkQueryHandler: ShelterOutLinkQueryHandler,
) : BehaviorSpec({

    Given("보호소 사용자로 로그인 한 경우에") {
        val shelter = shelterCommandHandler.create(Fixture.createShelterEntity())
        val shelterUser = shelterUserCommandHandler.save(Fixture.createShelterUserEntity(shelterId = shelter.id))
        val shelterOutLink = shelterOutLinkCommandHandler.upsertAll(shelter.id, listOf(Fixture.createShelterOutLink()))

        When("보호소 조회를 시도하면") {
            val resDto = shelterManageApplicationService.getShelter(shelterUser.id)
            Then("사용자가 등록한 보호소의 정보가 조회된다.") {
                val newShelter = shelterQueryHandler.findById(shelter.id)
                resDto.id shouldBe newShelter.id
                resDto.name shouldBe newShelter.name
                resDto.phoneNumber shouldBe newShelter.phoneNumber
                resDto.description shouldBe newShelter.description
            }
        }

        When("보호소 대표 이미지 변경을 시도하면") {
            val profileImageUrl = UUID.randomUUID().toString()
            val reqDto = EditShelterProfileImageRequestDto(profileImageUrl)
            val resDto = shelterManageApplicationService.editShelterProfileImage(shelterUser.id, reqDto)
            Then("해당 보호소의 이미지가 변경된다.") {
                val newShelter = shelterQueryHandler.findById(shelter.id)
                resDto.shelterId shouldBe newShelter.id
                newShelter.profileImageUrl shouldBe profileImageUrl
            }
        }

        When("보호소 필수정보 변경을 시도하면") {
            val reqDto = EditWithEssentialInfoRequestDto(
                name = "보호소이름",
                phoneNumber = "000-1111-2222",
                description = "보호소 정보",
                address = Address(
                    address = "주소주소",
                    addressDetail = "상세주소",
                    postalCode = "12345",
                    latitude = 0.0,
                    longitude = 0.0
                )
            )
            val resDto = shelterManageApplicationService.editShelterEssentialInfo(shelterUser.id, reqDto)
            Then("해당 보호소의 필수정보가 변경된다.") {
                val newShelter = shelterQueryHandler.findById(shelter.id)
                resDto.shelterId shouldBe newShelter.id
                newShelter.name shouldBe "보호소이름"
                newShelter.phoneNumber shouldBe "000-1111-2222"
                newShelter.description shouldBe "보호소 정보"
            }
        }

        When("보호소 추가정보 변경을 시도하면") {
            val reqDto = EditShelterWithAdditionalInfoRequestDto(
                outLinks = listOf(Pair(OutLinkType.INSTAGRAM, "인스타그램")),
                parkingInfo = null,
                bankAccount = null,
                notice = "공지공지"
            )

            val resDto = shelterManageApplicationService.editShelterAdditionalInfo(shelterUser.id, reqDto)
            Then("해당 보호소의 추가정보가 변경된다.") {
                val newShelter = shelterQueryHandler.findById(shelter.id)
                val newOutLink = shelterOutLinkQueryHandler.findAllByShelterId(shelter.id)
                resDto.shelterId shouldBe newShelter.id
                newShelter.notice shouldBe "공지공지"
                newOutLink[0].type shouldBe OutLinkType.INSTAGRAM
                newOutLink[0].url shouldBe "인스타그램"
            }
        }
    }
})
