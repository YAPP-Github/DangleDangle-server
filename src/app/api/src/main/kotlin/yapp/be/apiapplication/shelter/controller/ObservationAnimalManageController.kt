package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.AddObservationAnimalRequest
import yapp.be.apiapplication.shelter.controller.model.EditObservationAnimalRequest
import yapp.be.apiapplication.shelter.service.observationanimal.ObservationAnimalManageApplicationService
import yapp.be.apiapplication.shelter.service.observationanimal.model.AddObservationAnimalResponseDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.DeleteObservationAnimalResponseDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.EditObservationAnimalResponseDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.GetObservationAnimalResponseDto
import yapp.be.apiapplication.system.security.annotations.ShelterUserAuthentication
import yapp.be.apiapplication.system.security.annotations.ShelterUserAuthenticationInfo

@Tag(name = "특별 케어 동물 관리 api")
@RequestMapping("/v1/shelter/admin/observation-animal")
@RestController
class ObservationAnimalManageController(
    private val observationAnimalManageApplicationService: ObservationAnimalManageApplicationService
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(
        summary = "특별 케어 동물 리스트가져오기"
    )
    fun getObservationAnimals(
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<List<GetObservationAnimalResponseDto>> {
        val resDto = observationAnimalManageApplicationService.getShelterObservationAnimals(shelterUserInfo.shelterUserId)
        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{observationAnimalId}")
    @Operation(
        summary = "특별 케어 동물 가져오기"
    )
    fun getObservationAnimal(
        @PathVariable observationAnimalId: Long,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<GetObservationAnimalResponseDto> {
        val resDto = observationAnimalManageApplicationService.getObservationAnimal(observationAnimalId)
        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @Operation(
        summary = "특별 케어 동물 추가"
    )
    fun addObservationAnimal(
        @RequestBody req: AddObservationAnimalRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<AddObservationAnimalResponseDto> {
        val reqDto = req.toDto()
        val resDto = observationAnimalManageApplicationService.addObservationAnimal(
            shelterUserId = shelterUserInfo.shelterUserId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{observationAnimalId}")
    @Operation(
        summary = "특별 케어 동물 수정"
    )
    fun editObservationAnimal(
        @PathVariable observationAnimalId: Long,
        @RequestBody req: EditObservationAnimalRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo

    ): ResponseEntity<EditObservationAnimalResponseDto> {
        val reqDto = req.toDto()
        val resDto = observationAnimalManageApplicationService.editObservationAnimal(
            shelterUserId = shelterUserInfo.shelterUserId,
            observationAnimalId = observationAnimalId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{observationAnimalId}")
    @Operation(
        summary = "특별 케어 동물 삭제"
    )
    fun deleteObservationAnimal(
        @PathVariable observationAnimalId: Long,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<DeleteObservationAnimalResponseDto> {
        val resDto = observationAnimalManageApplicationService.deleteObservationAnimal(
            shelterUserId = shelterUserInfo.shelterUserId,
            observationAnimalId = observationAnimalId
        )
        return ResponseEntity.ok(resDto)
    }
}
