package yapp.be.apiapplication.volunteer.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthentication
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthenticationInfo
import yapp.be.apiapplication.volunteer.controller.model.EditVolunteerMyProfileRequest
import yapp.be.apiapplication.volunteer.service.VolunteerMyApplicationService
import yapp.be.apiapplication.volunteer.service.model.EditVolunteerMyProfileResponseDto
import yapp.be.apiapplication.volunteer.service.model.GetVolunteerBookMarkedShelterResponseDto
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

    @GetMapping("/bookmarks")
    @Operation(
        summary = "봉사자 My 즐겨찾기 보호소 가져오기"
    )
    fun getVolunteerBookMarkedShelters(
        @VolunteerAuthentication volunteerUserInfo: VolunteerAuthenticationInfo
    ): ResponseEntity<GetVolunteerBookMarkedShelterResponseDto> {
        val resDto = volunteerMyApplicationService
            .getVolunteerBookMarkedShelters(volunteerId = volunteerUserInfo.volunteerId)

        return ResponseEntity.ok(resDto)
    }

    @PutMapping
    @Operation(
        summary = "봉사자 My 정보 수정하기"
    )
    fun editVolunteerMyProfile(
        @VolunteerAuthentication volunteerUserInfo: VolunteerAuthenticationInfo,
        @RequestBody req: EditVolunteerMyProfileRequest
    ): ResponseEntity<EditVolunteerMyProfileResponseDto> {
        val reqDto = req.toDto()
        val resDto = volunteerMyApplicationService.editMyProfile(
            volunteerId = volunteerUserInfo.volunteerId,
            reqDto = reqDto
        )
        return ResponseEntity.ok(resDto)
    }
}
