package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.service.shelter.VolunteerEventApplicationService
import yapp.be.apiapplication.shelter.service.shelter.model.GetVolunteerEventsRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.GetVolunteerEventsResponseDto
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthentication
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthenticationInfo
import java.time.Month
import java.time.Year

@Tag(name = "봉사 이벤트 api")
@RestController
@RequestMapping("/v1/shelter/{shelterId}/volunteer-event")
class VolunteerEventController(
    private val volunteerEventApplicationService: VolunteerEventApplicationService
) {
    @GetMapping("/{shelterId}/volunteer-event")
    fun getVolunteerEvents(
        @PathVariable shelterId: Long,
        @RequestParam year: Int,
        @RequestParam month: Int,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo?
    ): ResponseEntity<GetVolunteerEventsResponseDto> {
        val reqDto = GetVolunteerEventsRequestDto(
            shelterId = shelterId,
            volunteerId = volunteerInfo?.volunteerId,
            year = Year.of(year),
            month = Month.of(month)
        )
        val resDto = volunteerEventApplicationService.getVolunteerEvents(reqDto)

        return ResponseEntity.ok(resDto)
    }
}
