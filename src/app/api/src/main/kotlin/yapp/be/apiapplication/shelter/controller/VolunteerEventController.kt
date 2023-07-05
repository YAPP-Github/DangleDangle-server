package yapp.be.apiapplication.shelter.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.service.shelter.VolunteerEventApplicationService
import yapp.be.apiapplication.shelter.service.shelter.model.GetVolunteerEventsRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.GetVolunteerEventsResponseDto
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthentication
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthenticationInfo

@RestController
@RequestMapping("/v1/shelter/{shelterId}/volunteer-event")
class VolunteerEventController(
    private val volunteerEventApplicationService: VolunteerEventApplicationService
) {
    @GetMapping("/{shelterId}/volunteer-event")
    fun getVolunteerEvents(
        @PathVariable shelterId: Long,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo?
    ): ResponseEntity<GetVolunteerEventsResponseDto> {
        val reqDto = GetVolunteerEventsRequestDto(
            shelterId = shelterId,
            volunteerId = volunteerInfo?.volunteerId
        )
        val resDto = volunteerEventApplicationService.getVolunteerEvents(reqDto)

        return ResponseEntity.ok(resDto)
    }
}
