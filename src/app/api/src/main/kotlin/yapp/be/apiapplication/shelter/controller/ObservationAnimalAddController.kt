package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.ObservationAnimalAddRequest
import yapp.be.apiapplication.shelter.service.ObservationAnimalManageApplicationService
import yapp.be.apiapplication.shelter.service.model.AddObservationAnimalResponseDto

@Tag(name = "특별 케어 동물 관리 api")
@RequestMapping("/v1/shelter/{shelterId}/observation-animal")
@RestController
class ObservationAnimalAddController(
    private val observationAnimalManageApplicationService: ObservationAnimalManageApplicationService
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @Operation(
        summary = "특별 케어 동물 추가"
    )
    fun editShelterProfileImage(
        @PathVariable shelterId: Long,
        @RequestBody req: ObservationAnimalAddRequest
    ): ResponseEntity<AddObservationAnimalResponseDto> {
        val reqDto = req.toDto()
        val resDto = observationAnimalManageApplicationService.addObservationAnimal(
            shelterId = shelterId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }
}
