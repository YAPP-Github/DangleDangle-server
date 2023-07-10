package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.time.LocalDate
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.AddVolunteerEventRequest
import yapp.be.apiapplication.shelter.service.VolunteerEventManageApplicationService
import yapp.be.apiapplication.shelter.service.model.AddVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.GetDetailVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerEventsRequestDto
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerEventsResponseDto
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthentication
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthenticationInfo

@Tag(name = "봉사 이벤트 관리 api")
@RestController
@RequestMapping("/v1/shelter/admin/volunteer-event")
class VolunteerEventManageController(
    private val volunteerEventManageApplicationService: VolunteerEventManageApplicationService
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{volunteerEventId}")
    @Operation(
        summary = "봉사 이벤트 상세 조회"
    )
    fun getVolunteerEvent(
        @PathVariable volunteerEventId: Long,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<GetDetailVolunteerEventResponseDto> {
        val reqDto = GetShelterUserVolunteerEventRequestDto(
            shelterUserId = shelterUserInfo.shelterUserId,
            volunteerEventId = volunteerEventId
        )
        val resDto = volunteerEventManageApplicationService
            .getVolunteerEvent(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/volunteer-event")
    @Operation(
        summary = "봉사 이벤트 리스트 조회"
    )
    fun getShelterUserVolunteerEvents(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        from: LocalDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        to: LocalDate,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<GetShelterUserVolunteerEventsResponseDto> {
        val reqDto = GetShelterUserVolunteerEventsRequestDto(
            shelterUserId = shelterUserInfo.shelterUserId,
            from = from.atStartOfDay(),
            to = to.atTime(23, 59, 59)
        )

        val resDto = volunteerEventManageApplicationService.getVolunteerEvents(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/volunteer-event")
    @Operation(
        summary = "봉사 이벤트 추가"
    )
    fun addVolunteerEvent(
        @RequestBody req: AddVolunteerEventRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<AddVolunteerEventResponseDto> {
        val reqDto = req.toDto()
        val resDto = volunteerEventManageApplicationService
            .addVolunteerEvent(
                shelterUserId = shelterUserInfo.shelterUserId,
                reqDto = reqDto
            )

        return ResponseEntity.ok(resDto)
    }
}
