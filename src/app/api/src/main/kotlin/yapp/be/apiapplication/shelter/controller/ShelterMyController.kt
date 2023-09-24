package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yapp.be.apiapplication.shelter.service.ShelterMyApplicationService
import yapp.be.apiapplication.shelter.service.model.DeleteShelterUserResponseDto
import yapp.be.apiapplication.shelter.service.model.GetShelterMyProfileResponseDto
import yapp.be.apiapplication.shelter.service.model.GetShelterMyVolunteerEventHistoryResponseDto
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthentication
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthenticationInfo
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.model.support.PagedResult

@RestController
@Tag(name = "보호소 My api")
@RequestMapping("/v1/shelter/admin/my")
class ShelterMyController(
    private val shelterMyApplicationService: ShelterMyApplicationService
) {

    @GetMapping
    @Operation(
        summary = "보호소 My 정보 가져오기"
    )
    fun getShelterMyProfile(
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<GetShelterMyProfileResponseDto> {
        val resDto = shelterMyApplicationService
            .getShelterMyProfile(shelterId = shelterUserInfo.shelterUserId)
        return ResponseEntity.ok(resDto)
    }

    @GetMapping("/volunteer-event")
    @Operation(
        summary = "보호소 봉사 History API"
    )
    fun getShelterVolunteerEventHistories(
        @RequestParam page: Int,
        @RequestParam(required = false) status: VolunteerActivityStatus?,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<PagedResult<GetShelterMyVolunteerEventHistoryResponseDto>> {
        val resDto = shelterMyApplicationService
            .getShelterVolunteerEventHistories(
                page = page,
                status = status,
                shelterId = shelterUserInfo.shelterUserId
            )

        return ResponseEntity.ok(resDto)
    }

    @DeleteMapping("/withdraw")
    @Operation(
        summary = "보호소 회원탈퇴"
    )
    fun withdrawVolunteer(
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo,
    ): ResponseEntity<DeleteShelterUserResponseDto> {
        val resDto = shelterMyApplicationService.withdrawShelterUser(
            shelterUserId = shelterUserInfo.shelterUserId,
        )

        return ResponseEntity.ok(resDto)
    }
}
