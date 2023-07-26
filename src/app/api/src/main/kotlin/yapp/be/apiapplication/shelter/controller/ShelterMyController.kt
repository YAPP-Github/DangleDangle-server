package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.service.ShelterMyApplicationService
import yapp.be.apiapplication.shelter.service.model.GetShelterMyProfileResponseDto
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthentication
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthenticationInfo

@RestController
@Tag(name = "보호소 My api")
@RequestMapping("/v1/shelter/admin/my")
class ShelterMyController(
    private val shelterMyApplicationService: ShelterMyApplicationService
) {

    @GetMapping
    @Operation(
        summary = "My 정보 가져오기"
    )
    fun getShelterMyProfile(
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<GetShelterMyProfileResponseDto> {
        val resDto = shelterMyApplicationService
            .getShelterMyProfile(shelterId = shelterUserInfo.shelterUserId)
        return ResponseEntity.ok(resDto)
    }
}
