package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.ShelterEditAdditionalInfoRequest
import yapp.be.apiapplication.shelter.controller.model.ShelterEditEssentialInfoRequest
import yapp.be.apiapplication.shelter.controller.model.ShelterEditProfileImageRequest

@Tag(name = "보호소 정보 관리 api")
@RequestMapping("/v1/shelter")
@RestController
class ShelterEditController {
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{shelterId}/image")
    @Operation(
        summary = "보호소 대표 이미지 변경"
    )
    fun editShelterProfileImage(
        @PathVariable shelterId: Long,
        @RequestBody req: ShelterEditProfileImageRequest
    ) {
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{shelterId}/essential-info")
    @Operation(
        summary = "보호소 필수정보 Edit",
    )
    fun editEssentialShelterInfo(
        @PathVariable shelterId: Long,
        @RequestBody req: ShelterEditAdditionalInfoRequest
    ) {
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{shelterId}/additinoal-info")
    @Operation(
        summary = "보호소 추가정보 Edit",
    )
    fun editAdditionalShelterInfo(
        @PathVariable shelterId: Long,
        @RequestBody req: ShelterEditEssentialInfoRequest
    ) {
    }
}
