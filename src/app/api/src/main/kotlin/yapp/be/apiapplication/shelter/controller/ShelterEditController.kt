package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.ShelterEditAdditionalInfoRequest
import yapp.be.apiapplication.shelter.controller.model.ShelterEditEssentialInfoRequest
import yapp.be.apiapplication.shelter.controller.model.ShelterEditProfileImageRequest
import yapp.be.apiapplication.shelter.service.ShelterEditApplicationService
import yapp.be.apiapplication.shelter.service.model.EditProfileImageResponseDto
import yapp.be.apiapplication.shelter.service.model.EditWithAdditionalInfoResponseDto
import yapp.be.apiapplication.shelter.service.model.EditWithEssentialInfoResponseDto

@Tag(name = "보호소 정보 관리 api")
@RequestMapping("/v1/shelter")
@RestController
class ShelterEditController(
    private val shelterEditApplicationService: ShelterEditApplicationService
) {
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{shelterId}/image")
    @Operation(
        summary = "보호소 대표 이미지 변경"
    )
    fun editShelterProfileImage(
        @PathVariable shelterId: Long,
        @RequestBody req: ShelterEditProfileImageRequest
    ): ResponseEntity<EditProfileImageResponseDto> {
        val reqDto = req.toDto(shelterId)
        val resDto = shelterEditApplicationService.editProfileImage(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{shelterId}/essential-info")
    @Operation(
        summary = "보호소 필수정보 Edit",
    )
    fun editEssentialShelterInfo(
        @PathVariable shelterId: Long,
        @RequestBody req: ShelterEditEssentialInfoRequest
    ): ResponseEntity<EditWithEssentialInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterEditApplicationService.editEssentialInfo(
            shelterId = shelterId,
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
        @RequestBody req: ShelterEditAdditionalInfoRequest
    ): ResponseEntity<EditWithAdditionalInfoResponseDto> {
        val reqDto = req.toDto(shelterId)
        val resDto = shelterEditApplicationService.editAdditionalInfo(
            shelterId = shelterId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }
}
