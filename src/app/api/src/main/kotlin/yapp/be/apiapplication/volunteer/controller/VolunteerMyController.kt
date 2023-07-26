package yapp.be.apiapplication.volunteer.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthentication
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthenticationInfo
import yapp.be.apiapplication.volunteer.service.VolunteerMyApplicationService
import yapp.be.apiapplication.volunteer.service.model.GetVolunteerMyProfileResponseDto

@RestController
@Tag(name = "봉사자 My api")
@RequestMapping("/v1/volunteer/my")
class VolunteerMyController(
    private val volunteerMyApplicationService: VolunteerMyApplicationService
) {

    @GetMapping
    @Operation(
        summary = "봉사자 My 정보 가져오기"
    )
    fun getVolunteerMyProfile(
        @VolunteerAuthentication volunteerUserInfo: VolunteerAuthenticationInfo
    ): ResponseEntity<GetVolunteerMyProfileResponseDto> {
        val resDto = volunteerMyApplicationService
            .getVolunteerMyProfile(volunteerId = volunteerUserInfo.volunteerId)
        return ResponseEntity.ok(resDto)
    }
}
