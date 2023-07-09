package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.tags.Tag
import java.time.LocalDate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.service.shelter.VolunteerEventApplicationService
import yapp.be.apiapplication.shelter.service.shelter.model.*
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthentication
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthenticationInfo
import org.springframework.format.annotation.DateTimeFormat

@Tag(name = "봉사 이벤트 api")
@RestController
@RequestMapping("/v1/shelter/{shelterId}/volunteer-event")
class VolunteerEventController(
    private val volunteerEventApplicationService: VolunteerEventApplicationService
) {
    @GetMapping
    fun getVolunteerEvents(
        @PathVariable shelterId: Long,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo?,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        from: LocalDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        to: LocalDate,
    ): ResponseEntity<GetVolunteerEventsResponseDto> {
        val reqDto = GetVolunteerEventsRequestDto(
            shelterId = shelterId,
            volunteerId = volunteerInfo?.volunteerId,
            from = from.atStartOfDay(),
            to = to.atTime(23, 59, 59)
        )
        val resDto = volunteerEventApplicationService.getVolunteerEvents(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @GetMapping("/{volunteerEventId}")
    fun getVolunteerEvent(
        @PathVariable shelterId: Long,
        @PathVariable volunteerEventId: Long,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo?
    ): ResponseEntity<GetDetailVolunteerEventResponseDto> {
        val reqDto = GetVolunteerEventRequestDto(
            shelterId = shelterId,
            volunteerEventId = volunteerEventId

        )
        val resDto = volunteerEventApplicationService.getVolunteerEvent(reqDto)

        return ResponseEntity.ok(resDto)
    }
}
