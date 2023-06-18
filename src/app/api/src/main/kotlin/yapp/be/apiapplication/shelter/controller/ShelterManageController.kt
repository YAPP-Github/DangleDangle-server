package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
import yapp.be.apiapplication.system.security.annotations.ShelterUserAuthentication
import yapp.be.apiapplication.system.security.annotations.ShelterUserAuthenticationInfo

@Tag(name = "보호소 정보 관리 api")
@RequestMapping("/v1/shelter")
@RestController
class ShelterManageController(
    private val shelterManageApplicationService: ShelterManageApplicationService
) {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{shelterId}")
    @Operation(
        summary = "보호소 정보 가져오기"
    )
    fun getShelter(@PathVariable shelterId: Long): ResponseEntity<GetShelterResponseDto> {

        val resDto = shelterManageApplicationService.getShelter(shelterId)
        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{shelterId}/image")
    @Operation(
        summary = "보호소 대표 이미지 변경"
    )
    fun editShelterProfileImage(
        @PathVariable shelterId: Long,
        @RequestBody req: EditShelterProfileImageRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<EditShelterProfileImageResponseDto> {
        val reqDto = req.toDto(shelterId)
        val resDto = shelterManageApplicationService.editShelterProfileImage(
            shelterUserId = shelterUserInfo.shelterUserId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{shelterId}/essential-info")
    @Operation(
        summary = "보호소 필수정보 Edit",
    )
    fun editEssentialShelterInfo(
        @PathVariable shelterId: Long,
        @RequestBody req: EditShelterEssentialInfoRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<EditWithEssentialInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterManageApplicationService.editShelterEssentialInfo(
            shelterId = shelterId,
            shelterUserId = shelterUserInfo.shelterUserId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{shelterId}/additional-info")
    @Operation(
        summary = "보호소 추가정보 Edit",
    )
    fun editAdditionalShelterInfo(
        @PathVariable shelterId: Long,
        @RequestBody req: EditShelterAdditionalInfoRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<EditShelterWithAdditionalInfoResponseDto> {
        val reqDto = req.toDto(shelterId = shelterId)

        val resDto = shelterManageApplicationService.editShelterAdditionalInfo(
            shelterId = shelterId,
            shelterUserId = shelterUserInfo.shelterUserId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }
}
