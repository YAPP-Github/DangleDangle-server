package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.EditShelterAdditionalInfoRequest
import yapp.be.apiapplication.shelter.controller.model.EditShelterEssentialInfoRequest
import yapp.be.apiapplication.shelter.controller.model.EditShelterProfileImageRequest
import yapp.be.apiapplication.shelter.service.shelter.ShelterManageApplicationService
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterProfileImageResponseDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterWithAdditionalInfoResponseDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditWithEssentialInfoResponseDto
import yapp.be.apiapplication.shelter.service.shelter.model.GetShelterResponseDto
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthentication
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthenticationInfo

@Tag(name = "보호소 정보 관리 api")
@RequestMapping("/v1/shelter/admin")
@RestController
class ShelterManageController(
    private val shelterManageApplicationService: ShelterManageApplicationService
) {
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "보호소 정보 가져오기"
    )
    @GetMapping
    fun getShelter(@ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo): ResponseEntity<GetShelterResponseDto> {
        val resDto = shelterManageApplicationService.getShelter(shelterUserInfo.shelterUserId)
        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/image")
    @Operation(
        summary = "보호소 대표 이미지 변경"
    )
    fun editShelterProfileImage(
        @RequestBody req: EditShelterProfileImageRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<EditShelterProfileImageResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterManageApplicationService.editShelterProfileImage(
            shelterUserId = shelterUserInfo.shelterUserId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/essential-info")
    @Operation(
        summary = "보호소 필수정보 Edit",
    )
    fun editEssentialShelterInfo(
        @RequestBody req: EditShelterEssentialInfoRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<EditWithEssentialInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterManageApplicationService.editShelterEssentialInfo(
            shelterUserId = shelterUserInfo.shelterUserId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/additional-info")
    @Operation(
        summary = "보호소 추가정보 Edit",
    )
    fun editAdditionalShelterInfo(
        @RequestBody req: EditShelterAdditionalInfoRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<EditShelterWithAdditionalInfoResponseDto> {
        val reqDto = req.toDto()

        val resDto = shelterManageApplicationService.editShelterAdditionalInfo(
            shelterUserId = shelterUserInfo.shelterUserId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }
}
