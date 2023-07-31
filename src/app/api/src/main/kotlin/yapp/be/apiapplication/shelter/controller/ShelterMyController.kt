package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.service.ShelterMyApplicationService
import yapp.be.apiapplication.shelter.service.model.GetShelterMyProfileResponseDto
import yapp.be.apiapplication.shelter.service.model.GetShelterMyVolunteerEventHistoryResponseDto
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthentication
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthenticationInfo
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
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
        @RequestParam(required = false) status: VolunteerEventStatus?,
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
}
