package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.time.LocalDate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.service.VolunteerActivityApplicationService
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthentication
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthenticationInfo
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import yapp.be.apiapplication.shelter.service.model.GetDetailVolunteerActivityResponseDto
import yapp.be.apiapplication.shelter.service.model.GetVolunteerActivityRequestDto
import yapp.be.apiapplication.shelter.service.model.GetVolunteerEventListRequestDto
import yapp.be.apiapplication.shelter.service.model.GetVolunteerEventListResponseDto
import yapp.be.apiapplication.shelter.service.model.ParticipateVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.ParticipateVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.WithdrawVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.WithdrawVolunteerEventResponseDto

@Tag(name = "봉사 이벤트 api")
@RestController
@RequestMapping("/v1/shelter/{shelterId}/volunteer-event")
class VolunteerActivityController(
    private val volunteerActivityApplicationService: VolunteerActivityApplicationService
) {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(
        summary = "봉사이벤트 리스트 조회"
    )
    fun getVolunteerEvents(
        @PathVariable shelterId: Long,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo?,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        from: LocalDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        to: LocalDate,
    ): ResponseEntity<GetVolunteerEventListResponseDto> {
        val reqDto = GetVolunteerEventListRequestDto(
            shelterId = shelterId,
            volunteerId = volunteerInfo?.volunteerId,
            from = from.atStartOfDay(),
            to = to.atTime(23, 59, 59)
        )
        val resDto = volunteerActivityApplicationService.getVolunteerEvents(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{volunteerEventId}")
    @Operation(
        summary = "봉사이벤트 상세 조회"
    )
    fun getVolunteerEvent(
        @PathVariable shelterId: Long,
        @PathVariable volunteerEventId: Long,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo?
    ): ResponseEntity<GetDetailVolunteerActivityResponseDto> {
        val reqDto = GetVolunteerActivityRequestDto(
            shelterId = shelterId,
            volunteerId = volunteerInfo?.volunteerId,
            volunteerEventId = volunteerEventId
        )
        val resDto = volunteerActivityApplicationService.getVolunteerEvent(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{volunteerEventId}/participate")
    @Operation(
        summary = "봉사이벤트 참여"
    )
    fun participateVolunteerEvent(
        @PathVariable shelterId: Long,
        @PathVariable volunteerEventId: Long,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo
    ): ResponseEntity<ParticipateVolunteerEventResponseDto> {
        val reqDto = ParticipateVolunteerEventRequestDto(
            shelterId = shelterId,
            volunteerId = volunteerInfo.volunteerId,
            volunteerEventId = volunteerEventId
        )
        val resDto = volunteerActivityApplicationService.participateVolunteerEvent(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{volunteerEventId}/withdraw")
    @Operation(
        summary = "봉사이벤트 참여철회"
    )
    fun withdrawVolunteerEvent(
        @PathVariable shelterId: Long,
        @PathVariable volunteerEventId: Long,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo
    ): ResponseEntity<WithdrawVolunteerEventResponseDto> {
        val reqDto = WithdrawVolunteerEventRequestDto(
            shelterId = shelterId,
            volunteerId = volunteerInfo.volunteerId,
            volunteerEventId = volunteerEventId
        )
        val resDto = volunteerActivityApplicationService.withdrawVolunteerEvent(reqDto)

        return ResponseEntity.ok(resDto)
    }
}
