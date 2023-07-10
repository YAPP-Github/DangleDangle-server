package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.AddVolunteerEventRequest
import yapp.be.apiapplication.shelter.service.VolunteerEventManageApplicationService
import yapp.be.apiapplication.shelter.service.model.AddVolunteerEventResponseDto
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthentication
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthenticationInfo

@Tag(name = "봉사 이벤트 관리 api")
@RestController
@RequestMapping("/v1/shelter/admin")
class VolunteerEventManageController(
    private val volunteerEventManageApplicationService: VolunteerEventManageApplicationService
) {

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
